package br.com.alura.aluraesporte.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.databinding.LoginBinding
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {

    private val controller by lazy { findNavController() }
    private val viewModel: LoginViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private var _binding: LoginBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = LoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appStateViewModel.hasComponents = VisualComponents()
        setUpLoginButton()
        setUpSignUpButton()
    }

    private fun setUpSignUpButton() {
        binding.loginButtonSignup.setOnClickListener {
            goToSignUpUser()
        }
    }

    private fun setUpLoginButton() {
        binding.loginButtonLogin.setOnClickListener {

            clearFieldsErrors()

            val email = binding.loginUserEmail.editText?.text.toString()
            val password = binding.loginPassword.editText?.text.toString()

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
            binding.loginUserEmail.error = "The email cannot be empty"
            valid = false
        }
        if (password.isBlank()) {
            binding.loginPassword.error = "The password cannot be empty"
            valid = false
        }

        return valid
    }


    private fun clearFieldsErrors() {
        binding.loginUserEmail.error = null
        binding.loginPassword.error = null
    }
}