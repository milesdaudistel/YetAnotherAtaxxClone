/**
 * Created by miles on 5/20/17.
 */

public class Ataxx {
    public static void main(String [] args) {


        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                GUI.make_GUI(8, 700, true);
            }
        });

        //CLI c = new CLI(true);
    }
}
