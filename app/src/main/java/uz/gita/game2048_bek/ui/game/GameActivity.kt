package uz.gita.game2048_bek.ui.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import uz.gita.game2048_bek.R
import uz.gita.game2048_bek.model.SideEnum
import uz.gita.game2048_bek.repository.AppRepository
import uz.gita.game2048_bek.utils.BackgroundUtil
import uz.gita.game2048_bek.utils.MyTouchListener

class GameActivity : AppCompatActivity() {
    private val items: MutableList<TextView> = ArrayList(16)
    private lateinit var mainView: LinearLayoutCompat
    private lateinit var score: AppCompatTextView
    private val repository = AppRepository.getInstance()
    private val util = BackgroundUtil()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        mainView = findViewById(R.id.mainView)
        loadViews()
        describeMatrixToViews()

        val myTouchListener = MyTouchListener(this)

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
    }

    private fun loadViews() {
        score = findViewById(R.id.txtScore)

        for (i in 0 until mainView.childCount) {
            val linear = mainView.getChildAt(i) as LinearLayoutCompat
            for (j in 0 until linear.childCount) {
                items.add(linear.getChildAt(j) as TextView)
            }
        }
    }

    private fun describeMatrixToViews() {
        score.text = "Score: ${repository.getScore()}"

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
