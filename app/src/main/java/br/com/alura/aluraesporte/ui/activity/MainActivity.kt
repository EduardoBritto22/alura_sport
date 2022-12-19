package br.com.alura.aluraesporte.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.MainActivityBinding
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val navController by lazy { findNavController(R.id.main_activity_nav) }
    private val viewModel: AppStateViewModel by viewModel()

    private lateinit var binding: MainActivityBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setUpNavController()
    }

    private fun setUpNavController() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            title = destination.label
            viewModel.components.observe(this) {
                it?.let { hasComponents ->
                    if (hasComponents.appBar) {
                        supportActionBar?.show()
                    } else {
                        supportActionBar?.hide()
                    }

                    if (hasComponents.bottomNavigation) {

                        binding.mainActivityBottomNavigation.visibility = View.VISIBLE
                    } else {
                        binding.mainActivityBottomNavigation.visibility = View.GONE
                    }
                }
            }
        }
        binding.mainActivityBottomNavigation
            .setupWithNavController(navController)
    }
}
