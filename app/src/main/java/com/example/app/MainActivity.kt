package com.example.app

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity() {

    var btn:Boolean = true
    var cnt:Int = 1
    lateinit var job: Job
    lateinit var btnSt:ImageView
    lateinit var sv:MySurfaceView

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSt = findViewById(R.id.btnSt)
        sv = findViewById(R.id.sv)

        btnSt.setOnClickListener(object:View.OnClickListener{
            override fun onClick(p0: View?) {
                if (cnt == 1){
                    cnt = 0;
                    btnSt.setImageResource(R.drawable.stop)
                    btn = true

                    job = GlobalScope.launch(Dispatchers.Main) {
                        while(btn == true) {
                            val canvas: Canvas = sv.holder.lockCanvas()
                            sv.drawSomething(canvas)
                            sv.holder.unlockCanvasAndPost(canvas)
                            delay(20)
                        }

                    }
                }
                else{
                    btn = false
                    job.cancel()
                    cnt = 1
                    btnSt.setImageResource(R.drawable.start)
                }
            }
        })
    }
}


