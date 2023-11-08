package uz.gita.my2048gamenumerick

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import uz.gita.my2048gamenumerick.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding: ActivityMain2Binding
    private var boolean: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.continueBtn.setOnClickListener {
            startGame(isContinued = true)
        }

        binding.newgameBtn.setOnClickListener {
            startGame(isNewGame = true)
        }
        binding.howToPlay.setOnClickListener {
            boolean = true

            binding.exitGame.visibility= GONE
            binding.exitGameText.visibility= GONE
            binding.aboutbtn.visibility= GONE
            binding.gameBtnn.visibility= GONE
            binding.txt2048.visibility = GONE
            binding.newgameBtn.visibility = GONE
            binding.howToPlay.visibility = GONE
            binding.docTxt2048.visibility = VISIBLE
            binding.docTxts.visibility = VISIBLE
            binding.home.visibility = VISIBLE
            binding.support.visibility = VISIBLE

            binding.support.setOnClickListener {
                openTelegramProfile()
            }
        }
        binding.exitGame.setOnClickListener {
            finish()
        }

        binding.home.setOnClickListener {
            boolean = false
            binding.exitGame.visibility= VISIBLE
            binding.exitGameText.visibility= VISIBLE
            binding.gameBtnn.visibility= VISIBLE
            binding.aboutbtn.visibility= VISIBLE
            binding.txt2048.visibility = VISIBLE
            binding.newgameBtn.visibility = VISIBLE
            binding.howToPlay.visibility = VISIBLE
            binding.docTxt2048.visibility = GONE
            binding.docTxts.visibility = GONE
            binding.home.visibility = GONE
            binding.support.visibility = GONE
        }
    }

    private fun openTelegramProfile() {
        val telegramUsername = "hmavlonov"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/$telegramUsername"))
        startActivity(intent)
    }

    private fun startGame(isContinued: Boolean = false, isNewGame: Boolean = false) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("isContinued", isContinued)
        intent.putExtra("isNewGame", isNewGame)
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        if (boolean == false) {
            super.onBackPressed()
        }
        binding.exitGameText.visibility = VISIBLE
        binding.exitGame.visibility = VISIBLE
        binding.aboutbtn.visibility= VISIBLE
        binding.gameBtnn.visibility= VISIBLE
        binding.txt2048.visibility = VISIBLE
        binding.newgameBtn.visibility = VISIBLE
        binding.howToPlay.visibility = VISIBLE
        binding.docTxt2048.visibility = GONE
        binding.docTxts.visibility = GONE
        binding.home.visibility = GONE
        binding.support.visibility = GONE

    }
}
