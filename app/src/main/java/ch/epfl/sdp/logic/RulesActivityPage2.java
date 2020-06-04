package ch.epfl.sdp.logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;

public class RulesActivityPage2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page2);
    }

    public void btnPage1_OnClick(View view) {
        startActivity(new Intent(RulesActivityPage2.this, RulesActivityPage1.class));
        finish();
    }

    public void btnStartSite_OnClick(View view) {
        startActivity(new Intent(RulesActivityPage2.this, MainActivity.class));
        finish();
    }
}
