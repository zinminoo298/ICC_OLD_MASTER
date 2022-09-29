package com.example.icc

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.icc.Adapters.SpinnerAdapter
import com.example.icc.DataBase.DatabaseHandler


class Setting : AppCompatActivity() {
    companion object{
        lateinit var checkBoxFtp: RadioButton
        lateinit var checkBoxInternal: RadioButton
        lateinit var editTextDevice: EditText
        lateinit var editTextIp: AutoCompleteTextView
        lateinit var buttonSave: Button
        lateinit var db:DatabaseHandler
        var checkedBox = ""
        var device_name = ""
        var ip = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        db = DatabaseHandler(this)

        loadCheckedBox()
        loadDevice()
        loadIp()
        db.getIp()

        checkBoxFtp = findViewById(R.id.checkbox_ftp)
        checkBoxInternal = findViewById(R.id.checkbox_internal)
        editTextIp = findViewById(R.id.editText_ip)
        editTextDevice = findViewById(R.id.edt_device)
        buttonSave = findViewById(R.id.btn_save)

        editTextDevice.setText(device_name)
        editTextIp.setText(ip)
        if(checkedBox == "ftp"){
            checkBoxFtp.isChecked = true
        }
        if(checkedBox == "internal"){
            checkBoxInternal.isChecked = true
        }

        checkBoxFtp.setOnClickListener {
            checkBoxInternal.isChecked = false
            checkedBox = "ftp"
        }
        checkBoxInternal.setOnClickListener{
            checkBoxFtp.isChecked = false
            checkedBox = "internal"
        }
        buttonSave.setOnClickListener {
            setDevice(editTextDevice.text.toString())
            setIp(editTextIp.text.toString())
            setCheckedBox(checkedBox)
            db.saveIp(editTextIp.text.toString())
            Toast.makeText(this, "Setting Saved", Toast.LENGTH_SHORT).show()
        }


        editTextIp.setAdapter(SpinnerAdapter(this, R.layout.spinner_row, DatabaseHandler.IpList, DatabaseHandler.IpList))
        editTextIp.threshold = 0
        editTextIp.setOnTouchListener(OnTouchListener { paramView, paramMotionEvent ->
            if(DatabaseHandler.IpList.isNotEmpty()){
                editTextIp.showDropDown()
                editTextIp.requestFocus()
            }
            false
        })
    }

    private fun setDevice(v: String) {
        val editor = getSharedPreferences("device", MODE_PRIVATE).edit()
        editor.putString("valdev", v)
        editor.apply()
    }
    private fun setIp(v: String) {
        val editor = getSharedPreferences("ip", MODE_PRIVATE).edit()
        editor.putString("valip", v)
        editor.apply()
    }
    private fun setCheckedBox(v: String) {
        val editor = getSharedPreferences("box", MODE_PRIVATE).edit()
        editor.putString("valbox", v)
        editor.apply()
    }

    private fun loadDevice() {
        var prefs = getSharedPreferences("device", Activity.MODE_PRIVATE)
        device_name = prefs.getString("valdev", "").toString()
    }
    private fun loadIp() {
        var prefs = getSharedPreferences("ip", Activity.MODE_PRIVATE)
        ip = prefs.getString("valip", "").toString()
    }
    private fun loadCheckedBox() {
        var prefs = getSharedPreferences("box", Activity.MODE_PRIVATE)
        checkedBox = prefs.getString("valbox", "internal").toString()
    }

    override fun onBackPressed() {
        val prefs1 = getSharedPreferences("device", Activity.MODE_PRIVATE)
        Export.device_name = prefs1.getString("valdev", "").toString()

        val prefs2 = getSharedPreferences("ip", Activity.MODE_PRIVATE)
        Export.ip =  prefs2.getString("valip", "").toString()

        val prefs3 = getSharedPreferences("box", Activity.MODE_PRIVATE)
        Export.checkedBox = prefs3.getString("valbox", "internal").toString()

        super.onBackPressed()
    }
}