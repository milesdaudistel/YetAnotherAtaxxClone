/**
 * Created by miles on 5/31/17.
 */

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import java.io.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JComponent;
import javax.imageio.*;

import javax.swing.JButton;

public class GUI extends JPanel implements MouseListener {
    private BufferedImage red;
    private BufferedImage blue;
    private BufferedImage empty;
    private Coordinate from;

    private Board board;

    private int side;

    private int piece_diameter;

    public GUI(int board_length) {
        super(new GridLayout(board_length, board_length));

        side = board_length;
        from = null;
        board = new Board(board_length);
        addMouseListener(this);

    }




    static void make_GUI(int n, int width) {
        //pack lets the layout manager take care of sizing
        //you can either setpreferredsize and then pack, or explicity setsize and dont pack
        JFrame jframe = new JFrame("Ataxx");
        jframe.setPreferredSize(new Dimension(width, width));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI gui = new GUI(n);
        jframe.getContentPane().add(gui);
        jframe.setResizable(false);
        jframe.pack();
        gui.init_piece_diameter();
        jframe.setVisible(true);
    }

    public void mousePressed(MouseEvent e) {
        Coordinate clicked = location_to_index(e.getX(), e.getY());
        int x = clicked.x;
        int y = clicked.y;
        System.out.println("x: " + x + " y: " + y);
        
        if (from == null && board.get(x, y) == board.whoseturn()) {
            from = clicked;
            repaint();
        } else if (from != null && board.get(x, y) == Piece.EMPTY) {
            board.update(new Move(from, clicked));
            from = null;
            repaint();
        } else if (from == clicked) {
            from = null;
            repaint();
        }

    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void paintComponent(Graphics g) {
        //g.drawImage(red, 0, 0, this);
        //g.drawOval(0, 0, 50, 50);
        paint_grid(g);
        paint_pieces(g);
    }

    void paint_grid(Graphics g) {
        //number of horizontal/vertical lines to draw is side-1
        for (int i = 0; i < side; i++) {
            int temp = (this.getWidth() * i) / side;
            g.setColor(Color.black);
            g.drawLine(temp, 0, temp, this.getWidth());
            g.drawLine(0, temp, this.getHeight(), temp);
        }
    }

    void paint_pieces(Graphics g) {
        for (int j = 0; j < side; j++) {
            for (int i = 0; i < side; i++) {
                if (board.get(i, j) == Piece.RED) {
                    g.setColor(Color.red);
                    Coordinate location = index_to_location(i, j);
                    g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
                } else if (board.get(i, j) == Piece.BLUE) {
                    g.setColor(Color.blue);
                    Coordinate location = index_to_location(i, j);
                    g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
                } else if (board.get(i, j) == Piece.EMPTY) {
                    g.setColor(Color.white);
                    Coordinate location = index_to_location(i, j);
                    g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
                }
            }
        }
        if (from != null) {
            g.setColor(Color.gray);
            Coordinate location = index_to_location(from.x, from.y);
            g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
        }
    }

    void init_piece_diameter() {
        piece_diameter = this.getWidth() / side;
    }

    Coordinate location_to_index(int x, int y) {
        return new Coordinate(x / piece_diameter, y / piece_diameter);
    }

    Coordinate index_to_location(int x, int y) {
        return new Coordinate((x * this.getWidth()) / side, (y * this.getHeight()) / side);
    }

}

/**
 * Game class no longer needed?
 */
