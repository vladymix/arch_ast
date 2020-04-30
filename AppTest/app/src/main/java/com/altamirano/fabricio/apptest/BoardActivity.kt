package com.altamirano.fabricio.apptest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.core.commons.OnBoardItem
import kotlinx.android.synthetic.main.activity_board.*

class BoardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_board)

        val list = ArrayList<OnBoardItem>()
        list.add(OnBoardItem(R.drawable.ic_bubble_chart,R.string.title1,R.string.description1, R.color.mdtp_accent_color,
            R.color.colorWhite))
        list.add(OnBoardItem(R.drawable.ic_acces_media,R.string.title2,R.string.description1, R.color.light_blue_A700, R.color.mdtp_numbers_text_color))
        this.onBoardScreen.setAdapterDefault(list)
    }
}
