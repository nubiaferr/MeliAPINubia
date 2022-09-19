package com.example.meliapi_nubia.Api

import com.google.gson.annotations.SerializedName

class HighlightsProductResponse {

    @SerializedName("content")
    lateinit var content: List<HighlightsProductContentResponse>



}