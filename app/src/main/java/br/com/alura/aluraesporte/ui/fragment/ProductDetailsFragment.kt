package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.ui.viewmodel.DetalhesProdutoViewModel
import kotlinx.android.synthetic.main.detalhes_produto.*
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailsFragment : Fragment() {

    private val arguments by navArgs<ProductDetailsFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: DetalhesProdutoViewModel by viewModel { parametersOf(productId) }

    private val navController by lazy {
        findNavController()
    }
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
                detalhes_produto_preco.text = produto.preco.formatParaMoedaBrasileira()
            }
        }
    }

}