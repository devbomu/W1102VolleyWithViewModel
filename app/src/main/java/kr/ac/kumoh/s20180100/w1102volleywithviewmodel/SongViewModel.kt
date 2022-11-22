package kr.ac.kumoh.s20180100.w1102volleywithviewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject

class SongViewModel(application: Application) : AndroidViewModel(application) {
    companion object {
        const val QUEUE_TAG = "SongVolleyRequest"
    }

    private val list = ArrayList<String>()
    val songs = MutableLiveData<ArrayList<String>>()

    private var queue: RequestQueue = Volley.newRequestQueue(getApplication())

    init {
        songs.value = list
    }

    fun requestSong() {
        // NOTE: 서버 주소는 본인의 서버 IP 사용할 것
        val myServerUrl = "http://192.168.231.134:9090/isd_song"

        // Array를 반환할 경우에는 JsonObjectRequest 대신 JsonArrayRequest 사용
        val request = JsonArrayRequest(
            Request.Method.GET,
            myServerUrl,
            null,
            {
                list.clear()
                parseJson(it)
                songs.value = list
                //Toast.makeText(getApplication(), list.toString(), Toast.LENGTH_LONG).show()
            },
            {
                Toast.makeText(getApplication(), it.toString(), Toast.LENGTH_LONG).show()
            }
        )

        request.tag = QUEUE_TAG
        queue.add(request)
    }

    private fun parseJson(items: JSONArray) {
        for (i in 0 until items.length()) {
            val item: JSONObject = items[i] as JSONObject
            val title = item.getString("s_title")
            val type = item.getString("s_type")

            list.add("$title ($type)")
        }
    }

    override fun onCleared() {
        super.onCleared()
        queue.cancelAll(QUEUE_TAG)
    }
}