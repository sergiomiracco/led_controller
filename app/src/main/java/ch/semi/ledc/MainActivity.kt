package ch.semi.ledc

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val _channels: RadioGroup = channel_radio_group
        val _chInside: RadioButton = inside_radio
        val _chOutside: RadioButton = outside_radio
        val _chAll: RadioButton = all_radio

        val _colorCircle: ImageView = rgb_color_circle

        val _displayAll: View = color_display_all

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {



        return super.onCreateOptionsMenu(menu)

    }


}
