package ch.epfl.sdp.social;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.epfl.sdp.R;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Friend} and makes a call to the
 * specified {@link FriendsListActivity.OnListFragmentInteractionListener}.
 */
public class MyFriendsRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendsRecyclerViewAdapter.FriendsViewHolder> {

    private final List<Friend> mValues;
    private final FriendsListActivity.OnListFragmentInteractionListener mListener;
    private static MyClickListener myClickListener;

    public MyFriendsRecyclerViewAdapter(List<Friend> items, FriendsListActivity.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    public void setOnItemClickListener(MyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }

    @Override
    public FriendsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_friends, parent, false);
        return new FriendsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final FriendsViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getUsername());

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final Button button;
        public final TextView mIdView;
        public final TextView mContentView;
        public Friend mItem;

        public FriendsViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);

            button = view.findViewById(R.id.chat_button);
            button.setOnClickListener(v -> myClickListener.onItemClick(getAdapterPosition(), v));
        }
    }
}
