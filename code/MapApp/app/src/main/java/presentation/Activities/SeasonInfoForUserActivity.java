package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.mapapp.R;

import java.util.ArrayList;

import presentation.Adapters.SeasonCountriesAdapter;

public class SeasonInfoForUserActivity extends AppCompatActivity {

    private TextView DateTextView;
    private ListView seasonCountriesListView;
    private ArrayList<String> seasonCountries;
    private String limitDate;
    private final String limitDateKey = "limitDate";
    private final String seasonCountriesKey = "seasonCountries";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_season_info_for_user);

        DateTextView = findViewById(R.id.DateTextView);
        seasonCountriesListView = findViewById(R.id.seasonCountriesListView);

        Bundle extras = getIntent().getExtras();

        if(extras != null){

            seasonCountries = extras.getStringArrayList(seasonCountriesKey);
            limitDate = extras.getString(limitDateKey);
        }
        else {

            seasonCountries = new ArrayList<>();
            limitDate = new String();
        }

        String data[][] = countriesToString(seasonCountries);
        seasonCountriesListView.setAdapter(new SeasonCountriesAdapter(this, data));

        DateTextView.setText(limitDate);
    }


    private String[][] countriesToString(ArrayList<String> countries){

        String[][] data;
        int numberOfCountries=countries.size();

        if(numberOfCountries > 0) {

            data = new String[numberOfCountries][1];
            for(int i = 0; i < numberOfCountries; i++){

                data[i][0] = countries.get(i);
            }
        }
        else{

            data = new String[1][0];
            data[0][0] = "nothing to show here";
        }
        return data;
    }
}
