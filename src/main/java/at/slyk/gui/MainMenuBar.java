package at.slyk.gui;

import at.slyk.PrefService;
import at.slyk.twitch.TwitchApi;

import javax.swing.*;


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
            logout.addActionListener(actionEvent -> PrefService.invalidate());
            this.add(logout);
        }
    }
}
