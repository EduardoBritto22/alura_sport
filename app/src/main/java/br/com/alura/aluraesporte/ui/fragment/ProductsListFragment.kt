package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.databinding.ListProductsBinding
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProductsAdapter
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProductsViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsListFragment : BaseFragment() {

    private val viewModel: ProductsViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val adapter: ProductsAdapter by inject()
    private var _binding: ListProductsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        appStateViewModel.hasComponents = VisualComponents(
            appBar = true,
            bottomNavigation = true
        )
        binding.productListFab.setOnClickListener {
           val direction = ProductsListFragmentDirections.actionProductsListToProductFormFragment()
            navController.navigate(direction)
        }
    }

    override fun onResume() {
        super.onResume()
        searchProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun searchProducts() {
        viewModel.searchAll().observe(this) { foundedProducts ->
            foundedProducts?.let {
                adapter.update(it)
            }
        }
    }


    private fun setUpRecyclerView() {
        val divisor = DividerItemDecoration(context, VERTICAL)
        binding.listaProdutosRecyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = {selectedProduct ->
            gotToProductDetails(selectedProduct.id)
        }
        binding.listaProdutosRecyclerview.adapter = adapter
    }

    private fun gotToProductDetails(productId: Long) {

        val directions =
            ProductsListFragmentDirections.actionProductsListToProductDetails(productId)
        navController.navigate(directions)
    }

}
