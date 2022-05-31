package com.example.app

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.core.graphics.plus

class MySurfaceView(context: Context?, attrs: AttributeSet?)
    : SurfaceView(context, attrs), SurfaceHolder.Callback{
    var surfaceHolder: SurfaceHolder
    var BG: Bitmap
    var BGmoveX:Int = 0

    init {
        surfaceHolder = getHolder()
        BG = BitmapFactory.decodeResource(getResources(), R.drawable.background)
        surfaceHolder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder) {
        var canvas: Canvas = surfaceHolder.lockCanvas()
        drawSomething(canvas)
        surfaceHolder.unlockCanvasAndPost(canvas)
    }

    fun drawSomething(canvas: Canvas) {
        //canvas.drawBitmap(BG, 0f, 0f, null)
        var SrcRect:Rect = Rect(0, 0, BG.width, BG.height) //使用者看到的範圍
        var w:Int = width
        var h:Int = height
        var DestRect: Rect = Rect(0, 0, w, h)
        //canvas.drawBitmap(BG, SrcRect, DestRect, null)

        //背景往左捲動
        BGmoveX -= 3
        var BGnewX:Int = w + BGmoveX

        if (BGnewX <= 0) {
            BGmoveX = 0
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        } else {
            DestRect = Rect(BGmoveX, 0, BGnewX, h)
            canvas.drawBitmap(BG, SrcRect, DestRect, null)

            DestRect = Rect(BGnewX, 0, w + BGnewX, h)
            canvas.drawBitmap(BG, SrcRect, DestRect, null)
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        //TODO("Not yet implemented")
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        //TODO("Not yet implemented")
    }
}