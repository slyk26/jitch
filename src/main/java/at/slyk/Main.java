package at.slyk;

import at.slyk.gui.MainFrame;
import at.slyk.server.BackendServer;
import com.github.weisj.darklaf.LafManager;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        LafManager.install();
        new BackendServer().start();
        SwingUtilities.invokeLater(MainFrame::new);
    }
}