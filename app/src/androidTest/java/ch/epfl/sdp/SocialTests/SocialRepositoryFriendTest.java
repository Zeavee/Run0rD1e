package ch.epfl.sdp.SocialTests;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.social.conversation.SocialRepository;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.social.socialDatabase.User;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Tests friend-fetching functionality provided by SocialRepository.java.
 * Note that the activity is itself not tested but is used to provide context needed by the database builder used inside SocialRepository
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SocialRepositoryFriendTest {

    private final List<User> sampleUsers = asList(
            new User("alice@gmail.com"),
            new User("bob@gmail.com"));
    private SocialRepository testRepo;
    @Rule
    public ActivityTestRule<FriendsListActivity> mActivityTestRule = new ActivityTestRule<FriendsListActivity>(FriendsListActivity.class) {

        @Override
        protected void beforeActivityLaunched() {
            PlayerManager.getInstance().setCurrentUser(new Player("mock", "mock@mock.com"));
            prepopulateDatabase();
        }
    };

    @Before
    public void setup() {
        SocialRepository.setContextActivity(mActivityTestRule.getActivity());
        SocialRepository.currentEmail = "mock@mock.com";
    }

    private void addUniqueFriendship(User x, User y) {
        if (x.getEmail().compareTo(y.getEmail()) < 0) {
            testRepo.addFriends(x, y);
        }
    }

    // Pre-populate the database with sample users
    public void prepopulateDatabase() {
        prepopulateDatabaseWithUserRecords();

        // Create the chat for each pair of users and mark each pair as friends
        for (User x : sampleUsers) {
            for (User y : sampleUsers) {
                addUniqueFriendship(x, y);
            }
        }
    }

    public void prepopulateDatabaseWithUserRecords() {

        testRepo = SocialRepository.getInstance();
        for (User user : sampleUsers) {
            testRepo.addUser(user);
        }
    }

    @Test
    public void getFriendsOfAlice() throws InterruptedException {
        testRepo.fetchFriends(sampleUsers.get(0));
        // Pretend fetching friends takes 1 s
        Thread.sleep(1000);

        assertEquals(mActivityTestRule.getActivity().getFriends().get(0).getEmail(), sampleUsers.get(1).getEmail());
    }
}
