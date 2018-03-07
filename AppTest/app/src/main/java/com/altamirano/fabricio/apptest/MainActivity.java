package com.altamirano.fabricio.apptest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.altamirano.fabricio.libraryast.DateTools;
import com.altamirano.fabricio.libraryast.ImageTools;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = findViewById(R.id.tx_formats);
        tx.setText(dateTools());

    }

    private String dateTools()  {


        Calendar cl = Calendar.getInstance();
        Date date = cl.getTime();

        String values = "";
        values += "Date to sqlite "+ DateTools.getDateToSqlite(date);
        values += "\nDate from sqlite "+ DateTools.getDateFromSqlite(DateTools.getDateToSqlite(date));

        values += "\n";
        values += "\nCurrent date "+ DateTools.getCurrentDate();
        try{
            values += "\nCurrent date UTC "+ DateTools.getDateCurrentUTC();
        }catch (ParseException ex){
            ex.printStackTrace();
        }
        values += "\n";
        values += "\nDate start year "+ DateTools.getStartYear();
        values += "\nDate end year "+ DateTools.getEndYear();

        values += "\n";

        values += "\nDate start hour day "+ DateTools.getStartHourOfDay().getTime();
        values += "\nDate last hour day "+ DateTools.getLastHourOfDay().getTime();


        return values;
    }


}
