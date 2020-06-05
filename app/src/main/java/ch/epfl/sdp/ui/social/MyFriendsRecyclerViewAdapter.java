package ch.epfl.sdp.ui.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;
import ch.epfl.sdp.database.room.social.User;

public class MyFriendsRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendsRecyclerViewAdapter.FriendsViewHolder> {

    private final List<User> mValues;
    private static MyClickListener myClickListener;

    /**
     * This method creates a recycler view adapter
     * @param items the list of users we will see in our friends list
     */
    MyFriendsRecyclerViewAdapter(List<User> items) {
        mValues = items;
    }

    /**
     * This method sets our click listener
     * @param myClickListener The click listener that we want to use
     */
    void setOnItemClickListener(MyClickListener myClickListener) {
        MyFriendsRecyclerViewAdapter.myClickListener = myClickListener;
    }

    /**
     * This interface is a click listener that will lock for clicks on user in the friends list
     */
    public interface MyClickListener {
        void onItemClick(int position, View v);
    }

    @NonNull
    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friends, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendsViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getEmail());

        holder.mView.setOnClickListener(v -> {
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    static class FriendsViewHolder extends RecyclerView.ViewHolder {
        final View mView;
        final Button button;
        final TextView mIdView;
        final TextView mContentView;
        User mItem;

        FriendsViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);

            button = view.findViewById(R.id.chat_button);
            button.setOnClickListener(v -> myClickListener.onItemClick(getAdapterPosition(), v));
        }
    }
}