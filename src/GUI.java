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

import java.io.BufferedWriter;
import java.io.IOException;

import javax.swing.ImageIcon;

public class GUI extends JPanel implements ActionListener {

    private BufferedWriter wr;

    private JButton selected_button;
    private JButton[][] button_grid;

    private ImageIcon red;
    private ImageIcon blue;
    private ImageIcon empty;
    private ImageIcon whoseturn;

    int dim;

    public GUI(BufferedWriter r, int n) {
        super(new GridLayout(n, n));
        dim = n;
        wr = r;
        selected_button = null;
        button_grid = new JButton[dim][dim];

        red = new ImageIcon("images/red.png");
        blue = new ImageIcon("images/blue.png");
        empty = new ImageIcon("images/empty.png");
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

    static void make_GUI(BufferedWriter r, int n) {
        JFrame jframe = new JFrame("Ataxx");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JComponent gui = new GUI(r, n);
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
            //get the button that was pushed, set it to selected button
            //change the image of selected button to show highlight
            // TODO
            // this is probably unsafe.  What if e.getSource() isn't a jbutton?  what then?

            selected_button = button_pushed;
            selected_button.setEnabled(false);
        } else if (selected_button != null && button_pushed.getIcon() == empty) {
            //give move to game
            try {
                wr.write("move " + selected_button.getText() + button_pushed.getText());
                wr.flush();
                selected_button.setEnabled(true);
                selected_button = null;
            } catch (IOException f) {
                System.out.println("GUI failed to give move to game");
            }
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
