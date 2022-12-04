package simulation.Map_City;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseControl extends MouseAdapter {

    @Override
    public void mousePressed(MouseEvent e) {
        City.chooseDistrictForOccurencies();

    }
}
