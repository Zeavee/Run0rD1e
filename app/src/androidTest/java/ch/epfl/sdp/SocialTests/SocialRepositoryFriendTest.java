package ch.epfl.sdp.SocialTests;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.entity.PlayerManager;
import ch.epfl.sdp.social.Conversation.SocialRepository;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.social.socialDatabase.User;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertTrue;

/**
 * @brief tests friend-fetching functionality provided by SocialRepository.java.
 * Note that the activity is itself not tested but is used to provide context needed by the database builder used inside SocialRepository
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SocialRepositoryFriendTest {

    private List<User> sampleUsers = asList(
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
        SocialRepository.setEmail("mock@mock.com");
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

        assertTrue(mActivityTestRule.getActivity().getFriends().get(0).getEmail().equals(sampleUsers.get(1).getEmail()));
    }
}
