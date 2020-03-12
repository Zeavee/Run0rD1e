package ch.epfl.sdp;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

public class DefaultAuthenticationDisplay implements AuthenticationOutcomeDisplayVisitor {
        private Activity app;
        public DefaultAuthenticationDisplay(Activity app)
        {
            this.app = app;
        }
        @Override
        public void onSuccessfulAuthentication() {
            Toast.makeText(app, "Success!", Toast.LENGTH_SHORT);
            Intent myIntent = new Intent(app, MainActivity.class);
            app.startActivity(myIntent);
            app.finish();
        }

        @Override
        public void onFailedAuthentication() {
            Toast.makeText(app, "Failed!", Toast.LENGTH_SHORT);
        }
}
