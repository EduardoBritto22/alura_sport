package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.user_signup.*
import org.koin.android.viewmodel.ext.android.sharedViewModel

class UserSignUpFragment : Fragment() {

    private val controller by lazy {
        findNavController()
    }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.user_signup,
            container,
            false
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user_signin_save_button.setOnClickListener {
            controller.popBackStack()
        }
        appStateViewModel.hasComponents = VisualComponents()
    }

}