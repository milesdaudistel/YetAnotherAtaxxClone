/**
 * Created by miles on 5/31/17.
 */
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.*;

import javax.swing.ImageIcon;

public class GUI extends JPanel implements ActionListener {

    private BufferedWriter wr;

    private JButton selected_button;
    private JButton[][] button_grid;

    private ImageIcon red;
    private ImageIcon blue;
    private ImageIcon empty;
    private ImageIcon whoseturn;
    private ImageIcon selected;

    private Game g;

    int dim;

    public GUI(int n) {
        super(new GridLayout(n, n));

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

        dim = n;
        selected_button = null;
        button_grid = new JButton[dim][dim];

        g = new Game(true, re, dim);

        red = new ImageIcon(this.getClass().getResource("/images/red.png"));
        blue = new ImageIcon(this.getClass().getResource("/images/blue.png"));
        empty = new ImageIcon(this.getClass().getResource("/images/empty.png"));
        selected = new ImageIcon(this.getClass().getResource("/images/selected.png"));
        whoseturn = red;

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                JButton b = new JButton(i + " " + j, empty);
                b.addActionListener(this);
                add(b);
                button_grid[i][j] = b;
            }
        }

        button_grid[0][0].setIcon(red);
        button_grid[0][dim-1].setIcon(blue);
        button_grid[dim-1][0].setIcon(blue);
        button_grid[dim-1][dim-1].setIcon(red);

    }

    static void make_GUI(int n) {
        JFrame jframe = new JFrame("Ataxx");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent gui = new GUI(n);
        gui.setOpaque(true);
        jframe.setContentPane(gui);
        jframe.pack();
        jframe.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button_pushed = (JButton) e.getSource();
        //if we don't have a selection, and the button pushed is not empty
        //else, if we have a selection, and the button pushed is empty
        if (selected_button == null && button_pushed.getIcon() == whoseturn) {
            selected_button = button_pushed;
            selected_button.setIcon(selected);
            //selected_button.setEnabled(false);
        } else if (selected_button != null && button_pushed.getIcon() == empty) {
            g.process_gui(this, "move " + selected_button.getText() + " " + button_pushed.getText());
            //selected_button.setEnabled(true);
            selected_button = null;
        } else if (button_pushed == selected_button) {
            //selected_button.setEnabled(true);
            selected_button.setIcon(whoseturn);
            selected_button = null;
        }
    }

    void update(Board board) {
        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                if (board.get(i, j) == Piece.EMPTY) {
                    button_grid[i][j].setIcon(empty);
                } else if (board.get(i, j) == Piece.RED) {
                    button_grid[i][j].setIcon(red);
                } else {
                    button_grid[i][j].setIcon(blue);
                }
            }
        }

        if (whoseturn == red) {
            whoseturn = blue;
        } else {
            whoseturn = red;
        }

        this.repaint();
    }
    /**
     * public GUI(pipedreader, n)
     * creates a pipedwriter to connect to pipedreader
     * create buttons in nxn grid
     *
     *
     * actionPerformed:
     * after user has clicked somewhere, put where they clicked into writer
     *
     * assumes move is valid
     * if not, update should not be called
     * update(move):  updates your buttons and stuff
     */
}
