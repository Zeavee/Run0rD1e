package ch.epfl.sdp.database.room;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AppDAO {
    /**
     * Return the LiveData which holds a list of leaderBoardEntity that can be observed with a given lifecycle.
     * Always holds/cached latest version of data. Notifies its active observers when the data has changed.
     * Since we select the all leaderBoard_table, we are notified whenever any of the leaderBoard_table contents have changed.
     *
     * @return LiveData which holds a list of leaderBoardEntity
     */
    @Query("SELECT * from leaderBoard_table ORDER BY generalScore DESC")
    LiveData<List<GeneralLeaderBoardEntity>> getGeneralLeaderBoard();

    /**
     * Insert a leaderBoardEntity to the leaderBoard_table
     *
     * @param player A leaderBoardEntity object used to be insert to the leaderBoard_table
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertToGeneralLeaderBoard(GeneralLeaderBoardEntity player);

    /**
     * Delete all the contents from leaderBoard_table
     */
    @Query("DELETE FROM leaderBoard_table")
    void deleteAllFromGeneralLeaderBoard();
}