package ch.epfl.sdp.logic;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ch.epfl.sdp.MainActivity;
import ch.epfl.sdp.R;
import ch.epfl.sdp.login.LoginFormActivity;

public class RulesActivityPage1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_page1);
    }

    public void btnPage2_OnClick(View view) {
        startActivity(new Intent(RulesActivityPage1.this, RulesActivityPage2.class));
        finish();
    }
}
