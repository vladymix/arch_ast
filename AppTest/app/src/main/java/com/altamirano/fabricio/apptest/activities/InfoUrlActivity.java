package com.altamirano.fabricio.apptest.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.altamirano.fabricio.apptest.R;
import com.altamirano.fabricio.libraryast.asynctasks.LoadUrlInfoTask;
import com.altamirano.fabricio.libraryast.commons.InfoUrl;
import com.altamirano.fabricio.libraryast.listener.UrlResponse;

public class InfoUrlActivity extends AppCompatActivity implements UrlResponse {

    EditText text_input_url;

    TextView url_input;
    TextView url_title;
    TextView url_description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_url);

    text_input_url = findViewById(R.id.text_input_url);

        url_title = findViewById(R.id.url_title);
        url_description = findViewById(R.id.url_description);
        url_input = findViewById(R.id.url_input);



    text_input_url.addTextChangedListener(new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            loadInfor(charSequence.toString());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    });

    }

    @Override
    public void result(InfoUrl infoUrl) {

        if(infoUrl!=null){
            url_title.setText(infoUrl.get_title());
            url_description.setText(infoUrl.getDescription());
            url_input.setText(infoUrl.getUrl());

            Toast.makeText(this,"Url loaded",Toast.LENGTH_LONG).show();
        }
    }

    private void loadInfor(String url){
        LoadUrlInfoTask infotask = new LoadUrlInfoTask(url,this);
        infotask.execute();
    }
}
