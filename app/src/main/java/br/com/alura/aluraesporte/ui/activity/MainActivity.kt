package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import br.com.alura.aluraesporte.R

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.main_activity_nav) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController.addOnDestinationChangedListener{ _, destination, _ ->
            title = destination.label
            when(destination.id){
                R.id.productsList -> supportActionBar?.show()
                R.id.login -> supportActionBar?.hide()
            }
        }
    }
}
