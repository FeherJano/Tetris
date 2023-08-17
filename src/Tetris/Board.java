package Tetris;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Board extends JPanel {

    private Timer looper;

    public Board() {
        looper = new Timer(500, new ActionListener() {
            int n = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(n++);
            }
        });
        looper.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(10,10,200,200);
    }
}