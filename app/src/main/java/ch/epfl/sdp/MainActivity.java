package ch.epfl.sdp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Locate the button in activity_main.xml
        Button button = findViewById(R.id.mainGoButton);

        // Capture button clicks
        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, GameInfoActivity.class);
            startActivity(intent);
        });
    }
}
