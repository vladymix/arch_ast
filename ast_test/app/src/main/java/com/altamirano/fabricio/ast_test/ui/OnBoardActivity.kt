package com.altamirano.fabricio.ast_test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.altamirano.fabricio.ast_test.R
import com.altamirano.fabricio.core.commons.OnBoardItem
import com.ast.widgets.OnBoardScreen

class OnBoardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_board)

        findViewById<OnBoardScreen>(R.id.onBoard).let {
        val list = ArrayList<OnBoardItem>()
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))
        list.add(OnBoardItem(R.drawable.ast_gamma_colors,R.string.ast_msg_title_update, R.string.ast_msg_new_version))

            it.setAdapterDefault(list)
        }
    }
}