package kr.ac.kumoh.s20180100.w1102volleywithviewmodel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kr.ac.kumoh.s20180100.w1102volleywithviewmodel.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var model: SongViewModel
    private var songs: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        model = ViewModelProvider(this)[SongViewModel::class.java]
        model.songs.observe(this, Observer<ArrayList<String>>{
            songs = model.songs.value?.toTypedArray()
            binding.listSongs.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                songs as Array<out String>
            )
        })
        model.requestSong()
    }
}