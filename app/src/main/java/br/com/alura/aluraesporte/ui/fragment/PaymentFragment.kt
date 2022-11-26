package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Pagamento
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import kotlinx.android.synthetic.main.pagamento.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"
private const val COMPRA_REALIZADA = "Compra realizada"

class PaymentFragment : BaseFragment() {

    private val arguments by navArgs<PaymentFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: PagamentoViewModel by viewModel()
    private lateinit var produtoEscolhido: Produto

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.pagamento,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configuraBotaoConfirmaPagamento()
        buscaProduto()
    }

    private fun buscaProduto() {
        viewModel.buscaProdutoPorId(productId).observe(viewLifecycleOwner) {
            it?.let { produtoEncontrado ->
                produtoEscolhido = produtoEncontrado
                pagamento_preco.text = produtoEncontrado.preco
                    .formatParaMoedaBrasileira()
            }
        }
    }

    private fun configuraBotaoConfirmaPagamento() {
        pagamento_botao_confirma_pagamento.setOnClickListener {
            createPayment()?.let(this::save) ?: Toast.makeText(
                context,
                FALHA_AO_CRIAR_PAGAMENTO,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun save(pagamento: Pagamento) {
        if (::produtoEscolhido.isInitialized) {
            viewModel.salva(pagamento)
                .observe(this) {
                    it?.dado?.let {
                        Toast.makeText(
                            context,
                            COMPRA_REALIZADA,
                            Toast.LENGTH_SHORT
                        ).show()
                        goToProductsList()
                    }
                }
        }
    }

    private fun goToProductsList() {
        navController.navigate(PaymentFragmentDirections.actionPaymentToProductsList())
    }

    private fun createPayment(): Pagamento? {
        val numeroCartao = pagamento_numero_cartao
            .editText?.text.toString()
        val dataValidade = pagamento_data_validade
            .editText?.text.toString()
        val cvc = pagamento_cvc
            .editText?.text.toString()
        return generatePayment(numeroCartao, dataValidade, cvc)
    }

    private fun generatePayment(
        numeroCartao: String,
        dataValidade: String,
        cvc: String
    ): Pagamento? = try {
        Pagamento(
            numeroCartao = numeroCartao.toInt(),
            dataValidade = dataValidade,
            cvc = cvc.toInt(),
            produtoId = productId,
            preco = produtoEscolhido.preco
        )
    } catch (e: NumberFormatException) {
        null
    }

}