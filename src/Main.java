import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Nya Writer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new Display());
        frame.pack();
        frame.setVisible(true);
    }
}
