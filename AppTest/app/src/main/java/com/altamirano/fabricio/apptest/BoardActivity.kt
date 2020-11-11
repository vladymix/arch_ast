package com.altamirano.fabricio.apptest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.altamirano.fabricio.core.commons.OnBoardItem
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val list = ArrayList<OnBoardItem>()
        list.add(OnBoardItem(R.drawable.board_files,R.string.title1,R.string.description1))
        list.add(OnBoardItem(R.drawable.board_location,R.string.title2,R.string.description1))
        list.add(OnBoardItem(R.drawable.board_dark_night, R.string.title2,R.string.description1))
        this.onBoardScreen.setAdapterDefault(list)
    }
}
