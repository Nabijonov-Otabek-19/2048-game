package uz.gita.game2048_bek.ui.game

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.game2048_bek.R
import uz.gita.game2048_bek.databinding.ActivityGameBinding
import uz.gita.game2048_bek.db.MyBase
import uz.gita.game2048_bek.model.SideEnum
import uz.gita.game2048_bek.repository.AppRepository
import uz.gita.game2048_bek.ui.result.ResultActivity
import uz.gita.game2048_bek.utils.BackgroundUtil
import uz.gita.game2048_bek.utils.MyTouchListener

class GameActivity : AppCompatActivity() {
    private val items: MutableList<TextView> = ArrayList(16)
    private val repository = AppRepository.getInstance()
    private val util = BackgroundUtil()

    private val myBase by lazy { MyBase.getInstance(this) }
    private lateinit var binding: ActivityGameBinding

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            loadViews()
            describeMatrixToViews()

            repository.setListener {
                openResultActivity()
            }

            val myTouchListener = MyTouchListener(this@GameActivity)

            myTouchListener.setMyMovementSideListener {
                when (it) {
                    SideEnum.DOWN -> {
                        repository.moveToDown()
                        describeMatrixToViews()
                    }
                    SideEnum.UP -> {
                        repository.moveToUp()
                        describeMatrixToViews()
                    }
                    SideEnum.RIGHT -> {
                        repository.moveToRight()
                        describeMatrixToViews()
                    }
                    SideEnum.LEFT -> {
                        repository.moveToLeft()
                        describeMatrixToViews()
                    }
                }
            }

            mainView.setOnTouchListener(myTouchListener)

            btnReload.setOnClickListener {
                showResetDialog()
            }
        }
    }

    private fun openResultActivity() {
        startActivity(Intent(this, ResultActivity::class.java))
        finish()
    }

    private fun showExitDialog() {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_exit_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnNo: AppCompatButton = dialog.findViewById(R.id.btnNo)
        val btnYes: AppCompatButton = dialog.findViewById(R.id.btnYes)

        btnNo.setOnClickListener { dialog.dismiss() }

        btnYes.setOnClickListener {
            val str = binding.txtRecord.text.toString()
            val currRecord = str.substring(6, str.length).trim().toInt()
            if (currRecord >= myBase.record) {
                myBase.record = currRecord
            }
            dialog.dismiss()
            this.finish()
        }
        dialog.create()
        dialog.show()
    }

    private fun showResetDialog() {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_reset_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnNo: AppCompatButton = dialog.findViewById(R.id.btnNo)
        val btnYes: AppCompatButton = dialog.findViewById(R.id.btnYes)

        btnNo.setOnClickListener { dialog.dismiss() }

        btnYes.setOnClickListener {
            repository.resetGame()
            describeMatrixToViews()
            dialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }

    override fun onBackPressed() {
        showExitDialog()
    }

    private fun loadViews() {
        binding.apply {
            for (i in 0 until mainView.childCount) {
                val linear = mainView.getChildAt(i) as LinearLayoutCompat
                for (j in 0 until linear.childCount) {
                    items.add(linear.getChildAt(j) as TextView)
                }
            }
        }
    }

    private fun describeMatrixToViews() {
        binding.apply {
            txtScore.text = "Score\n${repository.getScore()}"
            txtRecord.text = "Record\n${myBase.record}"
        }

        val _matrix = repository.matrix
        for (i in _matrix.indices) {
            for (j in _matrix[i].indices) {
                items[i * 4 + j].apply {
                    text = if (_matrix[i][j] == 0) ""
                    else _matrix[i][j].toString()

                    setBackgroundResource(util.colorByAmount(_matrix[i][j]))
                }
            }
        }
    }
}