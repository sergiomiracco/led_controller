package ch.semi.ledc

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener {

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private val mTAG : String = "COLOR_KOBOLD_LOG"
    fun getTAG(): String = mTAG

    private var mCurrentColor = Color.BLACK

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        supportActionBar?.setDisplayHomeAsUpEnabled(false)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

        supportFragmentManager.beginTransaction()
                //TODO: fix params
            .add(R.id.frag_container, MainFragment.newInstance())
            .commit()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.options_menu, menu)

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.opt_menu_settings -> {
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_container, SettingsFragment())
                    .addToBackStack(null)
                    .commit()
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
            }
        }

        return super.onOptionsItemSelected(item)
    }

}

