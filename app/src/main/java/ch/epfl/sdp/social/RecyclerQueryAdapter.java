package ch.epfl.sdp.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.social.friends_firestore.RemoteFriendFetcher;
import ch.epfl.sdp.social.friends_firestore.WaitsOnUserFetch;

public class RecyclerQueryAdapter  extends RecyclerView.Adapter<RecyclerQueryAdapter.ViewHolder> implements WaitsOnUserFetch {

    private List<User> friendsList;
    private RemoteFriendFetcher server;

    public RecyclerQueryAdapter(List<User> friendsList, RemoteFriendFetcher server) {
        this.friendsList = friendsList;
        this.server = server;
        this.server.setListener(this);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.row_item_query, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.usernameTextView.setText(friendsList.get(position).getUsername());
        holder.emailTextView.setText(friendsList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {
        return friendsList.size();
    }


    @Override
    public void signalFriendsFetched(List<User> fetched_friends) {
        friendsList = fetched_friends;
        notifyDataSetChanged();
    }

    @Override
    public void updateSearch(String friendQuery) {
        server.getFriendsFromServer(friendQuery);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private ImageView imageView;
        private TextView emailTextView, usernameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView5);
            emailTextView = itemView.findViewById(R.id.textViewEmail);
            usernameTextView = itemView.findViewById(R.id.textViewUsername);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(new View.OnLongClickListener(){

                @Override
                public boolean onLongClick(View v) {
                    friendsList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    return true;
                }
            });
        }

        /// Here is where you add that the user become friends in both FireStore and SQLite
        @Override
        public void onClick(View v) {
            // If it is not created already
            ChatRepository singleton = ChatRepository.createRepo(v.getContext());

            // Let it know which UI context thread to run on
            ChatRepository.setContextActivity(v.getContext());

            completeDBSetup();
            Toast.makeText(v.getContext(), friendsList.get(getAdapterPosition()).getUsername() + " added as friend" , Toast.LENGTH_SHORT).show();
        }

        private void completeDBSetup()
        {
            // Add friends TODO: Figure out a clean way to get current user instead of relying on hard coded value amro.abdrabo@gmail.com
            User cur_usr = new User("amro.abdrabo@gmail.com");
            User befriended_usr = new User(friendsList.get(getAdapterPosition()).getEmail());
            ChatRepository.addUser(cur_usr);
            ChatRepository.addUser(befriended_usr);
            ChatRepository.addChat(new Chat(cur_usr.getEmail(), befriended_usr.getEmail()));
            ChatRepository.addChat(new Chat(befriended_usr.getEmail(), cur_usr.getEmail()));
            ChatRepository.addFriends(befriended_usr, cur_usr); // symmetry handled in called function
        }
    }
}
