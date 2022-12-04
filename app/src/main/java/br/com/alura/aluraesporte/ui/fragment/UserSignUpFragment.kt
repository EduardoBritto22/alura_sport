package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.RegisterUserViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.user_signup.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserSignUpFragment : Fragment() {

    private val controller by lazy {
        findNavController()
    }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val viewModel: RegisterUserViewModel by viewModel()


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
        user_signup_save_button.setOnClickListener {

            val email = user_signup_email.editText?.text.toString()
            val password = user_signup_password.editText?.text.toString()

            viewModel.registerUser(email, password).observe(viewLifecycleOwner){
                it?.let { registered ->
                    if(registered.data){
                        controller.popBackStack()
                    } else {
                        val errorMessage = registered.error ?: "Failure to sign up the user"
                        Snackbar.make(
                            view,
                            errorMessage,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
        appStateViewModel.hasComponents = VisualComponents()
    }

}