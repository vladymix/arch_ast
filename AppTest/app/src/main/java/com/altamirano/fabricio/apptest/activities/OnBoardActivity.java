package com.altamirano.fabricio.apptest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.altamirano.fabricio.apptest.MainActivity;
import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.libraryast.onboard.OnBoardItem;
import com.altamirano.fabricio.libraryast.onboard.OnBoardScreen;

import java.util.ArrayList;

public class OnBoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        loadOnBoardScreen();
    }

    private void loadOnBoardScreen(){
        ArrayList<OnBoardItem> onBoardItems=new ArrayList<>();

        int[] header = {R.string.ob_header1, R.string.ob_header2, R.string.ob_header3};
        int[] desc = {R.string.ob_desc1, R.string.ob_desc2, R.string.ob_desc3};
        int[] imageId = {R.drawable.onboard_page1_ex, R.drawable.onboard_page2_in, R.drawable.onboard_page3};

        for(int i=0;i<imageId.length;i++)
        {
            OnBoardItem item=new OnBoardItem();
            item.setImageID(imageId[i]);
            item.setTitle(getResources().getString(header[i]));
            item.setDescription(getResources().getString(desc[i]));

            onBoardItems.add(item);
        }

        OnBoardScreen screen = findViewById(R.id.idBoardScreen);

        screen.configure(onBoardItems, "Begin", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OnBoardActivity.this,"OK",Toast.LENGTH_LONG).show();
            }
        });
    }
}
