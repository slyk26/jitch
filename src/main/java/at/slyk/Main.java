package at.slyk;

import at.slyk.common.User;
import at.slyk.gui.MainFrame;
import at.slyk.server.BackendServer;
import com.github.weisj.darklaf.LafManager;
import com.github.weisj.darklaf.theme.OneDarkTheme;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import raven.emoji.EmojiIcon;
import rx.subjects.PublishSubject;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

@Slf4j
public class Main {
    public static final PublishSubject<User> user = PublishSubject.create();

    public static void main(String[] args) {
        user.subscribe(u -> {
            if (u != null) {
                log.debug("setting Prefs: {}", u);
                PrefService.setUsername(u.getLoginName());
                PrefService.setToken(u.getAccessToken());
            } else {
                log.debug("removing Prefs");
                PrefService.invalidate();
            }
        });

        EmojiIcon.getInstance().installEmojiSvg();
        LafManager.setTheme(new OneDarkTheme());
        LafManager.install();
        new BackendServer().start();
        SwingUtilities.invokeLater(() -> getMainFrame().setVisible(true));
    }

    private static @NotNull MainFrame getMainFrame() {
        var frame = new MainFrame();

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                log.debug("setting user variable from prefs");
                if (PrefService.getUsername() == null && PrefService.getToken() == null) {
                    user.onNext(null);
                } else {
                    user.onNext(new User(PrefService.getUsername(), PrefService.getToken()));
                }
            }
        });
        return frame;
    }
}