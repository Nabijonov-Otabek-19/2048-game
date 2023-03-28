package uz.gita.game2048_bek.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.gita.game2048_bek.R
import uz.gita.game2048_bek.databinding.ActivityMainBinding
import uz.gita.game2048_bek.ui.game.GameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener {
                startActivity(Intent(this@MainActivity, GameActivity::class.java))
            }
        }
    }
}