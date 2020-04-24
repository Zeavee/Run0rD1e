package ch.epfl.sdp.SocialTests;

import com.google.firebase.Timestamp;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;
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
 * @brief tests conversation-relevant functionality provided by SocialRepository.java.
 * Note that the activity is itself not tested but is used to provide context needed by the database builder used inside SocialRepository
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class SocialRepositoryChatTest {

    // The users to add to the database
    private List<User> fantasticSix = asList(
            new User("amro@gmail.com"),
            new User("saoud@gmail.com"),
            new User("sacha@gmail.com"),
            new User("sen@gmail.com"),
            new User("peilin@gmail.com"),
            new User("rafael@gmail.com") );


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

    public void prepopulateDatabase() {
        // Pre-populate the database with sample users
        prepopulateDatabaseWithUserRecords();

        // Create the chat for each pair of users and mark each pair as friends
        for (User x : fantasticSix) {
            for (User y : fantasticSix) {
                    testRepo.addChat(new Chat(x.getEmail(), y.getEmail()));
                    testRepo.addFriends(x, y);
            }
        }
    }

    public void prepopulateDatabaseWithUserRecords()
    {
        testRepo = SocialRepository.getInstance();
        for (User user : fantasticSix) {
            testRepo.addUser(user);
        }
    }

    @Test
    public void AmroCanSendSachaMessage() throws InterruptedException {
        // get the conversation from Amro to Sacha
        Chat c = testRepo.getChat(fantasticSix.get(0).getEmail(), fantasticSix.get(2).getEmail());

        // send a joke message (should be taken lightly of course)
        testRepo.storeMessage(new Message(new Date(), "This code kills me, kills me", c.getChat_id()));

        // Pretend storing the message from the db will take 1 second
        Thread.sleep(1000);

        // wait asynchronously until messages are fetched (these two calls should return the same thing)
        testRepo.getMessagesExchanged(fantasticSix.get(0).getEmail(), fantasticSix.get(2).getEmail());

        // Pretend fetching twice the message from the db will take 2 second
        Thread.sleep(2000);

        String result = mActivityTestRule.getActivity().getMessages().get(0).getText();
        assertTrue(result.equals("This code kills me, kills me"));
    }

    @Test
    public void SachaCanReceiveRemoteMessage() throws InterruptedException {
        User sacha = fantasticSix.get(2);
        Chat c = testRepo.getChat(fantasticSix.get(0).getEmail(), sacha.getEmail());
        testRepo.insertMessageFromRemote(new Timestamp(new Date()), "Blessed",c.getChat_id());
        testRepo.getMessagesExchanged(fantasticSix.get(0).getEmail(), fantasticSix.get(2).getEmail());
        // Pretend fetching takes 2 seconds
        Thread.sleep(2000);
        String result = mActivityTestRule.getActivity().getMessages().get(1).getText();
        assertTrue(result.equals("Blessed"));
    }

}