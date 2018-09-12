package com.altamirano.fabricio.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.altamirano.fabricio.apptest.activities.OnBoardActivity;
import com.altamirano.fabricio.libraryast.DateTools;
import com.altamirano.fabricio.libraryast.DialogTools;

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
        final  DialogTools dg = new DialogTools();

        switch (view.getId()){
            case R.id.btn_image_circle:

                dg.setTitle("Oh yeah! is time!")
                        .setMessage("Add this dialog to facilitate your application, collaborate in github.com/vladymix")
                        .setImageCenter(getResources().getDrawable(R.drawable.ic_circle_java))
                        .setImageTitle(getResources().getDrawable(R.drawable.ic_android_studio))
                        .setPrimaryButton("Ok", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                            }
                        })
                        .setSecundaryButton("Cancel", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dg.dismiss();
                            }
                        });
                dg.setCancelable(false);

                dg.show(this.getFragmentManager(),"Hola");

                break;
            case R.id.btn_onbloard:
                navigate = new Intent(this, OnBoardActivity.class);
                break;
        }

        if(navigate!=null){
            startActivity(navigate);
        }
    }
}
