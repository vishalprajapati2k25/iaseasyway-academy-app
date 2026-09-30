package com.iaseasyway.academy.data.api

import com.iaseasyway.academy.data.security.SecurityManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

data class WordPressPost(
    val id: Int,
    val title: String,
    val excerpt: String,
    val link: String,
    val date: String
)

object ApiClient {
    private const val BASE_WP_URL = "https://www.iaseasyway.com/wp-json/wp/v2"
    private const val BASE_ACADEMY_API = "https://api.iaseasyway.com/v1"

    /**
     * Fetches live study notes, news and alerts from iaseasyway.com WordPress API.
     */
    suspend fun fetchLatestArticles(limit: Int = 10): Result<List<WordPressPost>> = withContext(Dispatchers.IO) {
        try {
            val url = URL("$BASE_WP_URL/posts?per_page=$limit&_fields=id,title,excerpt,link,date")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 8000
                readTimeout = 8000
                SecurityManager.generateApiHeaders().forEach { (k, v) -> setRequestProperty(k, v) }
            }

            if (conn.responseCode == 200) {
                val reader = BufferedReader(InputStreamReader(conn.inputStream))
                val response = reader.use { it.readText() }
                val jsonArray = JSONArray(response)
                val list = mutableListOf<WordPressPost>()
                for (i in 0 until jsonArray.length()) {
                    val obj = jsonArray.getJSONObject(i)
                    val titleObj = obj.optJSONObject("title")
                    val title = titleObj?.optString("rendered")?.replace(Regex("<[^>]*>"), "") ?: "Study Note"
                    val excerptObj = obj.optJSONObject("excerpt")
                    val excerpt = excerptObj?.optString("rendered")?.replace(Regex("<[^>]*>"), "") ?: ""
                    list.add(
                        WordPressPost(
                            id = obj.getInt("id"),
                            title = title.trim(),
                            excerpt = excerpt.trim(),
                            link = obj.optString("link", "https://iaseasyway.com"),
                            date = obj.optString("date", "")
                        )
                    )
                }
                Result.success(list)
            } else {
                Result.failure(Exception("HTTP Error: ${conn.responseCode}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Checks health of live API endpoint.
     */
    suspend fun pingBackend(): Boolean = withContext(Dispatchers.IO) {
        try {
            val url = URL("$BASE_WP_URL/categories?per_page=1")
            val conn = (url.openConnection() as HttpURLConnection).apply {
                requestMethod = "GET"
                connectTimeout = 5000
                readTimeout = 5000
            }
            conn.responseCode == 200
        } catch (_: Exception) {
            false
        }
    }
}
