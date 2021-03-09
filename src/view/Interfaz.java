package view;

import Controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import static java.awt.BorderLayout.CENTER;

public class Interfaz extends JFrame {
    private JPanel gamePane;
    private final Controller controller;

    public Interfaz() {
        controller = new Controller();
        setSize(300, 300);
        setTitle("Timbiriche");
        add(getGamePane(), CENTER);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                controller.assigPositionDrawables(gamePane);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                //System.out.println("Moved to " + e.getComponent().getLocation());
            }
        });
        setVisible(true);
    }

    private JPanel getGamePane() {
        gamePane = new JPanel();
        gamePane.setBackground(Color.white);
        gamePane.addMouseListener(
                new MouseListener() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        controller.clickInPoint(e.getPoint(),gamePane.getGraphics());
                    }

                    @Override
                    public void mousePressed(MouseEvent e) {

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {

                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {

                    }

                    @Override
                    public void mouseExited(MouseEvent e) {

                    }
                }
        );
        return gamePane;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        controller.paintDrawables(this.gamePane);
    }
}
