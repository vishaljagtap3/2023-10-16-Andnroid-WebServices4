package com.bitcodetech.webservices2

import android.graphics.Bitmap

data class User(
    val id : Int,
    val email : String,
    val firstName : String,
    val lastName : String,
    val avatar : String,
    val avatarBitmap : Bitmap?
)