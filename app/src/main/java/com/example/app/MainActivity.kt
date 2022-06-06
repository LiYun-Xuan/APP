package com.example.app

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.view.ViewCompat.getX
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule


import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.coroutines.*
import kotlin.properties.Delegates

@GlideModule
public final class MyAppGlideModule : AppGlideModule()

class MainActivity : AppCompatActivity(),
    GestureDetector.OnGestureListener{

    var btn:Boolean = true
    var cnt:Int = 1
    lateinit var job: Job
    lateinit var btnSt:ImageView
    lateinit var sv:MySurfaceView
    lateinit var img:ImageView
    lateinit var fly:ImageView
    lateinit var gd:GestureDetector

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSt = findViewById(R.id.btnSt)
        sv = findViewById(R.id.sv)
        fly = findViewById(R.id.fly)
        gd = GestureDetector(this,this)

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

                            fly.setImageResource(R.drawable.fly2)
                            delay(20)
                            fly.setImageResource(R.drawable.fly1)
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

        img = findViewById(R.id.mypicture)
        GlideApp.with(this)
            .load(R.drawable.mypicture)
            .circleCrop()
            .override(1600,1200)
            .into(img)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if(btn == true){
            gd.onTouchEvent(event)
        }
        return true
    }

    override fun onDown(e: MotionEvent?): Boolean { //短按，每次點擊時發生
        return true
    }

    override fun onShowPress(e: MotionEvent?) { //使用者點擊後，停留較長一點時間，沒有滑動也還沒放開時發生

    }

    override fun onSingleTapUp(e: MotionEvent?): Boolean {  //短按
        return true
    }

    override fun onScroll(e1: MotionEvent?, e2: MotionEvent?, distanceX: Float, distanceY: Float): Boolean {    //拖曳
        fly.y = e2!!.y
        return true
    }

    override fun onLongPress(e: MotionEvent?) { //長按

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean { //快滑
        return true
    }
}


