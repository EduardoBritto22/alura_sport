package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.PaymentListAdapter
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PaymentViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.list_payments.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentsListFragment : BaseFragment() {

    private val adapter: PaymentListAdapter by inject()
    private val viewModel : PaymentViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(
            R.layout.list_payments,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list_payments_recyclerview.adapter = adapter
        viewModel.all().observe(viewLifecycleOwner){
            it?.let { foundPayments ->
                adapter.add(foundPayments)
            }
        }

        appStateViewModel.hasComponents = VisualComponents(
            appBar = true,
            bottomNavigation = true
        )
    }

}