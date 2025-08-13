import View.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // The software runs from this class
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
