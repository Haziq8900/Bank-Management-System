import Backend.ATMSystem;
import Backend.User;
import View.MainFrame;

import javax.swing.*;
import java.util.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            new MainFrame();
        });
    }
}
