package at.slyk.components.chat;

import javax.swing.*;

public class ChatLog extends JScrollPane {

    private static JList<Message> log;
    private static DefaultListModel<Message> model;

    public ChatLog() {
        super(initList());
        this.setVerticalScrollBarPolicy(VERTICAL_SCROLLBAR_ALWAYS);
        ChatLog.log.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private static JList<Message> initList(){
        ChatLog.model = new DefaultListModel<>();

        ChatLog.log = new JList<>(model);
        return ChatLog.log;
    }

    public void addMessage(Message msg){
        ChatLog.model.addElement(msg);
    }

    public void pushDownScrollbar(){
        SwingUtilities.invokeLater(() -> {
            JScrollBar bar = this.getVerticalScrollBar();
            bar.setValue(bar.getMaximum());
        });
    }
}
