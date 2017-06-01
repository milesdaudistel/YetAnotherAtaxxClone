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

    public GUI(Writer r, int n) {
        super(new GridLayout(n, n));
        wr = new BufferedWriter(r);
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
        return;
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
     * update(move, valid):
     * update your buttons and stuff
     * deselect piece that was previously clicked
     * if valid is true, change the pieces around, if its not, theres no need
     * all you need is the move.  will be faster than updating the entire board
     */
}
