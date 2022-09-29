package com.example.icc.Model

class ItemModel {
    var prod:String = ""
    var item:String = ""
    var barcode:String = ""
    var cat:String = ""
    var price:String = ""
    var s_price:String = ""
    var description:String = ""

    constructor(prod:String, item:String, barcode:String, cat:String, price:String, s_price:String, description:String){
        this.prod = prod
        this.item = item
        this.barcode = barcode
        this.cat = cat
        this.price = price
        this.s_price = s_price
        this.description = description
    }

}