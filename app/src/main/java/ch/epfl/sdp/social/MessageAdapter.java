package ch.epfl.sdp.social;

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

// MessageAdapter.java
public class MessageAdapter extends BaseAdapter {

    private List<ChatActivity.MessageDecorator> messages = new ArrayList<>();
    private String remote_user_id;
    private Context context;

    public MessageAdapter(Context context, String remote_user_id) {
        this.remote_user_id = remote_user_id;
        this.context = context;
    }

    public void add(ChatActivity.MessageDecorator message) {
        this.messages.add(message);
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

    public void sortMessages() {
        for (int i = 0; i < messages.size()-1; i++)
            for (int j = 0; j < messages.size()-i-1; j++)
                if (messages.get(j).getM().getDate().compareTo(messages.get(j+1).getM().getDate())>0)
                {
                    ChatActivity.MessageDecorator temp = messages.get(j);
                    messages.set(j, messages.get(j+1));
                    messages.set(j+1,temp);
                }
    }

}

class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}
