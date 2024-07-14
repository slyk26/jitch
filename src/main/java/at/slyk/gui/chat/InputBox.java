package at.slyk.gui.chat;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class InputBox extends JPanel {
    private final JTextField tf;

    public InputBox(ChatPanel panelRef) {
        super(new BorderLayout());

        this.tf = new JTextField();

        this.tf.addActionListener(e -> {
            if(e.getActionCommand().isBlank() || e.getActionCommand().isEmpty()) {
                return;
            }
            log.debug(e.getActionCommand());

            panelRef.sendMessage(e.getActionCommand());
            panelRef.addMessage(new Message("slyk26", e.getActionCommand(), false));
            tf.setText("");
            panelRef.pushDownScrollbar();
        });

        this.add(tf, BorderLayout.CENTER);
    }
}
