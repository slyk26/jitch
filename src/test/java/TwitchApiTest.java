import at.slyk.PrefService;
import at.slyk.server.BackendServer;
import at.slyk.twitch.TwitchApi;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@Slf4j
public class TwitchApiTest {
    private final TwitchApi twitchApi = new TwitchApi();
    private final BackendServer backendServer = new BackendServer();

    private static String token = null;

    @Before
    public void setUp() {
        if (token != null) return;

        backendServer.start();
        twitchApi.login();
        log.info("Logging in - check Browser ...");
        await().atMost(60, SECONDS).until(() -> PrefService.getToken() != null);
        var res = PrefService.getToken();
        assert res != null;
        token = res;
    }

    @Test
    public void testMe() {
        var user = twitchApi.getMe();
        log.info("getMe: {}", user.getLogin());
        Assert.assertNotNull(user);
    }

    @Test
    public void testByUsername() {
        var wantedName = "forsen";
        var user = twitchApi.getUserByName(wantedName);
        Assert.assertEquals(wantedName, user.getLogin());
    }

    @Test
    public void testByUsernameFindNone() {
        var wantedName = "";
        var user = twitchApi.getUserByName(wantedName);
        Assert.assertNull(user);
    }

    @Test
    public void testSearchChannelsByName() {
        var wantedNameInList = "forsen";
        var channels = twitchApi.searchChannelsByName(wantedNameInList);
        var found = channels.stream().filter(channel -> channel.getBroadcasterLogin().equals(wantedNameInList)).findFirst();

        Assert.assertTrue(found.isPresent());
    }

    @Test
    public void testSearchChannelsByNameFindNone() {
        var wantedNameInList = "";
        var channels = twitchApi.searchChannelsByName(wantedNameInList);

        Assert.assertEquals(channels, List.of());
    }

    @Test
    public void testGetGlobalEmotes() {
        var emotes = twitchApi.getGlobalEmotes();

        Assert.assertNotNull(emotes);
    }

    @Test
    public void testGetChannelEmotes() {
        var channel = twitchApi.searchChannelsByName("forsen").getFirst();
        var channelEmotes = twitchApi.getChannelEmotes(channel.getId());

        Assert.assertNotNull(channelEmotes);
    }
}
