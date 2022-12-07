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
        appStateViewModel.hasComponents = VisualComponents()
        setUpLoginButton()
        setUpSignUpButton()
    }

    private fun setUpSignUpButton() {
        login_button_signup.setOnClickListener {
            goToSignUpUser()
        }
    }

    private fun setUpLoginButton() {
        login_button_login.setOnClickListener {

            clearFieldsErrors()

            val email = login_user_email.editText?.text.toString()
            val password = login_password.editText?.text.toString()

            val isValid = validateFields(email, password)
            if (isValid) {
                authenticate(email, password)
            }
        }
    }

    private fun authenticate(email: String, password: String) {
        viewModel.authenticate(User(email, password)).observe(viewLifecycleOwner) {
            it?.let { resource ->
                if (resource.data) {
                    goToProductsList()
                } else {
                    view?.snackBar(resource.error.orEmpty())
                }
            }
        }
    }

    private fun goToProductsList() {
        val direction = LoginFragmentDirections.actionLoginToProductsList()
        controller.navigate(direction)
    }

    private fun goToSignUpUser() {
        val direction = LoginFragmentDirections.actionLoginToUserSignUp()
        controller.navigate(direction)
    }

    private fun validateFields(email: String, password: String): Boolean {
        var valid = true
        if (email.isBlank()) {
            login_user_email.error = "The email cannot be empty"
            valid = false
        }
        if (password.isBlank()) {
            login_password.error = "The password cannot be empty"
            valid = false
        }

        return valid
    }


    private fun clearFieldsErrors() {
        login_user_email.error = null
        login_password.error = null
    }
}