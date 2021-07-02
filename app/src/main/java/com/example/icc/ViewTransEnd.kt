package com.example.icc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.icc.Adapters.ViewModelAdapter
import com.example.icc.Adapters.ViewTransEndAdapter
import com.example.icc.DataBase.DatabaseHandler

class ViewTransEnd : AppCompatActivity() {

    companion object{
        lateinit var db: DatabaseHandler

        private lateinit var recyclerView: RecyclerView
        private lateinit var viewAdapter: RecyclerView.Adapter<*>
        private lateinit var viewManager: RecyclerView.LayoutManager
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_trans_end)

        db =DatabaseHandler(this)
        viewManager = LinearLayoutManager(this)
        viewAdapter = ViewTransEndAdapter(DatabaseHandler.ViewTransEnd, this)

        recyclerView = findViewById<RecyclerView>(R.id.recycler1)
        recyclerView.apply {

            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter

        }
    }
}