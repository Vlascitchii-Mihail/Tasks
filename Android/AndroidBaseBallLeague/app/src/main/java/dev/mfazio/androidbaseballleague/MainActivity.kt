package dev.mfazio.androidbaseballleague

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dev.mfazio.androidbaseballleague.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }
}