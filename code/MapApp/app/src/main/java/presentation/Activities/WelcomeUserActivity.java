package presentation.Activities;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;


import java.util.ArrayList;

import businessLogic.Controllers.ControlResult;
import businessLogic.Controllers.MapController;
import businessLogic.Controllers.SeePlacesController;
import dataAccess.Models.Place;
import dataAccess.Models.User;

public class WelcomeUserActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeUserActivity";
    private static final int ERROR_DIALOG_REQUEST =9001;
    private User user;
    private String id,name;
    private final String userKey = "user";
    private final String placesKey = "places";

    class getSeasonPlacesTask extends AsyncTask<Void, Void, Pair<ArrayList<Place>, ControlResult>> {

        getSeasonPlacesTask(Context context) {

            this.context = context;
        }

        @Override
        protected Pair<ArrayList<Place>, ControlResult> doInBackground(Void... voids) {

            return MapController.getSeasonPlaces(context);
        }

        @Override
        protected void onPostExecute(Pair<ArrayList<Place>, ControlResult> resultPair){

            if(resultPair.second == ControlResult.CONNECT_ERROR){

                Toast.makeText(getApplicationContext(),"Couldn't connesct to DB. Try again later.",Toast.LENGTH_LONG).show();
                return;
            }

            Intent intent = new Intent(context, MapActivity.class);
            intent.putExtra(userKey, user);
            intent.putExtra(placesKey, resultPair.first);
            startActivityForResult(intent, 1);
        }

        Context context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_user);

        id = getIntent().getStringExtra("id");
        name = getIntent().getStringExtra("name");

        final TextView textViewUserId = findViewById(R.id.textViewUserId);
        TextView textViewUserName = findViewById(R.id.textViewUserName);
        TextView textViewCountry = findViewById(R.id.textViewCountry);

        Bundle extras = getIntent().getExtras();

        if(extras != null) {

            user = (User) extras.get(userKey);
            textViewUserId.setText(user.getId());
            textViewUserName.setText(user.getName());
        }

        Button mapButton = findViewById(R.id.mapButton);
        Button placesButton = findViewById(R.id.placesButton);
        if(MapController.isServicesOk(this))
        {
            mapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getSeasonPlacesTask seasonPlacesTask = new getSeasonPlacesTask(getApplicationContext());
                    seasonPlacesTask.execute();
                }
            });

            placesButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), SeePlacesActivity.class);
                    i.putExtra(userKey, user);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(i);
                }
            });
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){

        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){

            user = (User) data.getExtras().get(userKey);
        }
    }
}
