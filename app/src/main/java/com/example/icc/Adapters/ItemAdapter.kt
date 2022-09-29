package com.example.icc.Adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.ScaleAnimation
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.icc.DataBase.DatabaseHandler
import com.example.icc.Model.ItemModel
import com.example.icc.R
import java.util.*


class ItemAdapter(
        private var Dataset: ArrayList<ItemModel>,
        private val context: Context
) :
        RecyclerView.Adapter<ItemAdapter.MyViewHolder>() {

    companion object{
        var row_index = -1
    }
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtProd = itemView.findViewById<TextView>(R.id.txt_prod_code)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_list_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val txtProd = holder.txtProd
        txtProd.text = Dataset[position].item

        holder.view.setOnClickListener {
            row_index = position
            notifyDataSetChanged()

            DatabaseHandler.prod = Dataset[position].prod
            DatabaseHandler.item = Dataset[position].item
            DatabaseHandler.cat = Dataset[position].cat
            DatabaseHandler.price = Dataset[position].price
            DatabaseHandler.s_price = Dataset[position].s_price
            DatabaseHandler.description = Dataset[position].description
        }
        if(row_index == position){
            holder.view.setBackgroundColor(Color.parseColor("#00cc00"))
                holder.txtProd.setTextColor(Color.parseColor("#FFFFFF"))
        }
        else{
            holder.view.setBackgroundColor(Color.parseColor("#70add8e6"))
                holder.txtProd.setTextColor(Color.parseColor("#000000"))
        }
    }


    override fun getItemCount() = Dataset.size
}