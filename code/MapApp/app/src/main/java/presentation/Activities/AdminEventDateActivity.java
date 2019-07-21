package presentation.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mapapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AdminEventDateActivity extends AppCompatActivity {

    private String limitdate;
    private Date addate;
    private Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_date);

        limitdate = getIntent().getStringExtra("date");
        String[] chain = split(limitdate);

        Calendar todayCalendar = new GregorianCalendar(Integer.parseInt(chain[0]), Integer.parseInt(chain[1])-1, Integer.parseInt(chain[2]));
        final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

        addate = todayCalendar.getTime();
        today = new Date();

        final TextView currentdate = findViewById(R.id.currentdate);
        final TextView admindate = findViewById(R.id.admindate);

        currentdate.setText(dateFormat.format(today));
        admindate.setText(dateFormat.format(addate));

        int x = addate.compareTo(today);

        if(x == -1){
            Toast.makeText(getApplicationContext(), "the event has expired", Toast.LENGTH_SHORT).show();
        }

        final TextView year = findViewById(R.id.year);
        final TextView month = findViewById(R.id.month);
        final TextView day = findViewById(R.id.day);

        final Button setdate = (Button) findViewById(R.id.setdate);
        setdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(true == verifydate(Integer.parseInt(year.getText().toString()),Integer.parseInt(month.getText().toString())-1,Integer.parseInt(day.getText().toString()))){
                    admindate.setText(dateFormat.format(addate));
                }
            }
        });

    }

    public Boolean verifydate(int year, int month, int day){
        Calendar Calendar = new GregorianCalendar(year, month, day);
        Date date = Calendar.getTime();
        int x = date.compareTo(today);
        if(x == -1){
            Toast.makeText(getApplicationContext(), "invalid date", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            Toast.makeText(getApplicationContext(), "event date changed", Toast.LENGTH_SHORT).show();
            addate = date;
            return true;
        }


    }

    public String[] split (String date){
        String[] chain = date.split("/",3);
        return chain;
    }
}
