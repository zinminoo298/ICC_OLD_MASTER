package com.example.icc.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.icc.Model.ViewModel
import com.example.icc.Model.ViewTransEndModel
import com.example.icc.R

class ViewTransEndAdapter(private var Dataset: ArrayList<ViewTransEndModel>, private val context: Context) :
    RecyclerView.Adapter<ViewTransEndAdapter.MyViewHolder>() {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val txtCust = itemView.findViewById<TextView>(R.id.view_cust)
        val txtBp = itemView.findViewById<TextView>(R.id.view_bp)
        val txtPd = itemView.findViewById<TextView>(R.id.view_pdcode)
        val txtQty = itemView.findViewById<TextView>(R.id.view_qty)
        val txtPrice = itemView.findViewById<TextView>(R.id.view_price)
        val txtSprice = itemView.findViewById<TextView>(R.id.view_sprice)
        val txtBarcode = itemView.findViewById<TextView>(R.id.view_barcode)
        val txtDate = itemView.findViewById<TextView>(R.id.view_date)
        val txtType = itemView.findViewById<TextView>(R.id.view_type)
        val txtHandcode = itemView.findViewById<TextView>(R.id.view_handcode)
        val txtShelf = itemView.findViewById<TextView>(R.id.view_shelf)
        val txtCorner = itemView.findViewById<TextView>(R.id.view_corner)
        val txtCat = itemView.findViewById<TextView>(R.id.view_cat)
        val txtDesc = itemView.findViewById<TextView>(R.id.view_desc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.view_trans_end_row, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val txtcust = holder.txtCust
        val txtbp = holder.txtBp
        val txtprd = holder.txtPd
        val txtqty = holder.txtQty
        val txtprc = holder.txtPrice
        val txtsprc = holder.txtSprice
        val txtbc = holder.txtBarcode
        val txtdate = holder.txtDate
        val txttype = holder.txtType
        val txthandcode = holder.txtHandcode
        val txtshelf = holder.txtShelf
        val txtcorner = holder.txtCorner
        val txtcat = holder.txtCat
        val txtdesc = holder.txtDesc

        txtcust.text = Dataset[position].customer
        txtbp.text = Dataset[position].businessProduct
        txtprd.text = Dataset[position].productCode
        txtqty.text = Dataset[position].qty
        txtprc.text = Dataset[position].price
        txtsprc.text = Dataset[position].s_price
        txtbc.text = Dataset[position].barcode
        txtdate.text = Dataset[position].date
        txttype.text = Dataset[position].type
        txthandcode.text = Dataset[position].hand_code
        txtshelf.text = Dataset[position].shelf
        txtcorner.text = Dataset[position].corner
        txtcat.text = Dataset[position].category
        txtdesc.text = Dataset[position].description
    }

    override fun getItemCount() = Dataset.size
}