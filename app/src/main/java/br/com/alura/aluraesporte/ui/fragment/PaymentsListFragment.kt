package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.alura.aluraesporte.databinding.ListPaymentsBinding
import br.com.alura.aluraesporte.ui.recyclerview.adapter.PaymentListAdapter
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PaymentViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class PaymentsListFragment : BaseFragment() {

    private val adapter: PaymentListAdapter by inject()
    private val viewModel: PaymentViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()

    private var _binding: ListPaymentsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = ListPaymentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.listPaymentsRecyclerview.adapter = adapter
        viewModel.all().observe(viewLifecycleOwner) {
            it?.let { foundPayments ->
                adapter.add(foundPayments)
            }
        }

        appStateViewModel.hasComponents = VisualComponents(
            appBar = true,
            bottomNavigation = true
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}