package com.bitcodetech.webservices2

import android.os.AsyncTask
import android.os.Handler
import android.os.Message

class Worker(
    private val handler: Handler
) : AsyncTask<Any?, Any?, Any?>() {

    override fun doInBackground(vararg p0: Any?): Any? {
        val apiResponse = WebUtil.getUsers()

        val message = Message()
        message.obj = apiResponse

        handler.sendMessage(message)

        return null
    }
}