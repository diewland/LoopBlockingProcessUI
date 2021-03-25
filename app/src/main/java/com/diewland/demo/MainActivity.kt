package com.diewland.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class MainActivity : AppCompatActivity() {

    lateinit var tvMesg: TextView
    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // init view
        tvMesg = findViewById(R.id.tv_mesg)

        // init activity handler
        handler = Handler(Looper.getMainLooper())

        // loop heavy process inside this scope
        GlobalScope.launch {
            (1..99).forEach {
                heavyProcess(handler, it)
            }
        }
    }

    suspend fun heavyProcess(handler: Handler, round: Int): Boolean = suspendCoroutine {
        handler.post { // update UI inside this scope not freeze the screen
            Thread.sleep(1_000) // <- heavy process
            tvMesg.text = "heavy process done $round time(s)"
            it.resume(true)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // release handler
        handler.removeCallbacksAndMessages(null)
    }

}