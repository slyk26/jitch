package at.slyk.gui;

import at.slyk.twitch.TwitchApi;
import lombok.extern.log4j.Log4j2;

import javax.swing.*;

public class MainMenuBar extends JMenuBar {

    private static final TwitchApi twitchApi = new TwitchApi();

    public MainMenuBar() {
        super();

        this.add(new AccountMenu());
        this.add(new ApiMenu());
    }

    static class AccountMenu extends JMenu {
        public AccountMenu() {
            super("Account");

            var login = new JMenuItem("Login");
            login.addActionListener(actionEvent -> twitchApi.login());
            this.add(login);
        }
    }

    @Log4j2
    static class ApiMenu extends JMenu {
        public ApiMenu() {
            super("Api");

            var user = new JMenuItem("Twitch getUser");
            user.addActionListener(actionEvent -> log.debug(twitchApi.getUserByName("siyk26")));
            this.add(user);
        }
    }
}