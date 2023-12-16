package com.bitcodetech.webservices2

import com.google.gson.annotations.SerializedName

class ApiResponse {
    var page: Int = 0
    var per_page: Int = 0
    var total : Int = 0
    var total_pages = 0
    @SerializedName("data")
    var users : Array<User>? = null
}