/**
 * Created by miles on 5/31/17.
 */

import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

import java.io.*;

import javax.imageio.*;

import java.awt.image.BufferedImage;

public class GUI extends JPanel implements MouseListener {

    private BufferedWriter wr;

    private Coordinate selected;

    private BufferedImage red;
    private BufferedImage blue;
    private BufferedImage empty;
    private BufferedImage selected;

    private Game g;

    private int side;
    private int width;
    private int height;

    public GUI(int board_length) {
        super(new GridLayout(board_length, board_length));

        PipedWriter cool = new PipedWriter();
        PipedReader uncool;
        try {
            uncool = new PipedReader(cool);
        } catch (IOException e) {
            System.out.println("pipedreader creation failed");
            return;
        }
        wr = new BufferedWriter(cool);
        BufferedReader re = new BufferedReader(uncool);

        side = board_length;
        selected = null;
        button_grid = new JButton[side][side];

        g = new Game(true, re, side);

        try {
            height = this.getHeight();
            width = this.getWidth();

            red = ImageIO.read(new File("images/red.png"));
            red = (BufferedImage) red.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            blue = ImageIO.read(new File("images/blue.png"));
            blue = (BufferedImage) blue.getScaledInstance(width, height, Image.SCALE_SMOOTH);

            empty = ImageIO.read(new File("images/empty.png"));
            empty = (BufferedImage) empty.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        } catch (IOException e) {
            //kill self
            return;
        }

        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                JButton b = new JButton(i + " " + j, empty);
                b.addActionListener(this);
                add(b);
                button_grid[i][j] = b;
            }
        }

        button_grid[0][0].setIcon(red);
        button_grid[0][side-1].setIcon(blue);
        button_grid[side-1][0].setIcon(blue);
        button_grid[side-1][side-1].setIcon(red);



    }


    static void make_GUI(int n) {
        JFrame jframe = new JFrame("Ataxx");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setResizable(false);
        JComponent gui = new GUI(n);
        gui.setOpaque(true);
        jframe.setContentPane(gui);
        jframe.pack();
        jframe.setVisible(true);
    }

    public void mousePressed(MouseEvent e) {
        Coordinate xy = location_to_index(e.getX(), e.getY());
        int x = xy.x;
        int y = xy.y;
        
        if (selected == null && board().get(x, y) == g.g.whoseturn()()) {
            selected = button_pushed;
            selected.setIcon(selected);
            //selected.setEnabled(false);
        } else if (selected != null && button_pushed.getIcon() == empty) {
            g.process_gui(this, "move " + selected.getText() + " " + button_pushed.getText());
            //selected.setEnabled(true);
            selected = null;
        } else if (button_pushed == selected) {
            //selected.setEnabled(true);
            selected.setIcon(g.whoseturn());
            selected = null;
        }
    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

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

        if (g.whoseturn() == red) {
            g.whoseturn() = blue;
        } else {
            g.whoseturn() = red;
        }

        this.repaint();
    }

    Coordinate location_to_index(int x, int y) {
        return new Coordinate(x / side, y / side);
    }

    Coordinate index_to_location(int x, int y) {
        return new Coordinate((x * width) / side, (y * height) / side);
    }

    Board board() {
        return g.board();
    }
}

/**
 * Game class no longer needed?
 */
