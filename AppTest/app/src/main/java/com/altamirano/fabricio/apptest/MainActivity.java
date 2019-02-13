package com.altamirano.fabricio.apptest;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.altamirano.fabricio.apptest.activities.CircleBitmapActivity;
import com.altamirano.fabricio.apptest.activities.InfoUrlActivity;
import com.altamirano.fabricio.apptest.activities.OnBoardActivity;
import com.altamirano.fabricio.apptest.activities.SquareBitmapActivity;
import com.altamirano.fabricio.apptest.adapters.MenuAdapter;
import com.altamirano.fabricio.apptest.commons.MenuItem;
import com.altamirano.fabricio.libraryast.DateTools;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    ListView listMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.listMenu = findViewById(R.id.listMenu);
        ArrayList<MenuItem> itms = new ArrayList<>();
        itms.add(new MenuItem(OnBoardActivity.class, "On Board", R.drawable.ic_on_board));
        itms.add(new MenuItem(SquareBitmapActivity.class, "Square Images", R.drawable.ic_images));
        itms.add(new MenuItem(CircleBitmapActivity.class, "Circle Images", R.drawable.ic_circle_images));
        itms.add(new MenuItem(InfoUrlActivity.class, "Url load info", R.drawable.ic_loadurl));
        itms.add(new MenuItem(null));
        itms.add(new MenuItem(null));

        this.listMenu.setAdapter(new MenuAdapter(this, itms));
        this.listMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        MenuItem menu = (MenuItem) adapterView.getItemAtPosition(i);
        if (menu.getKlass() != null) {
            Intent intent = new Intent(MainActivity.this, menu.getKlass());
            startActivity(intent);
        }
    }

    private String dateTools() {


        Calendar cl = Calendar.getInstance();
        Date date = cl.getTime();

        String values = "";
        values += "Date to sqlite " + DateTools.getDateToSqlite(date);
        values += "\nDate from sqlite " + DateTools.getDateFromSqlite(DateTools.getDateToSqlite(date));

        values += "\n";
        values += "\nCurrent date " + DateTools.getCurrentDate();
        try {
            values += "\nCurrent date UTC " + DateTools.getDateCurrentUTC();
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        values += "\n";
        values += "\nDate start year " + DateTools.getStartYear();
        values += "\nDate end year " + DateTools.getEndYear();

        values += "\n";

        values += "\nDate start hour day " + DateTools.getStartHourOfDay().getTime();
        values += "\nDate last hour day " + DateTools.getLastHourOfDay().getTime();


        return values;
    }

    private void codeComment(){
         /*  SwipeActionAdapter mAdapter = new SwipeActionAdapter();
        mAdapter.setListView(this.listMenu);
         //mAdapter.setFadeOut(true);

        // Set backgrounds for the swipe directions
        mAdapter.addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.layout_left)
                //.addBackground(SwipeDirection.DIRECTION_FAR_LEFT,R.layout.row_bg_left)
                .addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.layout_right);
        //.addBackground(SwipeDirection.DIRECTION_FAR_RIGHT,R.layout.row_bg_right);


        mAdapter.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if (direction.isLeft()) return true; // Change this to false to disable left swipes
                if (direction.isRight()) return true;
                return false;
            }

            @Override
            public void onSwipe(int[] position, SwipeDirection[] direction) {

            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                // Only dismiss an item when swiping normal left
                return direction == SwipeDirection.DIRECTION_NORMAL_LEFT;
            }
        });

        */
    }


    /*public void onNavigateTo(View view) {

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
    }*/
}
