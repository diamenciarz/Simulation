package simulation.Map_City;

import helpers.Distributions;
import simulation.Location;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class City extends JPanel {

    public static ArrayList<Hex> city;
    public static int CENTER_X = 0;
    public static int CENTER_Y = 0;
    protected static final int N_HEX = 6;
    private static final double APHOTEM = (Math.sqrt(3) * Hex.RADIUS) / 2;


    public City(){

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        CENTER_X = (int)(dimension.width / 2);
        CENTER_Y = (int)(dimension.height / 2);
        city = generateCity();
        setPreferredSize(new Dimension(dimension.width, dimension.height));
        MouseControl mouse = new MouseControl();
        KeyboardControl keyboard = new KeyboardControl();
        addMouseListener(mouse);
    }


    public static void chooseDistrictForOccurencies(){
        int selectedDistrict = (int)(Math.random() * (N_HEX + 1));
        int time = (int) Distributions.bisection(Math.random());
        city.get(selectedDistrict).generateOcurrencies(time);
    }

    //TODO: implement EASY MONEY
    public Location manhatan(Location xo, Location xf){
        return null;
    }

    private ArrayList<Hex> generateCity(){

        ArrayList<Hex> hex = new ArrayList<>();
        hex.add(new Hex(true, CENTER_X, CENTER_Y));

        for (int i = 0; i < N_HEX + 1; i++) {
                double desfase = Math.PI / Hex.POINTS;
                int x = (int) (CENTER_X + 2 * APHOTEM * Math.cos(i * 2 * Math.PI / Hex.POINTS + desfase));
                int y = (int) (CENTER_Y + 2 * APHOTEM * Math.sin(i * 2 * Math.PI / Hex.POINTS + desfase));
                hex.add(new Hex(false, x, y));
        }
        return hex;
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;

        for (Hex hex : city) {
            g.setColor(Color.RED);
            g.fillPolygon(hex.getPolygon());
            g.setColor(Color.LIGHT_GRAY);
            g2.setStroke(new BasicStroke(2));
            g2.drawPolygon(hex.getPolygon());

            //THIS CREATES THE POINT WHERE THE AMBULANCE START THE TRIP
            //PARA MODIFICAR CAMBIAR TAMAÑO MODIFICAD LA VARIABLE CIRCLE
            //ALSO DIFERENCE BETWEEN CENTRAL HOSPITAL

            if(!hex.isHospital()){
                g.setColor(Color.LIGHT_GRAY);
                g2.fillOval(
                        (int) hex.getCoordinates()[0] - Hex.CIRCLE_HOSPITAL / 2,
                        (int) hex.getCoordinates()[1] - Hex.CIRCLE_HOSPITAL / 2,
                        Hex.CIRCLE_HOSPITAL,
                        Hex.CIRCLE_HOSPITAL)
                ;
            }else{
                g.setColor(Color.BLACK);
                g2.fillRect(
                        (int) hex.getCoordinates()[0] - Hex.CIRCLE_HOSPITAL / 2,
                        (int) hex.getCoordinates()[1] - Hex.CIRCLE_HOSPITAL / 2,
                        Hex.CIRCLE_HOSPITAL,
                        Hex.CIRCLE_HOSPITAL
                );
            }
        }

        repaint();

        g.setColor(Color.BLACK);

        for (int i = 0; i < city.size(); i++) {

            if (!city.get(i).getPatients().isEmpty()) {

                for (int j = 0; j < city.get(i).getPatients().size(); j++) {

                    g2.drawString(city.get(i).getPatients().get(j).getPatient().getPriority() + "",
                            (int) city.get(i).getPatients().get(j).getLocation().getX(),
                            (int) city.get(i).getPatients().get(j).getLocation().getY()
                    );

                }
            }
        }


        g2.fillOval((int) Hex.ambulanceX, (int) Hex.ambulanceY, 10, 10);

        repaint(1);
    }
}










/*
while (Math.abs(city.get(i).ambulanceX - city.get(i).getPatients().get(0).getLocation().getX()) > 5) {
                        city.get(i).moveX(city.get(i).getPatients().get(0).getLocation().getX());
                        repaint();
                        g2.fillOval((int) city.get(i).ambulanceX, (int) city.get(i).ambulanceY, 10, 10);
                    }

                    g2.fillOval((int) city.get(i).ambulanceX, (int) city.get(i).ambulanceY, 10, 10);
                    g2.drawLine(
                            (int) city.get(i).getCoordinates()[0],
                            (int) city.get(i).getCoordinates()[1],
                            (int) city.get(i).ambulanceX,
                            (int) city.get(i).ambulanceY
                    );

                    repaint();

                    double a = city.get(i).ambulanceX;
                    double b = city.get(i).ambulanceY;

                    while (Math.abs(city.get(i).ambulanceY - city.get(i).getPatients().get(0).getLocation().getY()) > 5) {
                        city.get(i).moveY(city.get(i).getPatients().get(0).getLocation().getY());
                        repaint();
                        g2.fillOval((int) city.get(i).ambulanceX, (int) city.get(i).ambulanceY, 10, 10);
                        repaint();
                    }
                    g2.fillOval((int) city.get(i).ambulanceX, (int) city.get(i).ambulanceY, 10, 10);
                    repaint();

                    g2.drawLine(
                            (int) a,
                            (int) b,
                            (int) city.get(i).ambulanceX,
                            (int) city.get(i).ambulanceY
                    );
                }

 */
