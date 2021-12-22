package com.itis.androidlabproject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.itis.androidlabproject.databinding.ActivitySecondBinding
import com.google.android.material.snackbar.Snackbar


class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater).also {
            setContentView(it.root)
        }

        with(binding) {
            btnMessage.setOnClickListener {
                val message = intent?.extras?.getString("MESSAGE") ?: "сообщение не дошло или пусто"

                Snackbar.make(
                    root,
                    "Получено сообщение: $message",
                    Snackbar.LENGTH_LONG
                ).show()

                /*Toast.makeText(
                    this@SecondActivity,
                    "Получено сообщение: $message",
                    Toast.LENGTH_LONG
                ).show()*/
            }

            btnReturn.setOnClickListener {
                setResult(Activity.RESULT_OK)
                finish()
            }
        }
    }
}
