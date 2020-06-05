package ch.epfl.sdp.social.conversation;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.socialDatabase.Message;

/**
 * This class adapts the messages, so we can add them in a list and give this to the recycler view
 */
public class MessageAdapter extends BaseAdapter {

    private final List<ChatActivity.MessageDecorator> messages = new ArrayList<>();
    private final String remote_user_id;
    private final Context context;

    /**
     *
     * @param context The context in which we want to display the messages (the activity)
     * @param remote_user_id The name of the user
     */
    MessageAdapter(Context context, String remote_user_id) {
        this.remote_user_id = remote_user_id;
        this.context = context;
    }

    /**
     * This method adds a message in the list that we display
     * @param message The message we want to add
     */
    public void add(ChatActivity.MessageDecorator message) {
        int i = 0;
        while (messages.size() > i && messages.get(i).getM().getDate().compareTo(message.getM().getDate()) <= 0) {
            ++i;
        }
        this.messages.add(i, message);
        notifyDataSetChanged(); // to render the list we need to notify
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    // This is the backbone of the class, it handles the creation of single ListView row (chat bubble)
    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        Message message = messages.get(i).getM();

        if (!messages.get(i).isIncoming()) { // this message was sent by us so let's create a basic chat bubble on the right
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(message.getText());
        } else { // this message was sent by someone else so let's create an advanced chat bubble on the left
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.avatar = convertView.findViewById(R.id.avatar);
            holder.name = convertView.findViewById(R.id.name);
            holder.messageBody = convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(remote_user_id);
            holder.messageBody.setText(message.getText());
        }

        return convertView;
    }
}

class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}