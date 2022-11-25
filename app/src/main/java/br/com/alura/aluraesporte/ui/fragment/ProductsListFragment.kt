package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.*
import android.widget.LinearLayout.VERTICAL
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.LoginViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import kotlinx.android.synthetic.main.lista_produtos.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ProductsListFragment : Fragment() {

    private val viewModel: ProdutosViewModel by viewModel()
    private val loginViewModel: LoginViewModel by viewModel()
    private val adapter: ProdutosAdapter by inject()
    private val navController by lazy {
        findNavController()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isLogged()
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
        setUpMenu()
    }

    private fun searchProducts() {
        viewModel.buscaTodos().observe(this) { foundedProducts ->
            foundedProducts?.let {
                adapter.update(it)
            }
        }
    }

    private fun setUpMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_products_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.menu_products_list_logout -> {
                        loginViewModel.logOut()
                        goToLogin()
                        true
                    }

                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun goToLogin() {
        navController.navigate(ProductsListFragmentDirections.actionProductsListToLogin())
    }


    private fun isLogged() {
        if (!loginViewModel.isLogged()) {
            goToLogin()
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
