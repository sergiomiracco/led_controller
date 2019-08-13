package ch.semi.ledc

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.RippleDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlin.math.floor
import kotlin.math.sqrt


class MainFragment : Fragment(), View.OnLongClickListener, View.OnClickListener {

    private lateinit var sharedPreference: SharedPreferences

    private var listener: OnFragmentInteractionListener? = null

    private fun getTAG(): String { return (activity as MainActivity).getTAG() }

    override fun onLongClick(v: View?): Boolean {

        val activeId = channel_radio_group.checkedRadioButtonId
        var bg: ColorDrawable? = null

        when(activeId){
            inside_radio.id -> {
                bg = color_display_inside.background as ColorDrawable
            }
            outside_radio.id -> {
                bg = color_display_outside.background as ColorDrawable
            }
            all_radio.id -> {
                bg = color_display_all.background as ColorDrawable
            }
        }
        if (bg != null) {
            v?.setBackgroundColor(bg.color)
        }
        return true
    }

    private fun rectifyTouchLocation(v: View, x: Float, y: Float): FloatArray{

        val inverse = Matrix()
        (v as ImageView).imageMatrix.invert(inverse)
        val touchLocation = floatArrayOf(x, y)
        inverse.mapPoints(touchLocation)

        val density = resources.displayMetrics.density
        touchLocation[0] /= density
        touchLocation[1] /= density

        return touchLocation
    }

    private fun touchWithinCircle(v: View, x: Float, y: Float): Boolean {

        var check = false

        //touch outside of drawable bounds not considered
        if(x > 0 && x < v.width && y > 0 && y < v.height) {

            val inverse = Matrix()
            (v as ImageView).imageMatrix.invert(inverse)

            val circleCenter = floatArrayOf((v.width / 2).toFloat(), (v.height / 2).toFloat())

            val ccX = x - circleCenter[0]
            val ccY = y - circleCenter[1]

            val r = sqrt((ccX * ccX) + (ccY * ccY))

            //touch outside of circle not considered
            if (r < (v.height / 2)-4) { //4 pixel margin for blurred edge
                check = true
            }
        }

        Log.d(getTAG(), "touching at x: $x , y: $y, in circle = $check")

        return check

    }

    private fun getTextFromColor(color: Int) : String {

        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)

        return "R:$r G:$g B:$b"

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
        Log.i(getTAG(), "$rgbText @$idName")

    }

    override fun onClick(v: View?) {
        when(v) {
            color_btn_1, color_btn_2, color_btn_3 -> {
                if(v != null){
                    val bg = v.background
                    if(bg is ColorDrawable) {
                        updateColor(bg.color)
                    }
                }
            }
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val shutterOn = sharedPreference.getBoolean("shutter", false)
        val rainbowOn = sharedPreference.getBoolean("rainbow", false)

        val view = inflater.inflate(R.layout.fragment_main, container, false)

        view.shutter_layout.visibility = if(shutterOn) View.VISIBLE else View.GONE
        view.rainbow_layout.visibility = if(rainbowOn) View.VISIBLE else View.GONE
        
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.rgb_color_circle.setOnTouchListener{ v: View, m: MotionEvent ->

            when(v) {
                rgb_color_circle -> {
                    when(m.action){
                        MotionEvent.ACTION_MOVE, MotionEvent.ACTION_DOWN -> {

                            val touchLocation = rectifyTouchLocation(v, m.x, m.y)
                            if(touchWithinCircle(v, m.x, m.y)){

                                val cxs = rgb_color_circle.left
                                val cxe = rgb_color_circle.right
                                val cys = rgb_color_circle.top
                                val cye = rgb_color_circle.bottom
                                val x = floor(touchLocation[0]).toInt()
                                val y = floor(touchLocation[1]).toInt()

                                val pixel = (rgb_color_circle.drawable as BitmapDrawable).bitmap.getPixel(x, y)
                                updateColor(pixel)

                            }

                        }
                    }
                }
            }

            true
        }

        color_btn_1.setOnLongClickListener(this)
        color_btn_1.setOnClickListener(this)
        color_btn_2.setOnLongClickListener(this)
        color_btn_2.setOnClickListener(this)
        color_btn_3.setOnLongClickListener(this)
        color_btn_3.setOnClickListener(this)

    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity){
            sharedPreference = PreferenceManager.getDefaultSharedPreferences(context)
        }
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {

        fun newInstance() = MainFragment()

     }
}
