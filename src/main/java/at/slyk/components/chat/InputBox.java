package at.slyk.components.chat;

import lombok.extern.log4j.Log4j2;

import javax.swing.*;
import java.awt.*;

@Log4j2
public class InputBox extends JPanel {
    private final JTextField tf;

    public InputBox(ChatLog logRef) {
        super(new BorderLayout());

        this.tf = new JTextField();

        // add messaage
        this.tf.addActionListener(e -> {
            log.info(e);
            logRef.addMessage(new Message("slyk26", tf.getText()));
            tf.setText("");
            logRef.pushDownScrollbar();
        });

        this.add(tf, BorderLayout.CENTER);
    }
}
