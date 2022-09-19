package com.example.meliapi_nubia.Api

import com.google.gson.annotations.SerializedName

class HighlightsProductContentResponse {

    @SerializedName("id")
    var product_id: String = ""

    @SerializedName("position")
    var position: Int = 0

    @SerializedName("type")
    var type: String = "ITEM"


}