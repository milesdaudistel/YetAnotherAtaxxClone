/**
 * Created by miles on 5/20/17.
 */
import java.io.*;
import java.util.Scanner;

public class Ataxx {
    public static void main(String [] args) {
        //Game g = new Game();

        PipedWriter cool;
        PipedReader uncool;
        BufferedWriter wr;
        BufferedReader re;
        try {
            cool = new PipedWriter();
            uncool = new PipedReader(cool);
            wr = new BufferedWriter(cool);
            re = new BufferedReader(uncool);

            /*
            GUI gui = new GUI(wr, 8);
            System.out.println("ok");
            Game g = new Game(gui, re, 8);
            */
            /*
            Scanner k = new Scanner(re);


            wr.write("asdfasdfasdfdsfadf\n");
            wr.write("asdfasdfasdfdsfadf\n");
            wr.flush();
            //wr.write("second time\n");
            System.out.println(re.readLine());
            System.out.println(k.nextLine());
            */

        } catch (IOException e) {
            System.out.println("I/O stream broke");
        }

    }
}
