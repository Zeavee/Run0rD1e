package ch.epfl.sdp.SocialTests;

import android.util.Log;

import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.dependencies.DependencyProvider;
import ch.epfl.sdp.social.Conversation.ChatActivity;
import ch.epfl.sdp.social.Conversation.SocialRepository;
import static org.junit.Assert.*;
import ch.epfl.sdp.social.socialDatabase.Chat;
import ch.epfl.sdp.social.socialDatabase.Message;
import ch.epfl.sdp.social.socialDatabase.User;

import static java.util.Arrays.asList;

/**
 * @brief tests every functionality provided by SocialRepository.java.
 * Note that the activity is itself not tested but is used to provide context needed by the database builder used inside SocialRepository
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SocialRepositoryTest {

    // The users to add to the database
    private List<User> fantasticSix = asList(
            new User("amro@gmail.com"),
            new User("saoud@gmail.com"),
            new User("sacha@gmail.com"),
            new User("sen@gmail.com"),
            new User("peilin@gmail.com"),
            new User("rafael@gmail.com") );

    // container for the messages that are fetched
    private List<Message> messages = new ArrayList<>();

    @Rule
    public ActivityTestRule<ChatActivity> mActivityTestRule = new ActivityTestRule<ChatActivity>(ChatActivity.class){

        @Override
        protected void beforeActivityLaunched()
        {
            SocialRepository.setContextActivity(this.getActivity());
            prepopulateDatabase();

            // Mocking the server that loads remote messages
            DependencyProvider.remoteToSQLiteAdapter = MockServerToSQLiteAdapter.getInstance();
            DependencyProvider.remoteToSQLiteAdapter.setListener(this.getActivity());
        }
        @Override
        protected void afterActivityLaunched() {

        }

    };
    private SocialRepository testRepo;

    public void prepopulateDatabase()
    {
        // Pre-populate the database with sample users
        testRepo = SocialRepository.getInstance();
        fantasticSix.forEach(x -> testRepo.addUser(x));

        // Create the chat for each pair of users and mark each pair as friends
        fantasticSix.forEach(x ->{
            fantasticSix.forEach(y -> {
                if (!x.getEmail().equals(y.getEmail())){
                    testRepo.addChat(new Chat(x.getEmail(), y.getEmail()));
                }
                if (x.getEmail().compareTo(y.getEmail())<0) {
                    testRepo.addFriends(x, y);
                }
            });
        });

    }

    @Test
    public void AmroCanSendSachaMessage() throws InterruptedException {
        // get the conversation from Amro to Sacha
        Chat c = testRepo.getChat(fantasticSix.get(0).getEmail(), fantasticSix.get(2).getEmail());

        // send a joke message (should be taken lightly of course)
        testRepo.storeMessage("This code kills me, kills me", c.getChat_id());

        // Pretend storing the message from the db will take 1 second
        Thread.sleep(1000);

        // wait asynchronously until messages are fetched (these two calls should return the same thing)
        testRepo.getMessagesReceived(fantasticSix.get(0).getEmail(), fantasticSix.get(2).getEmail());

        // Pretend fetching twice the message from the db will take 2 second
        Thread.sleep(2000);

        String result = mActivityTestRule.getActivity().getMessages().get(0).getText();
        assertTrue(result.equals("This code kills me, kills me"));
    }

}
