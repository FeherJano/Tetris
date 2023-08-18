package Tetris;

import javax.swing.*;
import javax.xml.parsers.SAXParser;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Board extends JPanel implements KeyListener {

    private static int FPS = 60;
    private static int delay = FPS / 10000;

    public static  final int BOARD_WIDTH = 10;
    public static  final int BOARD_HEIGHT = 20;
    public static  final int BLOCK_SIZE = 30;

    private Timer looper;

    private Color[][] board = new Color[BOARD_HEIGHT][BOARD_WIDTH];

    private Random random;


    private Shape[] shapes = new Shape[7];
    private Shape currentShape;

    public Board() {
        random = new Random();

        shapes[0] = new Shape(new int[][] {
                // S shape
                {0, 1, 1},
                {1, 1, 0},
        }, this, Color.RED);

        shapes[1] = new Shape(new int[][] {
                // Z shape
                {1, 1, 0},
                {0, 1, 1},
        }, this, Color.BLUE);

        shapes[2] = new Shape(new int[][] {
                // T shape
                {1, 1, 1},
                {0, 1, 0},
        }, this, Color.ORANGE);

        shapes[3] = new Shape(new int[][] {
                // L shape
                {1, 1, 1},
                {1, 0, 0},
        }, this, Color.GREEN);

        shapes[4] = new Shape(new int[][] {
                // J shape
                {1, 1, 1},
                {0, 0, 1},
        }, this, Color.MAGENTA);

        shapes[5] = new Shape(new int[][] {
                // I shape
                {1, 1, 1, 1},
        }, this, Color.LIGHT_GRAY);

        shapes[6] = new Shape(new int[][] {
                // O shape
                {1, 1},
                {1, 1},
        }, this, Color.WHITE);

        currentShape = shapes[0];

        looper = new Timer(delay, new ActionListener() {
            int n = 0;
            @Override
            public void actionPerformed(ActionEvent e) {

                update();
                repaint();
            }
        });
        looper.start();
    }

    private void update() {
        currentShape.update();
    }

    public void setCurrentShape() {
        currentShape = shapes[random.nextInt(shapes.length)];
        currentShape.reset();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0,0,getWidth(),getHeight());

        currentShape.render(g);

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (board[row][col] != null) {
                    g.setColor(board[row][col]);
                    g.fillRect(col * BLOCK_SIZE, row * BLOCK_SIZE, BLOCK_SIZE, BLOCK_SIZE );
                }
            }
        }

        //draw board
        g.setColor(Color.white);
        for(int row = 0; row< BOARD_HEIGHT; row++) {
            g.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH, BLOCK_SIZE * row);
        }

        for (int col = 0; col < BOARD_WIDTH + 1; col++) {
            g.drawLine(BLOCK_SIZE * col, 0, BLOCK_SIZE * col, BLOCK_SIZE * BOARD_HEIGHT);
        }
    }

    public Color[][] getBoard() {
        return board;
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedUp();
        }
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            currentShape.moveRight();
        }
        else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            currentShape.moveLeft();
        }
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentShape.rotateShape();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentShape.speedDown();
        }
    }
}
