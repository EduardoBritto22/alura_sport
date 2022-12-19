package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.databinding.UserSignupBinding
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.RegisterUserViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class UserSignUpFragment : Fragment() {

    private val controller by lazy {
        findNavController()
    }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val viewModel: RegisterUserViewModel by viewModel()
    private var _binding: UserSignupBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appStateViewModel.hasComponents = VisualComponents()
        setUpRegisterButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun setUpRegisterButton() {
        binding.userSignupSaveButton.setOnClickListener {

            clearFieldsErrors()

            val email = binding.userSignupEmail.editText?.text.toString()
            val password = binding.userSignupPassword.editText?.text.toString()
            val confirmPassword = binding.userSignupPasswordConfirm.editText?.text.toString()

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
            binding.userSignupEmail.error = "The email cannot be empty"
            valid = false
        }
        if (password.isBlank()) {
            binding.userSignupPassword.error = "The password cannot be empty"
            valid = false
        }
        if (password != confirmPassword) {
            binding.userSignupPasswordConfirm.error = "The passwords do not match"
            valid = false
        }
        return valid
    }

    private fun clearFieldsErrors() {
        binding.userSignupEmail.error = null
        binding.userSignupPassword.error = null
        binding.userSignupPasswordConfirm.error = null
    }

}