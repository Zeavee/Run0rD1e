package ch.epfl.sdp.ui.leader_board;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import ch.epfl.sdp.R;

/**
 * A Fragment used to show the leaderBoard of the current game round which ranks the current game score of all the players in this game round
 */
public class CurrentGameLeaderBoardFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingame_leaderboard, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        currentGameLeaderBoard();
    }

    /**
     * Initialize the recycleView of currentGameLeaderBoard, set the LinearLayoutManager and CurrentGameLeaderBoardAdapter
     */
    private void currentGameLeaderBoard() {
        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.ingame_leaderboard_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new CurrentGameLeaderBoardAdapter());
    }

}
