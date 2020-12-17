/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.valeev.model;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import course2.kg.valeev.math.Vector2;
import course2.kg.valeev.utils2d.ScreenConverter;
import course2.kg.valeev.utils2d.ScreenPoint;

/**
 *
 * Класс, описывающий весь мир, в целом.
 * @author Alexey
 */
public class Billiard {
    private List<Ball> balls;
    private Field f;
    private ForceSource externalForce;


    private static final List<Color> colors =  Arrays.asList(Color.WHITE, Color.BLACK, Color.RED, Color.YELLOW, Color.BLUE, Color.ORANGE, new Color(75, 0, 130), new Color(0, 100, 0));
    private static final double M = 0.284;
    private static final double R = 4;

    public Billiard(int ballsCount, Field f) {
        createBalls(ballsCount);
        this.f = f;
        this.externalForce = new ForceSource(f.getRectangle().getCenter());
    }

    private void createBalls(int count) {
        balls = new ArrayList<>();
        int x = 20;
        int y = 20;
        for (int i = 0; i < count; i++) {
            balls.add(new Ball(M, R, colors.get(i), new Vector2(x += 10, y += 10)));
        }
    }
    
    /**
     * Метод обновления состояния мира за указанное время
     * @param dt Промежуток времени, за который требуется обновить мир.
     */
    public void update(double dt) {
        for (Ball b : balls) {
            Vector2 np = b.getPosition()
                    .add(b.getVelocity().mul(dt))
                    .add(b.getAcceleration().mul(dt * dt * 0.5));
            Vector2 nv = b.getVelocity()
                    .add(b.getAcceleration().mul(dt));

            double vx = nv.getX(), vy = nv.getY();
            boolean reset = false;
            if (np.getX() - b.getR() < f.getRectangle().getLeft() || np.getX() + b.getR() > f.getRectangle().getRight()) {
                vx = -vx;
                reset = true;
            }
            if (np.getY() - b.getR() < f.getRectangle().getBottom() || np.getY() + b.getR() > f.getRectangle().getTop()) {
                vy = -vy;
                reset = true;
            }
            nv = new Vector2(vx, vy);
            if (nv.length() < 1e-10)
                nv = new Vector2(0, 0);
            if (reset)
                np = b.getPosition();

            Vector2 Fvn = externalForce.getForceAt(np);
            Vector2 Ftr = b.getVelocity().normolized().mul(-f.getMu() * b.getM() * f.getG());
            Vector2 F = Ftr.add(Fvn);

            b.setAcceleration(F.mul(1 / b.getM()));
            b.setVelocity(nv);
            b.setPosition(np);
        }
    }
    
    /**
     * Метод рисует ткущее состояние мира.
     * На самом деле всю логику рисования стоит вынести из этого класса
     * куда-нибудь в WroldDrawer, унаследованный от IDrawer
     * @param g Графикс, на котором надо нарисовать текущее состояние.
     * @param sc Актуальный конвертер координат.
     */
    public void draw(Graphics2D g, ScreenConverter sc) {
        ScreenPoint tl = sc.r2s(f.getRectangle().getTopLeft());
        int w = sc.r2sDistanceH(f.getRectangle().getWidth());
        int h = sc.r2sDistanceV(f.getRectangle().getHeight());
        g.setColor(Color.GREEN);
        g.fillRect(tl.getI(), tl.getJ(), w, h);
        g.setColor(Color.RED);
        g.drawRect(tl.getI(), tl.getJ(), w, h);
        for (Ball b : balls) {
            ScreenPoint pc = sc.r2s(b.getPosition());
            int rh = sc.r2sDistanceH(b.getR());
            int rv = sc.r2sDistanceV(b.getR());
            g.setColor(b.getColor());
            g.fillOval(pc.getI() - rh, pc.getJ() - rv, rh + rh, rv + rv);
        }
        g.drawString(String.format("Mu=%.2f", f.getMu()), 10, 30);
        g.drawString(String.format("F=%.0f", externalForce.getValue()), 10, 50);
    }

    public Field getF() {
        return f;
    }

    public void setF(Field f) {
        this.f = f;
    }
    
    public ForceSource getExternalForce() {
        return externalForce;
    }

    private boolean intersects(Ball b1, Ball b2) {
        return false;
    }
}
