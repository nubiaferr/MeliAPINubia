package com.example.meliapi_nubia.Api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

import retrofit2.http.Query

const val ACCESS_TOKEN: String =
    "APP_USR-7157959322474284-091900-b8f10e946d2ec2923be34c6ee273d39a-665806152"

interface ApiService {
    //acha categoria por nome
    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("sites/MLB/domain_discovery/search?limit=1")
    fun list(@Query("q") categoryPartName: String): Call<List<CategoryResponse>>

    //acha bestsellers por categoria
    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("highlights/MLB/category/{category_id}")
    fun highlightsItemList(@Path("category_id") categoryId: String): Call<HighlightsProductResponse>

    //acha itens (por bestsellers)
    @Headers("Authorization: Bearer $ACCESS_TOKEN")
    @GET("items")
    fun itemList(@Query("ids") ids: String): Call<List<ItemProductResponse>>
}