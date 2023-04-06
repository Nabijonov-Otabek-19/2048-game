package uz.gita.game2048_bek.ui.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import uz.gita.game2048_bek.R
import uz.gita.game2048_bek.databinding.ActivityMainBinding
import uz.gita.game2048_bek.db.MyBase
import uz.gita.game2048_bek.ui.game.GameActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val myBase by lazy { MyBase.getInstance(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStart.setOnClickListener {
                startActivity(Intent(this@MainActivity, GameActivity::class.java))
            }

            btnAbout.setOnClickListener {
                showAboutDialog()
            }

            btnExit.setOnClickListener {
                showExitDialog()
            }

            btnRecords.setOnClickListener {
                showRecordsDialog()
            }
        }
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
            dialog.dismiss()
            this.finishAffinity()
        }
        dialog.create()
        dialog.show()
    }

    private fun showAboutDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_about_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose: AppCompatButton = dialog.findViewById(R.id.btnClose)

        btnClose.setOnClickListener { dialog.dismiss() }

        dialog.create()
        dialog.show()
    }

    private fun showRecordsDialog() {
        val dialog = Dialog(this)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_records_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val btnClose: AppCompatButton = dialog.findViewById(R.id.btnClose)
        val btnReset: AppCompatButton = dialog.findViewById(R.id.btnReset)
        val txtRecord: AppCompatTextView = dialog.findViewById(R.id.txtRecord)

        txtRecord.text = "Record \n${myBase.record}"

        btnClose.setOnClickListener { dialog.dismiss() }

        btnReset.setOnClickListener {
            myBase.record = 0
            dialog.dismiss()
        }
        dialog.create()
        dialog.show()
    }
}