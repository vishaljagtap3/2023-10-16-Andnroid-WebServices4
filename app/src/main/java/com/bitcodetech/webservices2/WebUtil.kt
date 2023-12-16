package com.bitcodetech.webservices2

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class WebUtil {
    companion object {

        fun getUsers() : ArrayList<User>? {

            val httpURLConnection = URL("https://reqres.in/api/users?page=2")
                .openConnection() as HttpURLConnection
            httpURLConnection.requestMethod = "GET"

            httpURLConnection.connect()

            if (httpURLConnection.responseCode != 200) {
                Log.e("tag", "Failed to get users")
                return null
            }

            val inStream = httpURLConnection.inputStream
            val responseBuffer = StringBuffer()

            val data = ByteArray(1024)

            var count = inStream.read(data)
            while (count != -1) {
                responseBuffer.append(String(data, 0, count))
                count = inStream.read(data)
            }
            inStream.close()
            httpURLConnection.disconnect()

            Log.e("tag", responseBuffer.toString())

            val jResponse = JSONObject(responseBuffer.toString())
            val totalRecords = jResponse.getInt("total")

            val jUsers = jResponse.getJSONArray("data")

            val users = ArrayList<User>()

            for (i in 0..jUsers.length() - 1) {
                val jUser = jUsers.getJSONObject(i)

                users.add(
                    User(
                        jUser.getInt("id"),
                        jUser.getString("email"),
                        jUser.getString("first_name"),
                        jUser.getString("last_name"),
                        jUser.getString("avatar"),
                        getWebImage(jUser.getString("avatar"))
                    )
                )

            }

            Log.e("tag", "")

            return users
        }

        fun getWebImage(imageUrl : String) : Bitmap? {

            val httpURLConnection = URL(imageUrl).openConnection() as HttpURLConnection
            httpURLConnection.connect()

            if(httpURLConnection.responseCode != 200) {
                return null
            }

            val bitmap = BitmapFactory.decodeStream(httpURLConnection.inputStream)

            httpURLConnection.disconnect()
            return bitmap
        }
    }
}










