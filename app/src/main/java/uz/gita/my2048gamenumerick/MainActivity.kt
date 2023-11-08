package uz.gita.my2048gamenumerick

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.my2048gamenumerick.controller.Controller
import uz.gita.my2048gamenumerick.model.SideEnum
import uz.gita.my2048gamenumerick.pref.MyPref
import uz.gita.my2048gamenumerick.utils.MyTouchListener
import uz.gita.my2048gamenumerick.databinding.ActivityMainBinding
import uz.gita.my2048gamenumerick.utils.MyBgGenerator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val controller = Controller.getInstance()
    private val views = ArrayList<TextView>(16)
    private val generatorBg = MyBgGenerator()
    private var count = 0
    private lateinit var mediaPlayer: MediaPlayer
    private val myPref by lazy { MyPref.getSharedPref() }


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        count = myPref?.getInt("count", 0)!!
//        mediaPlayer = MediaPlayer.create(this, R.raw.animation_lmxkgrol)

        //count = controller.getScore()
        binding.score.text = count.toString()
        myPref?.edit()?.putBoolean("dismiss", false)?.apply()


        val isNewGame = intent.getBooleanExtra("isNewGame", false)
        if (isNewGame) {

            if (!myPref?.getBoolean("isCheck", false)!!) {
                attachTouchListener()
                loadViews()
                describeMatrix()
            } else {
                val s = myPref!!.getString("numbers", "")?.split("/")
                loadViews()
                showScore()
                attachTouchListener()


                for (i in 0 until 16) {
                    views[i].apply {
                        if (!s?.get(i).equals("")) {
                            if (s!![i].equals("0")) {
                                text = ""
                            } else {
                                text = s!![i]
                                this.setBackgroundResource(MyBgGenerator().getBgByAmount(s[i].toInt()))
                            }
                        }
                    }
                }
                controller.getNumberMatrix()
            }

            binding.restart.setOnClickListener {
                onClick()
            }

        }

        binding.home.setOnClickListener {
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
        }
    }


    private fun loadViews() {
        for (i in 0 until binding.mainView.childCount) {
            val liner = binding.mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until liner.childCount) {
                views.add(liner.getChildAt(j) as TextView)
            }
        }
    }


    private fun describeMatrix() {
        val matrix = controller.getMatrix()
        for (i in 0 until matrix.size) {
            for (j in 0 until matrix[i].size) {
                views[i * matrix.size + j].apply {
                    this.text = if (matrix[i][j] == 0) "" else matrix[i][j].toString()
                    this.setBackgroundResource(generatorBg.getBgByAmount(matrix[i][j]))
                }

                if (matrix[i][j] == 2048 && !myPref?.getBoolean("dismiss" , false)!!) {
                    showWinnerDialog()
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun attachTouchListener() {

        val listener = MyTouchListener(this)
        binding.constraint.setOnTouchListener(listener)
        listener.setHandleSideEnumListener { side ->
            when (side) {
                SideEnum.RIGHT -> {
//                    mediaPlayer.start()
                    if (!controller.isClickable()) {
                        showLoserDialog()
                    }

                    controller.moveToRight()
                    showScore()

                    showScore()
                    describeMatrix()
                }

                SideEnum.LEFT -> {
//                    mediaPlayer.start()
                    if (!controller.isClickable()) {
                        showLoserDialog()
                    }

                    controller.moveToleft()

                    showScore()
                    describeMatrix()


                }

                SideEnum.UP -> {
//                    mediaPlayer.start()
                    if (!controller.isClickable()) {
                        showLoserDialog()
                    }

                    controller.moveToUp()

                    showScore()
                    describeMatrix()


                }

                SideEnum.DOWN -> {
//                    mediaPlayer.start()
                    if (!controller.isClickable()) {
                        showLoserDialog()
                    }

                    controller.moveToDown()

                    showScore()
                    describeMatrix()


                }
            }
        }
    }

    private fun onClick() {
        count = 0
        myPref?.edit()?.putBoolean("dismiss", false)?.apply()
        binding.score.text = count.toString()
        controller.restart()
        describeMatrix()
        showScore()
    }


    private fun showScore() {
        binding.score.text = controller.getScore().toString()
    }


    override fun onPause() {
        super.onPause()
        controller.saveNumber()
    }

    private fun showWinnerDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.game_diolog, null)

        val builder = AlertDialog.Builder(this, R.style.CustomDialogStyle)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        count = controller.getScore()
        dialogView.findViewById<TextView>(R.id.textview1).text = count.toString()

        dialogView.findViewById<ImageView>(R.id.diolog_home).setOnClickListener {
            myPref?.edit()?.putBoolean("dismiss", false)?.apply()
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
            onClick()
        }

        dialogView.findViewById<ImageView>(R.id.diolog_dismiss).setOnClickListener {
            myPref?.edit()?.putBoolean("dismiss", true)?.apply()
            alertDialog.dismiss()
        }

        dialogView.findViewById<ImageView>(R.id.diolog_share).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I won the game! Download 2048 now.")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        alertDialog.show()
    }
    private fun showLoserDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.game_diolog_lose, null)

        val builder = AlertDialog.Builder(this, R.style.CustomDialogStyle)
        builder.setView(dialogView)
        builder.setCancelable(false)
        val alertDialog = builder.create()

        count = controller.getScore()
        dialogView.findViewById<TextView>(R.id.textview1).text = count.toString()

        dialogView.findViewById<ImageView>(R.id.diolog_home).setOnClickListener {
            myPref?.edit()?.putBoolean("dismiss", false)?.apply()
            startActivity(Intent(this, MainActivity2::class.java))
            finish()
            onClick()
        }

        dialogView.findViewById<ImageView>(R.id.diolog_restart).setOnClickListener {
            controller.restart()
            myPref?.edit()?.putBoolean("dismiss", false)?.apply()
            alertDialog.dismiss()
            describeMatrix()
        }

        dialogView.findViewById<ImageView>(R.id.diolog_share).setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "I won the game! Download 2048 now.")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }

        alertDialog.show()
    }


    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity2::class.java))
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.saveNumber()
    }

    override fun onStop() {
        super.onStop()
        controller.saveNumber()
    }
}