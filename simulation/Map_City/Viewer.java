package simulation.Map_City;

import javax.swing.*;

public class Viewer extends JFrame {

    public static void main(String[] args) {

        Hex h = new Hex(true, 100, 100);
        City c = new City();
        JFrame frame = new JFrame("City Viewer");
        frame.setFocusable(true);
        frame.addKeyListener(new KeyboardControl());
        frame.add(c);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}
