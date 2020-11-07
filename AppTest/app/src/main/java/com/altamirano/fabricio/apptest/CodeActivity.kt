package com.altamirano.fabricio.apptest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.core.tools.asyncTask

class CodeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_code)
    }

    private  fun tast(){

        asyncTask(preExecute = {

        },doInBackground = {

            return@asyncTask "value return"
        }, postExecute = {

        })

    }
}