package ch.epfl.sdp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Math.toRadians;

public class GameInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_info);

        TextView textView = findViewById(R.id.healthpoint_text);
        Player player = new Player(new GeoPoint(toRadians(6.14308), toRadians(46.21023)), 1.0, "admin", "admin@epfl.ch");
        textView.setText(String.valueOf(player.healthPoints));
    }
}