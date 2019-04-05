package ch.semi.ledc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.Menu
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    private val TAG : String = "COLOR_KOBOLD_LOG"

    private var mCurrentColor: Color = Color.valueOf(Color.BLACK)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val _channels: RadioGroup = channel_radio_group
        val _chInside: RadioButton = inside_radio
        val _chOutside: RadioButton = outside_radio
        val _chAll: RadioButton = all_radio

        rgb_color_circle.setOnTouchListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {



        return super.onCreateOptionsMenu(menu)

    }

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {

        when(v) {
            rgb_color_circle -> {
                when(event?.action){
                    MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {

                        val inverse = Matrix()
                        (v as ImageView).imageMatrix.invert(inverse)
                        val touchLocation = floatArrayOf(event.x, event.y)
                        inverse.mapPoints(touchLocation)

                        val x = touchLocation[0].toInt()
                        val y = touchLocation[1].toInt()

                        Log.d(TAG, "touching at x: $x , y: $y")

                        val pixel = (rgb_color_circle.drawable as BitmapDrawable).bitmap.getPixel(x, y)

                        color_display_all.setBackgroundColor(pixel)



 //                       updateColor(Color.red(pixel), Color.green(pixel), Color.blue(pixel))

                    }
                }
            }
        }

        return true
    }

    fun updateColor(r: Int, g: Int, b: Int){


        Log.d(TAG, "red: $r; green: $g; blue: $b;")

    }



}
