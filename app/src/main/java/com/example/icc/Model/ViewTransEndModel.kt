package com.example.icc.Model

class ViewTransEndModel {
    var customer:String? = null
    var businessProduct:String? = null
    var productCode:String? = null
    var qty:String? = null
    var price:String? = null
    var s_price:String? = null
    var barcode:String? = null
    var date:String? = null
    var type:String? = null
    var hand_code:String? = null
    var shelf:String? = null
    var corner:String? = null
    var category:String? = null
    var description:String? = null

    constructor(
        customer: String?,
        businessProduct: String?,
        productCode: String?,
        qty: String?,
        price: String?,
        s_price: String?,
        barcode: String?,
        date: String?,
        type: String?,
        hand_code: String?,
        shelf: String?,
        corner: String?,
        category: String?,
        description: String?
    ) {
        this.customer = customer
        this.businessProduct = businessProduct
        this.productCode = productCode
        this.qty = qty
        this.price = price
        this.s_price = s_price
        this.barcode = barcode
        this.date = date
        this.type = type
        this.hand_code = hand_code
        this.shelf = shelf
        this.corner = corner
        this.category = category
        this.description = description
    }
}