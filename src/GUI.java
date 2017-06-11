/**
 * Created by miles on 5/31/17.
 */

import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUI extends JPanel implements MouseListener {
    private Coordinate from;

    private Board board;

    private int side;

    private int piece_diameter;

    private boolean useAI;

    private GUI(int board_length) {
        super(new GridLayout(board_length, board_length));

        side = board_length;
        from = null;
        board = new Board(board_length);
        addMouseListener(this);

        useAI = false;
    }

    private GUI(int board_length, boolean AI) {
        super(new GridLayout(board_length, board_length));

        side = board_length;
        from = null;
        board = new Board(board_length);
        addMouseListener(this);

        useAI = AI;
    }

    static void make_GUI(int n, int width, boolean AI) {
        //pack lets the layout manager take care of sizing
        //you can either setpreferredsize and then pack, or explicity setsize and dont pack
        JFrame jframe = new JFrame("Ataxx");
        jframe.setPreferredSize(new Dimension(width, width));
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GUI gui;
        if (AI) {
            gui = new GUI(n, AI);
        } else {
            gui = new GUI(n);
        }
        jframe.getContentPane().add(gui);
        jframe.setResizable(false);
        jframe.pack();
        gui.init_piece_diameter();
        jframe.setVisible(true);
    }

    public void mousePressed(MouseEvent e) {
        Coordinate selected = location_to_index(e.getX(), e.getY());

        if (from == null && board.get(selected) == board.whoseturn()) {
            from = selected;
            repaint();
        } else if (from != null && board.get(selected) == Piece.EMPTY) {
            board.update(new Move(from, selected));
            from = null;
            repaint();

            if (useAI) {
                board.call_AI();
                repaint();
            }

        } else if (from != null && from.equals(selected)) {
            from = null;
            repaint();
        }

    }

    public void mouseReleased(MouseEvent e) {}

    public void mouseEntered(MouseEvent e) {}

    public void mouseExited(MouseEvent e) {}

    public void mouseClicked(MouseEvent e) {}

    public void paintComponent(Graphics g) {
        paint_grid(g);
        paint_pieces(g);
    }

    private void paint_grid(Graphics g) {
        //number of horizontal/vertical lines to draw is side-1
        for (int i = 0; i < side; i++) {
            int temp = (this.getWidth() * i) / side;
            g.setColor(Color.black);
            g.drawLine(temp, 0, temp, this.getWidth());
            g.drawLine(0, temp, this.getHeight(), temp);
        }
    }

    private void paint_pieces(Graphics g) {
        for (int j = 0; j < side; j++) {
            for (int i = 0; i < side; i++) {
                Coordinate location = index_to_location(i, j);

                if (board.get(i, j) == Piece.RED) {
                    g.setColor(Color.red);
                } else if (board.get(i, j) == Piece.BLUE) {
                    g.setColor(Color.blue);
                } else if (board.get(i, j) == Piece.EMPTY) {
                    //this erases parts of the default light gray color of the jpanel, but it looks
                    //cool, so its a feature.  kind of reminds me of connect 4
                    g.setColor(Color.white);
                }

                g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
            }
        }
        if (from != null) {
            g.setColor(Color.gray);
            Coordinate location = index_to_location(from.x, from.y);
            g.fillOval(location.x, location.y, piece_diameter, piece_diameter);
        }
    }

    private void init_piece_diameter() {
        piece_diameter = this.getWidth() / side;
    }

    private Coordinate location_to_index(int x, int y) {
        return new Coordinate(x / piece_diameter, y / piece_diameter);
    }

    private Coordinate index_to_location(int x, int y) {
        return new Coordinate((x * this.getWidth()) / side, (y * this.getHeight()) / side);
    }

}
