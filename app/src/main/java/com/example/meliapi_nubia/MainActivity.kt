package com.example.meliapi_nubia

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meliapi_nubia.Adapter.Product
import com.example.meliapi_nubia.Adapter.ProductAdapter
import com.example.meliapi_nubia.Api.ApiClient
import com.example.meliapi_nubia.Api.CategoryResponse
import com.example.meliapi_nubia.Api.HighlightsProductResponse
import com.example.meliapi_nubia.Api.ItemProductResponse
import com.example.meliapi_nubia.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.stream.Collectors

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.recyclerAllProducts.layoutManager = LinearLayoutManager(baseContext)

        //pega a lista de produtos por uma categoria chumbada
        getProductCategory("Celular")

    }

    //usa o nome da categoria para achar uma lista
    private fun getProductCategory(categoryName: String) {
        val service = ApiClient.createCategoryService()

        val call: Call<List<CategoryResponse>> = service.list(categoryName)
        call.enqueue(object :
            Callback<List<CategoryResponse>> {

            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {

                val categories = response.body()

                //pega a posição 0 dessa lista e busca os best sellers
                categories?.get(0)?.let { getHighlightsByCategory(it.category_id) }


            }

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                Toast.makeText(applicationContext, "Internet failed", Toast.LENGTH_SHORT)
            }

        })
    }

    //acha os best sellers da categoria
    private fun getHighlightsByCategory(categoryId: String) {
        val service = ApiClient.createCategoryService()

        val callBestSeller: Call<HighlightsProductResponse> =
            service.highlightsItemList(categoryId)

        callBestSeller.enqueue(object :
            Callback<HighlightsProductResponse> {
            @RequiresApi(Build.VERSION_CODES.N)
            override fun onResponse(
                call: Call<HighlightsProductResponse>,
                response: Response<HighlightsProductResponse>
            ) {
                val highlightsProduct = response.body()

                //faz uma lista dos ids dos bestsellers e apresenta
                val productIds = highlightsProduct?.content?.stream()?.map { it.product_id }
                    ?.collect(Collectors.toList())
                if (productIds != null) {
                    getItemDetails(productIds)
                }

            }

            override fun onFailure(call: Call<HighlightsProductResponse>, t: Throwable) {

            }

        })

    }

    //
    private fun getItemDetails(productIds: List<String>) {

        val service = ApiClient.createCategoryService()
        val ids = productIds.joinToString(",")

        val call: Call<List<ItemProductResponse>> = service.itemList(ids)
        call.enqueue(object : Callback<List<ItemProductResponse>> {
            override fun onResponse(
                call: Call<List<ItemProductResponse>>,
                response: Response<List<ItemProductResponse>>
            ) {
                //recebe um monte de produtos que serão separados
                val productsAPI: List<ItemProductResponse>? = response.body()
                loadProducts(productsAPI)
            }

            override fun onFailure(call: Call<List<ItemProductResponse>>, t: Throwable) {
            }

        })
    }


    private fun loadProducts(responseAPI: List<ItemProductResponse>?) {
        val products: MutableList<Product> = ArrayList()

        if (responseAPI != null) {
            //separa o pacote de produtos recebidos em individuais e poe numa lista
            for (productAPI in responseAPI) {
                val product = Product(
                    productAPI.item.item_id,
                    productAPI.item.item_title,
                    productAPI.item.item_price, productAPI.item.item_thumbnail
                )
                products.add(product)
            }

            // mapeia todos pra por evento onClick
            binding.recyclerAllProducts.adapter = ProductAdapter(products) { product ->
                onClickItem(
                    product
                )
            }
            binding.recyclerAllProducts.layoutManager = LinearLayoutManager(this)

        }
    }


    //abre a tela de detalhes passando as infos
    private fun onClickItem(product: Product) {
        val intent = Intent(this, ItemDetailsActivity::class.java)
        intent.putExtra("itemProduct", product)
        startActivity(intent)
    }
}