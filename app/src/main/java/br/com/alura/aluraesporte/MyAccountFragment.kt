package br.com.alura.aluraesporte

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alura.aluraesporte.ui.fragment.BaseFragment
import br.com.alura.aluraesporte.ui.viewmodel.MyAccountViewModel
import kotlinx.android.synthetic.main.fragment_my_account.*
import org.koin.android.viewmodel.ext.android.viewModel

class MyAccountFragment : BaseFragment() {

    private val viewModel: MyAccountViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_account, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.user.observe(viewLifecycleOwner){
            it?.let {user->
                my_account_email.text = user.email
            }
        }
    }
}