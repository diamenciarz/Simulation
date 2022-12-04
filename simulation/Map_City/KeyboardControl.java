package simulation.Map_City;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardControl  implements KeyListener {


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (e.getKeyCode() == KeyEvent.VK_A) {
            Hex.left();
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            Hex.right();
        }
        if (e.getKeyCode() == KeyEvent.VK_W) {
            Hex.up();
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            Hex.down();
        }



    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
