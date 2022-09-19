package com.example.meliapi_nubia.Adapter

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler


@Parcelize
data class Product(
    var product_id: String,
    val product_name: String,
    val product_price: Float,
    val product_image: String,

    ) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat(),
        parcel.readString().toString()
    )

    companion object : Parceler<Product> {

        override fun Product.write(parcel: Parcel, flags: Int) {
            parcel.writeString(product_id)
            parcel.writeString(product_name)
            parcel.writeFloat(product_price)
            parcel.writeString(product_image)
        }

        override fun create(parcel: Parcel): Product {
            return Product(parcel)
        }
    }
}