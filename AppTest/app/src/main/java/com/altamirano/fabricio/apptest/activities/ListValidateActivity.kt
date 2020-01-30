package com.altamirano.fabricio.apptest.activities

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import com.altamirano.fabricio.apptest.R
import com.altamirano.fabricio.apptest.adapters.RecycleContactsAdapter
import com.altamirano.fabricio.apptest.commons.DataSample
import com.altamirano.fabricio.simpleSwipe.SwipeToValidateAdapter

class ListValidateActivity : AppCompatActivity() {

    private lateinit var recycler: RecyclerView
    private val adapter = RecycleContactsAdapter(DataSample.getContacts()) {
        Toast.makeText(this@ListValidateActivity, "${it.name} Clicked", Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_validate)

        this.recycler = findViewById(R.id.listview)

        this.recycler.layoutManager = LinearLayoutManager(this)

        recycler.adapter = adapter

        val swipe = object : SwipeToValidateAdapter(this, this.recycler) {

            override fun instantiateUnderlayButton(viewHolder: RecyclerView.ViewHolder?, underlayButtons: ArrayList<UnderlayButton>) {
                underlayButtons.add(UnderlayButton(
                        "Delete",
                        Color.parseColor("#FF3C30"),
                        object : UnderlayButtonClickListener {
                            override fun onClick(pos: Int) { // TODO: onDelete
                                Toast.makeText(this@ListValidateActivity, "${adapter.items[pos].name} Delete", Toast.LENGTH_LONG).show()
                            }
                        }
                ))

                underlayButtons.add(UnderlayButton(
                        "Transfer",
                        Color.parseColor("#FF9502"),
                        object : UnderlayButtonClickListener {
                            override fun onClick(pos: Int) { // TODO: OnTransfer
                                Toast.makeText(this@ListValidateActivity, "${adapter.items[pos].name} Transfer", Toast.LENGTH_LONG).show()
                            }
                        }
                ))
                underlayButtons.add(UnderlayButton(
                        "Unshare",
                        Color.parseColor("#C7C7CB"),
                        object : UnderlayButtonClickListener {
                            override fun onClick(pos: Int) { // TODO: OnUnshare
                                Toast.makeText(this@ListValidateActivity, "${adapter.items[pos].name} Unshare", Toast.LENGTH_LONG).show()
                            }
                        }
                ))
            }

        }
    }
}
