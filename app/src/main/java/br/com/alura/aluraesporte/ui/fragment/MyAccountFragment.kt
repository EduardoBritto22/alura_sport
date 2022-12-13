package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alura.aluraesporte.databinding.FragmentMyAccountBinding
import br.com.alura.aluraesporte.ui.viewmodel.MyAccountViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class MyAccountFragment : BaseFragment() {

    private val viewModel: MyAccountViewModel by viewModel()
    private var _binding: FragmentMyAccountBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner){
            it?.let {user->
                binding.myAccountEmail.text = user.email
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}