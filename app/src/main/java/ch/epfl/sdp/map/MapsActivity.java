package ch.epfl.sdp.map;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import ch.epfl.sdp.R;
import ch.epfl.sdp.entity.Player;
import ch.epfl.sdp.game.Game;
import ch.epfl.sdp.item.Healthpack;
import ch.epfl.sdp.item.Item;
import ch.epfl.sdp.item.Scan;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    public static final String TAG = "Test Firebase";
    public static final MapApi mapApi = new GoogleMapApi();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Button mapButton = findViewById(R.id.recenter);
        mapButton.setOnClickListener(v -> mapApi.moveCameraOnCurrentLocation());

        Button scanButton = findViewById(R.id.scanButton);
        Scan scan = new Scan(new GeoPoint(9.34324, 47.24942), true, 30, mapApi);
        scanButton.setOnClickListener(v -> scan.showAllPlayers());

        mapApi.initializeApi((LocationManager) getSystemService(Context.LOCATION_SERVICE), this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        ((GoogleMapApi) mapApi).setMap(googleMap);
        mapApi.updatePosition();
        Item hp = new Healthpack(new GeoPoint(7.9592, 47.0407), false, 10);
        Game.addToDisplayList(hp);
        Game.addToUpdateList(hp);

        Player player = new Player(7.9592, 47.0407, 22, "startGame", "startGame@gmail.com");
        Map<String, Object> docData = new HashMap<>();
        docData.put("player", player);
        docData.put("timestamp", FieldValue.serverTimestamp());
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Players")
                .add(docData)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });

    }
}
