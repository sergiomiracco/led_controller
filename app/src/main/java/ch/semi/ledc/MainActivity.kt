package ch.semi.ledc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.IllegalArgumentException
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.sqrt

class MainActivity : AppCompatActivity(), View.OnTouchListener, View.OnLongClickListener, View.OnClickListener {

    private val TAG : String = "COLOR_KOBOLD_LOG"

    private var mCurrentColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        rgb_color_circle.setOnTouchListener(this)

        color_btn_1.setOnLongClickListener(this)
        color_btn_1.setOnClickListener(this)
        color_btn_2.setOnLongClickListener(this)
        color_btn_2.setOnClickListener(this)
        color_btn_3.setOnLongClickListener(this)
        color_btn_3.setOnClickListener(this)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.options_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.opt_menu_settings -> {

            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onClick(v: View?) {
        when(v) {
            color_btn_1, color_btn_2, color_btn_3 -> {
                val background = v?.background as ColorDrawable
                updateColor(background.color)
            }
        }
    }

    override fun onLongClick(v: View?): Boolean {
        v?.setBackgroundColor(mCurrentColor)
        return true
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

                        val rawX = touchLocation[0]
                        val rawY = touchLocation[1]

                        //touch outside of drawable bounds not considered
                        if(rawX > 0 && rawX < rgb_color_circle.width && rawY > 0 && rawY < rgb_color_circle.height) {

                            val circleCenter = floatArrayOf((v.width / 2).toFloat(), (v.height / 2).toFloat())
                            inverse.mapPoints(circleCenter)

                            val ccX = rawX - circleCenter[0]
                            val ccY = rawY - circleCenter[1]

                            val r = sqrt((ccX * ccX) + (ccY * ccY))

                            //touch outside of circle not considered
                            if (r < (rgb_color_circle.height / 2)-4) { //4 pixel margin for blurred edge

                                val x = floor(rawX).toInt()
                                val y = floor(rawY).toInt()

                                Log.d(TAG, "touching at x: $x , y: $y")

                                val pixel = (rgb_color_circle.drawable as BitmapDrawable).bitmap.getPixel(x, y)

                                updateColor(pixel)
                            }
                        }
                    }
                }
            }
        }

        return true
    }

    private fun updateColor(color: Int){

        mCurrentColor = color
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

