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
import br.com.alura.aluraesporte.ui.viewmodel.RegisterUserViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
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
        appStateViewModel.hasComponents = VisualComponents()
        setUpRegisterButton()
    }

    private fun setUpRegisterButton() {
        user_signup_save_button.setOnClickListener {

            clearFieldsErrors()

            val email = user_signup_email.editText?.text.toString()
            val password = user_signup_password.editText?.text.toString()
            val confirmPassword = user_signup_password_confirm.editText?.text.toString()

            val valid = validateFields(email, password, confirmPassword)

            if (valid) {
                register(User(email, password))
            }
        }
    }

    private fun register(user: User) {
        viewModel.registerUser(user).observe(viewLifecycleOwner) {
            it?.let { registered ->
                if (registered.data) {
                    view?.snackBar("New user registered")
                    controller.popBackStack()
                } else {
                    val errorMessage = registered.error ?: "Failure to sign up the user"
                    view?.snackBar(errorMessage)
                }
            }
        }
    }

    private fun validateFields(email: String, password: String, confirmPassword: String): Boolean {
        var valid = true
        if (email.isBlank()) {
            user_signup_email.error = "The email cannot be empty"
            valid = false
        }
        if (password.isBlank()) {
            user_signup_password.error = "The password cannot be empty"
            valid = false
        }
        if (password != confirmPassword) {
            user_signup_password_confirm.error = "The passwords do not match"
            valid = false
        }
        return valid
    }

    private fun clearFieldsErrors() {
        user_signup_email.error = null
        user_signup_password.error = null
        user_signup_password_confirm.error = null
    }

}