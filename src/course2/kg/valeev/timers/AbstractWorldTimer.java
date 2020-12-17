/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package course2.kg.valeev.timers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import course2.kg.valeev.model.Billiard;

/**
 *
 * @author Alexey
 */
public abstract class AbstractWorldTimer {
    protected Billiard actualWorld;
    private Timer timer;

    public AbstractWorldTimer(Billiard world, int period) {
        this.actualWorld = world;
        timer = new Timer(period, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                worldAction(actualWorld);
            }
        });
    }
    
    public void start() {
        timer.start();
    }
    public void stop() {
        timer.stop();
    }
    public void setPeriod(int delay) {
        timer.setDelay(delay);
    }
    
    abstract void worldAction(Billiard w);
}
