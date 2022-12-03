package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import kotlinx.android.synthetic.main.main_activity.*
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.main_activity_nav) }
    private val viewModel: AppStateViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
            viewModel.appBar.observe(this) {
                it?.let { hasAppBar ->
                    if (hasAppBar) {
                        supportActionBar?.show()
                    } else {
                        supportActionBar?.hide()
                    }
                }
            }
        }
        main_activity_bottom_navigation
            .setupWithNavController(navController)
    }
}
