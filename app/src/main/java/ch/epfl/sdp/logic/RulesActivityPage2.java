package ch.epfl.sdp.logic;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;

public class RulesActivityPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page2);

        findViewById(R.id.btnPage1).setOnClickListener(v -> startActivity(new Intent(RulesActivityPage2.this, RulesActivityPage1.class)));

        findViewById(R.id.btnStartSite).setOnClickListener(v -> startActivity(new Intent(RulesActivityPage2.this, MainActivity.class)));
    }
}
