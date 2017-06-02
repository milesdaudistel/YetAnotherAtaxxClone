/**
 * Created by miles on 5/31/17.
 */
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.Toolkit;
import java.awt.BorderLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import java.io.Writer;
import java.io.BufferedWriter;

public class GUI extends JPanel implements ActionListener {

    BufferedWriter wr;

    JButton selected_button;
    JButton[][] button_grid;

    public GUI(Writer r, int n) {
        super(new GridLayout(n, n));
        wr = new BufferedWriter(r);
        selected_button = null;
        button_grid = new JButton[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                JButton b = new JButton();
                b.addActionListener(this);
                add(b);
                button_grid[i][j] = b;
            }
        }
    }

    static void make_GUI(Writer r, int n) {
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
        if (selected_button == null) {
            //get the button that was pushed, set it to selected button
            //change the image of selected button to show highlight
            // TODO
            // this is probably unsafe.  What if e.getSource() isn't a jbutton?  what then?
            selected_button = (JButton) e.getSource();
        } else if (e.getSource() == selected_button) {
            //
            //change selected button back to normal image
            selected_button = null;
        } else {
            //give move to game
            //change selected button back to normal image
            selected_button = null;
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
