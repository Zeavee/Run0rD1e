package ch.epfl.sdp;

import android.app.Activity;

public interface OfflineAble  {
    void switchMode(ConnectionMode cm);
    Activity getActivity();
}
