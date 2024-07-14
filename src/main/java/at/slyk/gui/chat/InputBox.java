package at.slyk.gui.chat;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

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

        this.tf.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
               if(e.isAltDown()) {
                   panelRef.setPaused(true);
               }
            }

            @Override
            public void keyReleased(KeyEvent e) {
               if(e.getKeyCode() == KeyEvent.VK_ALT){
                  panelRef.setPaused(false);
               }
            }
        });
        this.add(tf, BorderLayout.CENTER);
    }
}
