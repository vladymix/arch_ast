package com.altamirano.fabricio.apptest.activities;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.swipe.SwipeActionAdapter;
import com.altamirano.fabricio.swipe.SwipeDirection;
import com.altamirano.fabricio.swipe.SwipeList;

import java.util.ArrayList;
import java.util.Arrays;

import static com.altamirano.fabricio.swipe.SwipeDirection.DIRECTION_FAR_LEFT;
import static com.altamirano.fabricio.swipe.SwipeDirection.DIRECTION_FAR_RIGHT;
import static com.altamirano.fabricio.swipe.SwipeDirection.DIRECTION_NORMAL_LEFT;
import static com.altamirano.fabricio.swipe.SwipeDirection.DIRECTION_NORMAL_RIGHT;

public class ListSwipeActivity extends AppCompatActivity {

    private ArrayAdapter<String> stringAdapter;
    private SwipeList swipeList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_swipe);
        this.swipeList = findViewById(R.id.listview);
        this.loadAdapter();

    }

    private void loadAdapter() {
        // Create an Adapter for your content
        String[] content = new String[20];
        for (int i = 0; i < 20; i++) content[i] = "Row " + (i + 1);
        this.stringAdapter = new ArrayAdapter<>(
                this,
                R.layout.item_swipe,
                R.id.text,
                new ArrayList<>(Arrays.asList(content))
        );


        this.swipeList.setAdapter(stringAdapter);

        this.swipeList.addBackground(DIRECTION_FAR_LEFT, R.layout.row_bg_left_far)
                .addBackground(DIRECTION_NORMAL_LEFT, R.layout.row_bg_left)
                .addBackground(DIRECTION_FAR_RIGHT, R.layout.row_bg_right_far)
                .addBackground(DIRECTION_NORMAL_RIGHT, R.layout.row_bg_right);

        this.swipeList.setSwipeActionListener(new SwipeActionAdapter.SwipeActionListener() {
            @Override
            public boolean hasActions(int position, SwipeDirection direction) {
                if (direction.isLeft()) return true; // Change this to false to disable left swipes
                if (direction.isRight()) return true;
                return false;
            }

            @Override
            public boolean shouldDismiss(int position, SwipeDirection direction) {
                // Only dismiss an item when swiping normal left
                return direction == DIRECTION_NORMAL_LEFT;
            }

            @Override
            public void onSwipe(int[] positionList, SwipeDirection[] directionList) {
                for (int i = 0; i < positionList.length; i++) {
                    SwipeDirection direction = directionList[i];
                    int position = positionList[i];
                    String dir = "";

                    switch (direction) {
                        case DIRECTION_FAR_LEFT:
                            dir = "Far left";
                            break;
                        case DIRECTION_NORMAL_LEFT:
                            dir = "Left";
                            break;
                        case DIRECTION_FAR_RIGHT:
                            dir = "Far right";
                            break;
                        case DIRECTION_NORMAL_RIGHT:
                            AlertDialog.Builder builder = new AlertDialog.Builder(ListSwipeActivity.this);
                            builder.setTitle("Test Dialog").setMessage("You swiped right").create().show();
                            dir = "Right";
                            break;
                    }


                    Toast.makeText(ListSwipeActivity.this, dir + " swipe Action triggered on " + stringAdapter.getItem(position), Toast.LENGTH_SHORT).show();

                   stringAdapter.remove(stringAdapter.getItem(position));
                   stringAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onSwipeEnded(ListView listView, int position, SwipeDirection direction) {

            }

            @Override
            public void onSwipeStarted(ListView listView, int position, SwipeDirection direction) {

            }
        });
    }
}
