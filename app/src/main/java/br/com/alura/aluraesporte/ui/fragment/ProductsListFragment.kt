package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProductsAdapter
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProductsViewModel
import kotlinx.android.synthetic.main.lista_produtos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsListFragment : BaseFragment() {

    private val viewModel: ProductsViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val adapter: ProductsAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        searchProducts()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.lista_produtos,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        appStateViewModel.hasAppBar = true
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
        lista_produtos_recyclerview.addItemDecoration(divisor)
        adapter.onItemClickListener = {selectedProduct ->
            gotToProductDetails(selectedProduct.id)
        }
        lista_produtos_recyclerview.adapter = adapter
    }

    private fun gotToProductDetails(productId: Long) {

        val directions =
            ProductsListFragmentDirections.actionProductsListToProductDetails(productId)
        navController.navigate(directions)
    }

}
