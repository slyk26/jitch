package at.slyk;

import at.slyk.components.MainFrame;
import com.github.weisj.darklaf.LafManager;

public class Main {
    public static void main(String[] args) {

        LafManager.install();
        javax.swing.SwingUtilities.invokeLater(MainFrame::new);
    }
}