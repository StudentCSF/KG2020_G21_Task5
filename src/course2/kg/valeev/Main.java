package course2.kg.valeev;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(1300, 650);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        DrawPanel panel = new DrawPanel();
        frame.add(panel);
        frame.setVisible(true);
    }
}
