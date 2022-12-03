package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PaymentViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import kotlinx.android.synthetic.main.pagamento.*
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val FALHA_AO_CRIAR_PAGAMENTO = "Falha ao criar pagamento"
private const val COMPRA_REALIZADA = "Compra realizada"

class PaymentFragment : BaseFragment() {

    private val arguments by navArgs<PaymentFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: PaymentViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()

    private lateinit var chosenProduct: Product

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
        setUpConfirmPaymentButton()
        searchProduct()
        appStateViewModel.hasComponents = VisualComponents(appBar = true)
    }

    private fun searchProduct() {
        viewModel.searchProductById(productId).observe(viewLifecycleOwner) {
            it?.let { chosenProduct ->
                this.chosenProduct = chosenProduct
                pagamento_preco.text = chosenProduct.price
                    .formatToBrazilianCurrency()
            }
        }
    }

    private fun setUpConfirmPaymentButton() {
        pagamento_botao_confirma_pagamento.setOnClickListener {
            createPayment()?.let(this::save) ?: Toast.makeText(
                context,
                FALHA_AO_CRIAR_PAGAMENTO,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun save(payment: Payment) {
        if (::chosenProduct.isInitialized) {
            viewModel.save(payment)
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

    private fun createPayment(): Payment? {
        val cardNumber = pagamento_numero_cartao
            .editText?.text.toString()
        val validityDate = pagamento_data_validade
            .editText?.text.toString()
        val cvc = pagamento_cvc
            .editText?.text.toString()
        return generatePayment(cardNumber, validityDate, cvc)
    }

    private fun generatePayment(
        cardNumber: String,
        validityDate: String,
        cvc: String
    ): Payment? = try {
        Payment(
            cardNumber = cardNumber.toInt(),
            validityDate = validityDate,
            cvc = cvc.toInt(),
            produtoId = productId,
            price = chosenProduct.price
        )
    } catch (e: NumberFormatException) {
        null
    }

}