package presentation.Activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.mapapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import businessLogic.Controllers.MapController;

public class MapActivity extends AppCompatActivity implements  OnMapReadyCallback {
    private static final String TAG = "MapActivity";
    private static final float  DEFAULT_ZOOM=4.3f;
    //provitional LatLong, later this data will come from data base
    private static double lat=4.0000000;
    private static double longitud=-72.0000000;
    //provitional markers for demostration
    private Marker marker0;
    private Marker marker1;
    private ArrayList<Marker> markerArrayList=new ArrayList<>();
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        Log.d(TAG,"initMap: initializing map");
        SupportMapFragment mapFragment=(SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapActivity.this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this,"map is ready", Toast.LENGTH_LONG).show();
        Log.d(TAG,"onMapReady: map is ready");
        mMap=googleMap;
        MapController.moveCamera(mMap,new LatLng(lat,longitud),DEFAULT_ZOOM);
        //putting markers on default demostration places
        marker0=MapController.makeMarker(mMap,new LatLng(4.71098599,-74.072092),"Bogotá,Colombia");
        markerArrayList.add(marker0);

        marker1=MapController.makeMarker(mMap,new LatLng(6.244203,-75.5812119),"Medellín, Colombia");
        markerArrayList.add(marker1);

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                for(Marker m:markerArrayList)
                {
                    if(marker.equals(m))
                    {
                        m.showInfoWindow();
                        Toast.makeText(getApplicationContext(),m.getTitle(),Toast.LENGTH_LONG).show();
                    }

                }
                return true;
            }
        });

        //address can be null if google doesn´t find any place or if the geolocate service is no available
    }
}
