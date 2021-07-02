
package com.example.icc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class SelectImport : AppCompatActivity() {

    companion object{
        lateinit var btnMaster:Button
        lateinit var btnTrasnsEnd:Button
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_import)

        btnMaster = findViewById(R.id.btn_master)
        btnTrasnsEnd = findViewById(R.id.btn_trans_end)

        btnMaster.setOnClickListener{
            val intent = Intent(this, Import::class.java)
            startActivity(intent)
        }
        btnTrasnsEnd.setOnClickListener{
            val intent = Intent(this, TransEnd::class.java)
            startActivity(intent)
        }
    }
}