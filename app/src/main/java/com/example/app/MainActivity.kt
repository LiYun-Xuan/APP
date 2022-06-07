package com.example.app

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.media.Image
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
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
    GestureDetector.OnGestureListener, View.OnTouchListener {

    var btn: Boolean = false     //現在工作執行狀態，按下start為執行，按下stop為暫停
    var cnt: Int = 1    //紀錄現在按鈕圖片為? 1 表示為start, 0 表示為stop
    lateinit var job: Job
    lateinit var btnSt: ImageView   //按鈕圖片
    lateinit var sv: MySurfaceView  //背景繪製與轉動
    lateinit var img: ImageView     //放在按鈕旁的個人照
    lateinit var fly: ImageView     //飛機照片
    lateinit var gd: GestureDetector    //觸控監聽
    lateinit var mp: MediaPlayer    //撥放音訊
    var shoot: Boolean = false      //是否按下fly(飛機圖片)

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btnSt = findViewById(R.id.btnSt)
        sv = findViewById(R.id.sv)
        fly = findViewById(R.id.fly)
        gd = GestureDetector(this, this)
        mp = MediaPlayer()

        btnSt.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (cnt == 1) {
                    cnt = 0;
                    btnSt.setImageResource(R.drawable.stop)
                    btn = true

                    job = GlobalScope.launch(Dispatchers.Main) {
                        while (btn == true) {
                            val canvas: Canvas = sv.holder.lockCanvas()
                            sv.drawSomething(canvas)
                            sv.holder.unlockCanvasAndPost(canvas)
                            delay(20)

                            if (shoot == true){        //按下fly時才換圖片，沒按時回到圖片fly1/2輪播
                                shoot = false

                                StartPlay(fly)

                                fly.setImageResource(R.drawable.shoot1)
                                delay(20)
                                fly.setImageResource(R.drawable.shoot2)
                                delay(20)
                                fly.setImageResource(R.drawable.shoot3)
                                delay(20)
                                fly.setImageResource(R.drawable.shoot4)
                                delay(20)
                                fly.setImageResource(R.drawable.shoot5)
                                delay(20)
                            }else{
                                fly.setImageResource(R.drawable.fly2)
                                delay(20)
                                fly.setImageResource(R.drawable.fly1)
                                delay(20)
                            }
                        }
                    }
                }else {
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
            .override(1600, 1200)
            .into(img)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (btn == true) {
            fly.setOnTouchListener(this)
            gd.onTouchEvent(event)
        }
        return true
    }

    fun StartPlay(v: View) {    //撥放音訊
        mp.reset()

        when (v.id) {
            R.id.fly -> {
                mp = MediaPlayer.create(this, R.raw.shoot)
                mp.start()
            }
        }
    }

    override fun onTouch(p0: View?, event: MotionEvent?): Boolean {
        shoot = true
        return false
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
        fly.y = e2!!.y      //讓fly(飛機圖片) = 手指拖曳的位置 (只有y座標)
        return true
    }

    override fun onLongPress(e: MotionEvent?) { //長按

    }

    override fun onFling(e1: MotionEvent?, e2: MotionEvent?, velocityX: Float, velocityY: Float): Boolean { //快滑
        return true
    }
}


