package ch.epfl.sdp.logic;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.R;

public class RulesActivityPage1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page1);
        findViewById(R.id.btnPage2).setOnClickListener(v -> startActivity(new Intent(RulesActivityPage1.this, RulesActivityPage2.class)));
    }

}
