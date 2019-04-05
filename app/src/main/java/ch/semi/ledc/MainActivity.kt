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

                        updateColor(pixel)

                    }
                }
            }
        }

        return true
    }

    private fun updateColor(color: Int){

        val rgbText = getTextFromColor(color)
        when(channel_radio_group.checkedRadioButtonId) {
            inside_radio.id -> {
                color_display_inside.setBackgroundColor(color)
                rgbval_inside.text = rgbText
            }
            outside_radio.id -> {
                color_display_outside.setBackgroundColor(color)
                rgbval_outside.text = rgbText
            }
            all_radio.id -> {
                color_display_all.setBackgroundColor(color)
                rgbval_both.text = rgbText
            }
        }
        val idName = resources.getResourceEntryName(channel_radio_group.checkedRadioButtonId)
        Log.i(TAG, "$rgbText @$idName")

    }

    private fun getTextFromColor(color: Int) : String {

        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)

        return "R:$r G:$g B:$b"

    }



}
