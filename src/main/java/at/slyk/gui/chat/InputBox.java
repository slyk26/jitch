package at.slyk.gui.chat;

import at.slyk.Main;
import at.slyk.PrefService;
import at.slyk.gui.common.PlaceholderTextField;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class InputBox extends JPanel {

    private static final String PLACEHOLDER = "Login in the Menubar first";
    private final PlaceholderTextField tf = new PlaceholderTextField(PLACEHOLDER);

    public InputBox(ChatPanel panelRef) {
        super(new BorderLayout());

        tf.addActionListener(e -> {
            if(e.getActionCommand().isBlank() || e.getActionCommand().isEmpty()) {
                return;
            }
            log.debug(e.getActionCommand());

            panelRef.sendMessage(e.getActionCommand());
            panelRef.addMessage(new Message(PrefService.getUsername(), e.getActionCommand(), false));
            tf.setText("");
            panelRef.pushDownScrollbar();
        });

        this.add(tf, BorderLayout.CENTER);

        Main.user.subscribe(u -> {
            if (u != null) {
                tf.setEnabled(true);
                tf.setPlaceholder("Send a message as " + u.getLoginName() + "...");
            } else {
                tf.setEnabled(false);
                tf.setPlaceholder(PLACEHOLDER);
            }
            tf.repaint();
        });
    }
}
