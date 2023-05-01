package com.cyn.composeproject.api

import android.util.Log
import com.cyn.composeproject.utils.SpUtils
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit



object Api {
    const val ZHIHU_URL = "http://news-at.zhihu.com/api/"
    const val NEWS_URL = "http://news-at.zhihu.com/api/2/news/latest"
    const val MOVIE_URL = "http://baobab.kaiyanapp.com/api/v4/rankList/videos"
    const val MUSIC_BANNER = "https://mrlin-netease-cloud-music-api-iota-silk.vercel.app/BANNER/TYPE=1"
    const val Album = "https://mrlin-netease-cloud-music-api-iota-silk.vercel.app/top/album?limit=5&offset=0"
    const val TOP_MV = "https://mrlin-netease-cloud-music-api-iota-silk.vercel.app/top/mv?limit=5&offset=0"
//        const val RECOMMEND_MUSIC = "https://mrlin-netease-cloud-music-api-iota-silk.vercel.app/recommend/resource"


    private const val SET_COOKIE_KEY = "set-cookie"
    private const val COOKIE_NAME = "Cookie"
    private const val CONNECT_TIMEOUT = 30L
    private const val READ_TIMEOUT = 10L

    private fun create(): Retrofit {
        val okHttpClientBuilder = OkHttpClient().newBuilder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            // get response cookie
            addInterceptor {
                val request = it.request()
                val response = it.proceed(request)
                val requestUrl = request.url().toString()
                val domain = request.url().host()
                // set-cookie maybe has multi, login to save cookie
                if ((requestUrl.contains("user/login") || requestUrl.contains(
                        "user/register"
                    ))
                    && response.headers(SET_COOKIE_KEY).isNotEmpty()
                ) {
                    val cookies = response.headers(SET_COOKIE_KEY)
                    val cookie = encodeCookie(cookies)
                    saveCookie(requestUrl, domain, cookie)
                }
                Log.e("request","Sending request: ${request.url()} \n ${request.headers()}")
                Log.e("response", "Received response for  ${response.request().url()}\n${response.headers()}")
                response
            }
            // set request cookie
            addInterceptor {
                val request = it.request()
                val builder = request.newBuilder()
                val domain = request.url().host()
                // get domain cookie
                if (domain.isNotEmpty()) {
                    val spDomain: String? = SpUtils.getString(domain)
                    val cookie: String = if (spDomain!!.isNotEmpty()) spDomain else ""
                    if (cookie.isNotEmpty()) {
                        builder.addHeader(COOKIE_NAME, cookie)
                    }
                }
                it.proceed(builder.build())

            }
        }

        return RetrofitBuild(
            url = ZHIHU_URL,
            client = okHttpClientBuilder.build(),
            gsonFactory = GsonConverterFactory.create()
        ).retrofit
    }

    class RetrofitBuild(
        url: String, client: OkHttpClient,
        gsonFactory: GsonConverterFactory
    ) {
        val retrofit: Retrofit = Retrofit.Builder().apply {
            baseUrl(url)
            client(client)
            addConverterFactory(gsonFactory)

        }.build()
    }
    /**
     * get ServiceApi
     */
    fun <T> create(service: Class<T>): T = create().create(service)
}



//    private val retrofit by lazy {
//        Retrofit.Builder().apply {
//            baseUrl(ZHIHU_URL)
//            client(okHttpClientBuilder.build())
//            addConverterFactory(GsonConverterFactory.create())
//        }.build()
//    }



/***
 * 保存数据到cookie string
 * @param cookies List<String>
 * @return String
 */

fun encodeCookie(cookies: List<String>): String {
    val sb = StringBuilder()
    val set = HashSet<String>()
    cookies
        .map { cookie ->
            cookie.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }
        .forEach { it ->
            it.filterNot { set.contains(it) }.forEach { set.add(it) }
        }

    val ite = set.iterator()
    while (ite.hasNext()) {
        val cookie = ite.next()
        sb.append(cookie).append(";")
    }

    val last = sb.lastIndexOf(";")
    if (sb.length - 1 == last) {
        sb.deleteCharAt(last)
    }
    return sb.toString()
}

private fun saveCookie(url: String?, domain: String?, cookies: String) {
    url ?: return
    SpUtils.setString(url,cookies)
    domain ?: return
    SpUtils.setString(domain, cookies)
}