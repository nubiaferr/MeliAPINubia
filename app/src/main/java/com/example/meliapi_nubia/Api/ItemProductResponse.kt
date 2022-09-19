package com.example.meliapi_nubia.Api

import com.example.meliapi_nubia.Api.ItemProductBodyResponse
import com.google.gson.annotations.SerializedName

class ItemProductResponse {

    //Get details by item
    @SerializedName("body")
    lateinit var item: ItemProductBodyResponse


}