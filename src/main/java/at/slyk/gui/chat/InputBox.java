package at.slyk.gui.chat;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;

@Log4j2
public class InputBox extends JPanel {
    private final JTextField tf;

    public InputBox(ChatPanel panelRef) {
        super(new BorderLayout());

        this.tf = new JTextField();

        this.tf.addActionListener(e -> {
            if(e.getActionCommand().isBlank() || e.getActionCommand().isEmpty()) {
                return;
            }
            log.debug(e);
            panelRef.addMessage(new Message("slyk26", e.getActionCommand()));
            tf.setText("");
            panelRef.pushDownScrollbar();
        });

        this.add(tf, BorderLayout.CENTER);
    }
}
