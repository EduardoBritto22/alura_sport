package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.databinding.ProductDetailsBinding
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProductDetailsViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : BaseFragment() {

    private val arguments by navArgs<ProductDetailsFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: ProductDetailsViewModel by viewModel { parametersOf(productId) }
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private var _binding: ProductDetailsBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchProduct()
        setUpBuyButton()
        appStateViewModel.hasComponents = VisualComponents(appBar = true)
    }

    private fun setUpBuyButton() {
        binding.detalhesProdutoBotaoComprar.setOnClickListener {
            viewModel.produtoEncontrado.value?.let {
                goToPayment()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun goToPayment() {
        val directions =
        ProductDetailsFragmentDirections.actionProductDetailsToPayment(productId)
        navController.navigate(directions)
    }

    private fun searchProduct() {
        viewModel.produtoEncontrado.observe(viewLifecycleOwner) {
            it?.let { product ->
                binding.detalhesProdutoNome.text = product.name
                binding.detalhesProdutoPreco.text = product.price.formatToBrazilianCurrency()
            }
        }
    }

}