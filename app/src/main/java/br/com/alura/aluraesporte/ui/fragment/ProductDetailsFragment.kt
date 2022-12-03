package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProductDetailsViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : BaseFragment() {

    private val arguments by navArgs<ProductDetailsFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(productId) }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.detalhes_produto,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchProduct()
        setUpBuyButton()
        appStateViewModel.hasComponents = VisualComponents(appBar = true)
    }

    private fun setUpBuyButton() {
        detalhes_produto_botao_comprar.setOnClickListener {
            viewModel.produtoEncontrado.value?.let {
                goToPayment()
            }
        }
    }

    private fun goToPayment() {
        val directions =
        ProductDetailsFragmentDirections.actionProductDetailsToPayment(productId)
        navController.navigate(directions)
    }

    private fun searchProduct() {
        viewModel.produtoEncontrado.observe(viewLifecycleOwner) {
            it?.let { produto ->
                detalhes_produto_nome.text = produto.nome
                detalhes_produto_preco.text = produto.price.formatToBrazilianCurrency()
            }
        }
    }

}