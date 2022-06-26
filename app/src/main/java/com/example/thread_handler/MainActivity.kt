package com.example.thread_handler

import android.os.Bundle
import android.os.Handler
import android.os.Message
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread

//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Handler
//import android.os.Message
//import android.util.Log
//import kotlinx.android.synthetic.main.activity_main.*
//
//class MainActivity : AppCompatActivity() {
//    private var myThread : MyThread? = null
//    lateinit var myHandler: MyHandler
//
//    val SEND_START = 0
//    val SEND_STOP = 1
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//
//        myHandler = MyHandler()
//
//        btnStart.setOnClickListener {
//            if(myThread != null){
//                myThread!!.stopThread()
//            }
//            myThread = MyThread()
//            myThread!!.start()
//        }
//        btnStop.setOnClickListener {
//            myHandler.sendEmptyMessage(SEND_STOP)
//        }
//    }
//    inner class MyThread : Thread() {
//        var i = 0
//        var stopped = false
//
//        fun stopThread() {
//            stopped = true
//        }
//
//        override fun run(){
//            super.run()
//
//            while (!stopped) {
//                var message : Message = Message.obtain() // 메세지를 얻는 메소드
//                message.what = SEND_START
//                message.arg1 = i++
//                message.obj = "초 경과"
//                myHandler.sendMessage(message)
//
//                sleep(100)
//            }
//        }
//    }
//    inner class MyHandler: Handler() {
//        override fun handleMessage(msg: Message) {
//            super.handleMessage(msg)
//            Log.d("msg","$msg")
//            when(msg.what) {
//                SEND_START -> {
//                    tvText.text = msg.arg1.toString() + msg.obj
//                }
//                SEND_STOP -> {
//                    myThread?.stopThread()
//                    tvText.text = "Stop"
//                }
//                else -> {
//                }
//            }
//        }
//    }
//}
class MainActivity : AppCompatActivity() {
    lateinit var myHandler : MyHandler
    private var myThread: MyThread? = null
    var i = 0
    var arg1 = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        myHandler = MyHandler()

        btnStart.setOnClickListener{
            if(myThread != null){
                myThread?.stopThread()
            }
            myThread = MyThread()
            myThread?.start()
        }
        btnStop.setOnClickListener {
            myHandler?.sendEmptyMessage(0)
            myThread?.stopThread()
        }
        btnRecode.setOnClickListener {
            myHandler.sendEmptyMessage(2)
        }
    }
    inner class MyHandler : Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            when(msg.what){
                1 -> {
                    tvText.text = "${msg.arg1}  ${msg.obj}"
                    arg1 = msg.arg1
                }
                0 -> {
                    tvText.text = "Stop"
                    i = 0
                    tvRecode.text = ""
                }
                2 -> {
                    i++
                    tvRecode.text = "${i}번  $arg1 초 "
                }
            }
        }
    }
    inner class MyThread : Thread(){
        var i = 0
        var state = false

        fun stopThread(){
            state = true
        }

        override fun run() {
            super.run()

            while(!state){
                var message : Message = Message.obtain()
                message.what = 1
                message.arg1 = i++
                message.obj = "초"
                myHandler.sendMessage(message)
                sleep(100)
            }
        }
    }
}


