package com.example.icc

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.*
import com.example.icc.DataBase.DatabaseHandler
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class TransEnd : AppCompatActivity() {

    companion object{
        lateinit var buttonImport: Button
        lateinit var buttonView: Button
        lateinit var imageViewFileImport: ImageView
        lateinit var editTextFile: EditText
        lateinit var textViewCheckImport: TextView
        lateinit var textViewRec: TextView

        lateinit var db : DatabaseHandler
        val requestcode = 1
        var filepath = ""
        var importProgress = ""
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trans_end)

        buttonImport = findViewById(R.id.btn_import)
        buttonView = findViewById(R.id.btn_view)
        imageViewFileImport = findViewById(R.id.btn_file)
        editTextFile = findViewById(R.id.edt_file)
        textViewCheckImport = findViewById(R.id.importcheck)
        textViewRec = findViewById(R.id.totalRec)
        db = DatabaseHandler(this)

        db.countTransEnd()
        textViewRec.text ="Total Records : ${ DatabaseHandler.transendRec.toString() }"

        buttonView.setOnClickListener {
            object : AsyncTask<Int?, Int?, Int?>() {
                internal lateinit var pgd: ProgressDialog
                override fun onPreExecute() {
                    pgd = ProgressDialog(this@TransEnd)
                    pgd.setMessage("Please Wait")
                    pgd.setTitle("Loading Data")
                    pgd.show()
                    pgd.setCancelable(false)

                    super.onPreExecute()

                }

                override fun onPostExecute(result: Int?) {
                    pgd.dismiss()
                    if(DatabaseHandler.count == 0){
                       Toast.makeText(this@TransEnd,"No Data",Toast.LENGTH_LONG).show()
                    }
                    else{
                        val intent = Intent(this@TransEnd,ViewTransEnd::class.java)
                        startActivity(intent)
                    }

                    super.onPostExecute(result)
                }

                override fun doInBackground(vararg params: Int?): Int? {
                    db.loadTransEnd()
                    return null
                }


            }.execute()
        }

        imageViewFileImport.setOnClickListener {
            val fileintent = Intent(Intent.ACTION_GET_CONTENT)
            fileintent.type = "text/plain"
            fileintent.addCategory(Intent.CATEGORY_OPENABLE)
            fileintent.putExtra("android.content.extra.SHOW_ADVANCED", true)
            try {
                startActivityForResult(fileintent, Import.requestcode)
            } catch (e: ActivityNotFoundException) {
                editTextFile.setText("No activity can handle picking a file. Showing alternatives.")
            }
        }

        buttonImport.setOnClickListener {
            if(editTextFile.text.toString() =="")
            {
                Toast.makeText(this,"Please Select File", Toast.LENGTH_SHORT).show()
            }
            else{
                importProgress = "importing"
                Progress()
            }

        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data == null)
            return
        if (requestCode == requestcode) {
            filepath = data.data.toString()
            editTextFile.setText(data.data!!.path)
        }
    }

    private fun Progress(){
        val view = ProgressBar(this)
        view.setBackgroundColor(0xffffff)
        val windowParams: WindowManager.LayoutParams = WindowManager.LayoutParams()
        windowParams.gravity = Gravity.CENTER
        windowParams.height = WindowManager.LayoutParams.WRAP_CONTENT
        windowParams.width = WindowManager.LayoutParams.WRAP_CONTENT
        windowParams.flags = (WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                or WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                or WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN)
        windowParams.format = PixelFormat.TRANSLUCENT
        windowParams.windowAnimations = 0

        object : AsyncTask<Int?, Int?, Int?>() {
            override fun onPreExecute() {
                // init your dialog here;
                buttonImport.isEnabled = false
                imageViewFileImport.isEnabled = false
                buttonView.isEnabled = false
                windowManager.addView(view, windowParams)
                textViewCheckImport.text="Importing"

            }

            override fun onPostExecute(result: Int?) {
                windowManager.removeView(view)
                buttonImport.isEnabled = true
                imageViewFileImport.isEnabled = true
                buttonView.isEnabled = true
                textViewRec.text = "Total Records : ${ DatabaseHandler.transendRec.toString() }"
                if(importProgress == "complete"){
                    textViewCheckImport.text="Import Successful"
                }
                else{
                    textViewCheckImport.text="Import Fail!"
                }
                // process result;
            }

            override fun doInBackground(vararg params: Int?): Int? {
                // do your things here
                try {
                    importText()
                    db.countTransEnd()
                } catch (e: InterruptedException) {
                    // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                return null
            }


        }.execute()
        println(importProgress)
//        if(importProgress == "complete"){
//            textViewCheckImport.text="Import Successful"
//        }
//        else{
//            if(importProgress == "importing"){
//                textViewCheckImport.text="Importing"
//            }
//            else{
//                textViewCheckImport.text="Import Fail!"
//            }
//        }
    }

    private fun importText() {
        val cursor=contentResolver.openInputStream(android.net.Uri.parse(filepath))
        val db = this.openOrCreateDatabase("database.db", Context.MODE_PRIVATE, null)
        val tableName = "transaction_table"
        var linecount = 0

        try {
            try {
                val file= InputStreamReader(cursor)
                val buffer = BufferedReader(file)
                val contentValues = ContentValues()
                db.beginTransaction()
                val words: ArrayList<String> = ArrayList()
                var wordsArray: Array<String?>
                buffer.readLine()
                while (true) {
                    words.clear()
                    val line = buffer.readLine()
                    linecount++
                    println(linecount)

                    if (line == null) break
                    var customer = ""
                    var business_product = ""
                    var product_code = ""
                    var qty = ""
                    var price = ""
                    var total = ""
                    var barcode = ""
                    var date = ""
                    var type = ""
                    var hand_code = ""
                    var shelf = ""
                    var corner = ""
                    var category = ""
                    var desc = ""
                    var total_array = ArrayList<String>()

                    wordsArray =line.split("\t").toTypedArray()
                    for (each in wordsArray) {
                        words.add(each!!.replace(" ", ""))
                    }
//                    for(each in words){
//                        println(each)
//                    }

                    customer = words[0]
                    business_product = words[1]
                    product_code = words[2]
                    qty = words[3]
                    price = words[4]
                    total = words[5]
                    barcode = words[6]
                    date = words[7]
                    type = words[8]
                    hand_code = words[9]
                    shelf = words[10]
                    corner = words[11]
                    category = words[12]
                    desc = words[13]


                    total_array.add(customer)
                    total_array.add(business_product)
                    total_array.add(product_code)
                    total_array.add(qty)
                    total_array.add(price)
                    total_array.add(total)
                    total_array.add(barcode)
                    total_array.add(date)
                    total_array.add(type)
                    total_array.add(hand_code)
                    total_array.add(shelf)
                    total_array.add(corner)
                    total_array.add(category)
                    total_array.add(desc)

                    contentValues.put("Customer", total_array[0])
                    contentValues.put("Business_Product", total_array[1])
                    contentValues.put("Product_Code", total_array[2])
                    contentValues.put("Qty", total_array[3])
                    contentValues.put("Price", total_array[4])
                    contentValues.put("S_Price", total_array[5])
                    contentValues.put("Barcode", total_array[6])
                    contentValues.put("Date", total_array[7])
                    contentValues.put("Check_type", total_array[8])
                    contentValues.put("Hand_Code", total_array[9])
                    contentValues.put("Shelf", total_array[10])
                    contentValues.put("Corner", total_array[11])
                    contentValues.put("Category", total_array[12])
                    contentValues.put("Description", total_array[13])

                    try{
                        db.insert(tableName, null, contentValues)
                    }
                    catch(e: IOException){

                    }
                }

                db.setTransactionSuccessful()
                db.endTransaction()
                importProgress = "complete"
            } catch (e: IOException) {
                if (db.inTransaction())
                    db.endTransaction()
                println("ERROR")
                importProgress = "fail"
            }

        } catch (e: IOException) {
            println(e)
        }
    }
}