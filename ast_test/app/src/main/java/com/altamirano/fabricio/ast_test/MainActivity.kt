package com.altamirano.fabricio.ast_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import com.altamirano.fabricio.ast_test.adapter.SimpleListAdapter
import com.altamirano.fabricio.core.swipe.SwipeActionAdapter
import com.altamirano.fabricio.core.swipe.SwipeDirection
import com.altamirano.fabricio.core.swipe.SwipeList

class MainActivity : AppCompatActivity() {
    lateinit var swipeList:SwipeList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        swipeList = findViewById(R.id.swipeList)
        swipeList.setAdapter(SimpleListAdapter(this, 0, ArrayList<String>().apply {
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Como estas")
            add("Hola")
            add("Fabricio")

        }))

        this.swipeList.addBackground(SwipeDirection.DIRECTION_NORMAL_RIGHT, R.layout.row_nr_right)!!
            .addBackground(SwipeDirection.DIRECTION_FAR_RIGHT, R.layout.row_bg_right)
            .addBackground(SwipeDirection.DIRECTION_NORMAL_LEFT, R.layout.row_nr_left)
            .addBackground(SwipeDirection.DIRECTION_FAR_LEFT, R.layout.row_bg_left)

        this.swipeList.setSwipeActionListener(object : SwipeActionAdapter.SwipeActionListener {

            override fun hasActions(position: Int, direction: SwipeDirection?): Boolean {
                if (direction!!.isLeft) return true
                if (direction.isRight) return true
                return false
            }

            override fun onSwipe(position: IntArray?, direction: Array<SwipeDirection?>?) {
                position?:return
                direction?:return

                for (i in position.indices) {
                    val mdirection = direction[i]
                    val mPosition = position[i]
                    when (mdirection) {
                        SwipeDirection.DIRECTION_NORMAL_LEFT -> { }
                        SwipeDirection.DIRECTION_FAR_LEFT -> {
                           // onDelete(mPosition)
                        }
                        SwipeDirection.DIRECTION_NORMAL_RIGHT -> { }
                        SwipeDirection.DIRECTION_FAR_RIGHT -> {
                           // onEdit(mPosition)
                        }
                        else -> {
                            Log.i("Favorites","direction no controlled")
                        }
                    }
                }
            }

            override fun onSwipeEnded(listView: ListView?, position: Int, direction: SwipeDirection?) {
            }

            override fun onSwipeStarted(listView: ListView?, position: Int, direction: SwipeDirection?) {
            }

            override fun shouldDismiss(position: Int, direction: SwipeDirection?): Boolean {
                // Only dismiss an item when swiping normal left
                // return p1 == SwipeDirection.DIRECTION_NORMAL_LEFT
                return false
            }

        })


    }
}