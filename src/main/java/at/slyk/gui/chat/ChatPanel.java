package at.slyk.gui.chat;

import javax.swing.*;
import java.awt.*;

public class ChatPanel extends JPanel {

    public static final int CHAT_WIDTH = 300;

    private final JPanel view;
    private final JScrollPane pane;

    public ChatPanel() {
        super(new BorderLayout());
        var wrapper = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.view = new JPanel();
        this.view.setLayout(new GridLayout(0, 1));
        this.view.setBackground(Color.GREEN);
        wrapper.add(this.view);
        this.pane = new JScrollPane(wrapper);
        this.pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.setPreferredSize(new Dimension(CHAT_WIDTH, this.getPreferredSize().height));

        this.add(this.pane, BorderLayout.CENTER);
        this.pushDownScrollbar();
        this.add(new InputBox(this), BorderLayout.SOUTH);
    }

    public void addMessage(Message msg) {
        var p = new JPanel(new FlowLayout(FlowLayout.LEFT));
        p.add(leftJustify(msg));
        this.view.add(leftJustify(p));
        this.validate();
    }

    private Component leftJustify(Component msg){
        Box b = Box.createHorizontalBox();
        b.add(msg);
        b.add(Box.createHorizontalGlue());
        return b;
    }

    public void pushDownScrollbar(){
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = this.pane.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }}
