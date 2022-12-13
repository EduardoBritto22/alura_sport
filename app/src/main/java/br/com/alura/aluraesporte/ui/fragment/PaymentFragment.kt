package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.databinding.PaymentBinding
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PaymentViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel

private const val FAIL_TO_CREATE_PAYMENT = "Fail to create payment"
private const val COMPRA_REALIZADA = "Copra realizada"

class PaymentFragment : BaseFragment() {

    private val arguments by navArgs<PaymentFragmentArgs>()
    private val productId by lazy { arguments.productId }
    private val viewModel: PaymentViewModel by viewModel()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()

    private lateinit var chosenProduct: Product

    private var _binding: PaymentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpConfirmPaymentButton()
        searchProduct()
        appStateViewModel.hasComponents = VisualComponents(appBar = true)
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    private fun searchProduct() {
        viewModel.searchProductById(productId).observe(viewLifecycleOwner) {
            it?.let { chosenProduct ->
                this.chosenProduct = chosenProduct
                binding.paymentPrice.text = chosenProduct.price
                    .formatToBrazilianCurrency()
            }
        }
    }

    private fun setUpConfirmPaymentButton() {
        binding.paymentButtonConfirmPayment.setOnClickListener {
            createPayment()?.let(this::save) ?: Toast.makeText(
                context,
                FAIL_TO_CREATE_PAYMENT,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun save(payment: Payment) {
        if (::chosenProduct.isInitialized) {
            viewModel.save(payment)
                .observe(this) {
                    it?.data?.let {
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
        val cardNumber = binding.paymentCardNumber
            .editText?.text.toString()
        val validityDate = binding.paymentDateValidity
            .editText?.text.toString()
        val cvc = binding.paymentCvc
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