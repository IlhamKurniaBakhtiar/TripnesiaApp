package com.tripnesia.mobile.Database

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject

suspend fun getSnapToken(
    orderId: String,
    amount: Int,
    name: String,
    email: String
): String? = withContext(Dispatchers.IO) {
    try {
        val client = OkHttpClient()
        val mediaType = "application/json; charset=utf-8".toMediaType()
        val jsonBody = """
            {
                "orderId": "$orderId",
                "amount": $amount,
                "name": "$name",
                "email": "$email"
            }
        """.trimIndent()

        val request = Request.Builder()
            .url("https://tripnesia-production.up.railway.app/create-transaction")
            .post(jsonBody.toRequestBody(mediaType))
            .build()

        val response = client.newCall(request).execute()

        if (response.isSuccessful) {
            val body = response.body?.string()
            val json = JSONObject(body ?: "")
            json.getString("token")
        } else {
            null
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
