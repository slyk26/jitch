package at.slyk.gui;

import at.slyk.Main;
import at.slyk.twitch.TwitchApi;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

@Slf4j
public class MainMenuBar extends JMenuBar {

    private static final TwitchApi twitchApi = new TwitchApi();

    public MainMenuBar() {
        super();

        this.add(new AccountMenu());
    }

    static class AccountMenu extends JMenu {
        public AccountMenu() {
            super("Account");

            var login = new JMenuItem("Login");
            login.addActionListener(actionEvent -> twitchApi.login());
            this.add(login);

            var logout = new JMenuItem("Logout");
            logout.addActionListener(actionEvent -> Main.user.onNext(null));
            this.add(logout);

            Main.user.subscribe(u -> {
                log.debug("subscribe in MenuBar: {}", u);
                var loggedIn = (u != null && u.getAccessToken() != null && !u.getAccessToken().isBlank());
                login.setEnabled(!loggedIn);
                logout.setEnabled(loggedIn);
            });

        }
    }
}
