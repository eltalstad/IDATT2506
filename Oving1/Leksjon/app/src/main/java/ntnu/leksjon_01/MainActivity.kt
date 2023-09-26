package ntnu.leksjon_01

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(meny: Menu): Boolean {
        super.onCreateOptionsMenu(meny)
        meny.add("Eirik")
        meny.add("Leer Talstad")
        Log.d("Leksjon" , "meny laget") //vises i LogCat
        return true // gjor at menyen vil vises
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.title!!.equals("Eirik")) {
            Log.w("Leksjon", "Eirik er trykket av brukeren")
        }
        if (item.title!!.equals("Leer Talstad")) {
            Log.e("Leksjon", "Leer Talstad er trykket av brukeren")
        }
        return true // hvorfor true her? Se API-dokumentasjonen!!
    }
}