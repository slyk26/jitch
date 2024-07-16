package at.slyk.gui.chat;

import at.slyk.PrefService;
import at.slyk.gui.PlaceholderTextField;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class InputBox extends JPanel {
    private static final String USERNAME = PrefService.getUsername();
    private static final PlaceholderTextField tf = new PlaceholderTextField("Send a message as " + USERNAME + "...");


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
    }
}
