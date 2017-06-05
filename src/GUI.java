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

    public GUI(int board_length) {
        super(new GridLayout(board_length, board_length));

        side = board_length;
        from = null;
        board = new Board(board_length);

    }


    static void make_GUI(int n) {
        //pack lets the layout manager take care of sizing
        //you can either setpreferredsize and then pack, or explicity setsize and dont pack
        JFrame jframe = new JFrame("Ataxx");
        jframe.setPreferredSize(new Dimension(400, 400));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI gui = new GUI(n);
        jframe.getContentPane().add(gui);
        jframe.setResizable(false);
        jframe.pack();
        //gui.scale_board_image();
        jframe.setVisible(true);
    }

    void scale_board_image() {
        try {
            int height = this.getHeight();
            int width = this.getWidth();
            System.out.println("ok");
            System.out.println(height);
            red = ImageIO.read(new File("images/red.png"));
            System.out.println(red.getHeight());
            red = (BufferedImage) red.getScaledInstance(width, height, Image.SCALE_SMOOTH);


            blue = ImageIO.read(new File("images/blue.png"));
            blue = (BufferedImage) blue.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            empty = ImageIO.read(new File("images/empty.png"));
            empty = (BufferedImage) empty.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            //Todo kill self
            System.out.println(e);
            return;
        }

        repaint();
    }
    public void mousePressed(MouseEvent e) {
        Coordinate clicked = location_to_index(e.getX(), e.getY());
        int x = clicked.x;
        int y = clicked.y;
        
        if (from == null && board.get(x, y) == board.whoseturn()) {
            from = clicked;
        } else if (from != null && board.get(x, y) == Piece.EMPTY) {
            board.update(new Move(from, clicked));
            from = null;
        } else if (from == clicked) {
            from = null;
        }

        repaint();
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void paintComponent(Graphics g) {
        //g.drawImage(red, 0, 0, this);
        g.drawOval(0, 0, 50, 50);
        paint_grid(g);
    }

    public void paint_grid(Graphics g) {
        //number of horizontal/vertical lines to draw is side-1
        for (int i = 0; i < side; i++) {
            int temp = (this.getWidth() * i) / side;
            g.drawLine(temp, 0, temp, this.getWidth());
            g.drawLine(0, temp, this.getHeight(), temp);
        }
    }

    public void paint_pieces(Graphics g) {

    }
/*
    void update(Board board) {
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (board.get(i, j) == Piece.EMPTY) {
                    button_grid[i][j].setIcon(empty);
                } else if (board.get(i, j) == Piece.RED) {
                    button_grid[i][j].setIcon(red);
                } else {
                    button_grid[i][j].setIcon(blue);
                }
            }
        }

    }
*/
    Coordinate location_to_index(int x, int y) {
        return new Coordinate(x / side, y / side);
    }

    Coordinate index_to_location(int x, int y) {
        return new Coordinate((x * this.getWidth()) / side, (y * this.getHeight()) / side);
    }

}

/**
 * Game class no longer needed?
 */
