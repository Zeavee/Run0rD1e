package ch.epfl.sdp.SocialTests;

import android.util.Log;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import ch.epfl.sdp.dependencies.DependencyProvider;
import ch.epfl.sdp.social.Conversation.ChatActivity;
import ch.epfl.sdp.social.Conversation.SocialRepository;
import ch.epfl.sdp.social.FriendsListActivity;
import ch.epfl.sdp.social.socialDatabase.Chat;
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
    public ActivityTestRule<FriendsListActivity> mActivityTestRule = new ActivityTestRule<FriendsListActivity>(FriendsListActivity.class){

        @Override
        protected void beforeActivityLaunched()
        {
            SocialRepository.setContextActivity(this.getActivity());
            prepopulateDatabase();
            DependencyProvider.email = sampleUsers.get(0).getEmail();
        }
        @Override
        protected void afterActivityLaunched() {

        }

    };

    // Pre-populate the database with sample users
    public void prepopulateDatabase() {
        testRepo = SocialRepository.getInstance();
        for (User user : sampleUsers) {
            testRepo.addUser(user);
        }
        // Create the chat for each pair of users and mark each pair as friends
        for (User x : sampleUsers) {
            for (User y : sampleUsers) {
                if (x.getEmail().compareTo(y.getEmail()) < 0) {
                    testRepo.addFriends(x, y);
                }
            }
        }
    }

    @Test
    public void getFriendsOfAlice() throws InterruptedException {
        testRepo.fetchFriends(sampleUsers.get(0));
        // Pretend fetching friends takes 1 s
        Thread.sleep(1000);
        Log.d("real friend is ", mActivityTestRule.getActivity().getFriends().get(0).getEmail());
        assertTrue(mActivityTestRule.getActivity().getFriends().get(0).getEmail().equals(sampleUsers.get(1).getEmail()));
    }
}
