package com.bitcodetech.webservices2

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleyRequestQueue {
    companion object {
        private var requestQueue : RequestQueue? = null

        fun getRequestQueue(context : Context) : RequestQueue {
            if(requestQueue == null) {
                requestQueue = Volley.newRequestQueue(context)
            }
            return requestQueue!!
        }
    }
}