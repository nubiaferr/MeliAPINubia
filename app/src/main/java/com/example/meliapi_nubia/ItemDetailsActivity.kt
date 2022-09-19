package com.example.meliapi_nubia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatImageView
import com.example.meliapi_nubia.Adapter.Product
import com.example.meliapi_nubia.databinding.ActivityItemDetailsBinding
import com.squareup.picasso.Picasso

class ItemDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityItemDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        val product: Product? = intent.getParcelableExtra<Product>("itemProduct")

        if (product != null) loadProductDetails(product)
    }





    private fun loadProductDetails(product: Product?) {
        binding.textProductDetail.text = product?.product_name
        binding.textProductPriceDetail.text = "R$ " + product?.product_price.toString()
        val imageProductDetail: AppCompatImageView = binding.imageProductDetail
        Picasso.get().load(product?.product_image).into(imageProductDetail)

    }
}