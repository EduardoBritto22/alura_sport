package br.com.alura.aluraesporte.ui.fragment

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.databinding.StartBinding
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import org.koin.android.viewmodel.ext.android.sharedViewModel


private const val TAG = "StartFragment"

class StartFragment : Fragment() {

    private val controller by lazy {
        findNavController()
    }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private var _binding: StartBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = StartBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        appStateViewModel.hasComponents = VisualComponents()
        binding.startButtonEnter.setOnClickListener {
            val authUi = AuthUI.getInstance()
            val intent = authUi.createSignInIntentBuilder()
                .setTheme(R.style.AppTheme)
                .setLogo(R.drawable.alurasports_logo)
                .setAvailableProviders(listOf(AuthUI.IdpConfig.EmailBuilder().build()))
                .build()

            signInLauncher.launch(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == Activity.RESULT_OK) {
            // Successfully signed in
            goToProductsList()
        } else {
            response?.let {
                Log.e(TAG, "onSignInResult: Failed to authenticate", response.error)
                view?.snackBar(it.error?.message.orEmpty())
            }
        }
    }

    private fun goToProductsList() {
        val direction = StartFragmentDirections.actionStartToProductsList()
        controller.navigate(direction)
    }

}