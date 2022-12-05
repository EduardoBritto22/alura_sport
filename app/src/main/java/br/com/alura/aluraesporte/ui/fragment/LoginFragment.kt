package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.login.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val controller by lazy { findNavController() }
    private val viewModel: LoginViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.login,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_button_login.setOnClickListener {
            val email = login_user_email.editText?.text.toString()
            val password = login_password.editText?.text.toString()

            viewModel.authenticate(User(email,password)).observe(viewLifecycleOwner){
                it?.let { resource ->
                    if(resource.data){
                        goToProductsList()
                    }else {
                        view.snackBar(resource.error.orEmpty())
                    }
                }
            }

        }

        login_button_signup.setOnClickListener {
            goToSignUpUser()
        }

        appStateViewModel.hasComponents = VisualComponents()

    }

    private fun goToProductsList() {
        val direction = LoginFragmentDirections.actionLoginToProductsList()
        controller.navigate(direction)
    }
    private fun goToSignUpUser() {
        val direction = LoginFragmentDirections.actionLoginToUserSignUp()
        controller.navigate(direction)
    }

}