package com.altamirano.fabricio.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.altamirano.fabricio.libraryast.DateTools;
import com.altamirano.fabricio.libraryast.DialogTools;
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


    public void onNavigateTo(View view) {

        Intent navigate =null;
        switch (view.getId()){
            case R.id.btn_image_circle:
                DialogTools dg = new DialogTools();
                dg.setCancelable(true);
                dg.setImageCenter(getResources().getDrawable(R.drawable.ic_circle_java));
                dg.setImageTitle(getResources().getDrawable(R.drawable.ic_android_studio));


                dg.show(this.getFragmentManager(),"Hola");

                //navigate = new Intent(this, CircleBitmapActivity.class);
                break;
        }

        if(navigate!=null){
            startActivity(navigate);
        }

    }
}
