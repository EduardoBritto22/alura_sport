package br.com.alura.aluraesporte.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.alura.aluraesporte.databinding.FragmentProductFormBinding
import br.com.alura.aluraesporte.extensions.snackBar
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.ui.viewmodel.AppStateViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProductFormViewModel
import br.com.alura.aluraesporte.ui.viewmodel.VisualComponents
import org.koin.android.viewmodel.ext.android.sharedViewModel
import org.koin.android.viewmodel.ext.android.viewModel
import java.math.BigDecimal

class ProductFormFragment : BaseFragment() {

    private val arguments by navArgs<ProductFormFragmentArgs>()
    private val appStateViewModel: AppStateViewModel by sharedViewModel()
    private val viewModel: ProductFormViewModel by viewModel()
    private val controller: NavController by lazy {
        findNavController()
    }
    private var _binding: FragmentProductFormBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val productId by lazy {
        arguments.productId
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductFormBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tryToSearchProduct()

        appStateViewModel.hasComponents = VisualComponents(
            appBar = true,
            bottomNavigation = false
        )
        setUpSaveButton()
    }

    private fun setUpSaveButton() {
        binding.formProductButtonSave.setOnClickListener {
            val product = createProduct()
            save(product)
        }
    }

    private fun save(product: Product) {
        viewModel.save(product).observe(viewLifecycleOwner) {
            it?.let { saved ->
                if (saved) {
                    controller.popBackStack()
                    return@observe
                }
            }
            view?.snackBar("Fail to save a product")
        }
    }

    private fun createProduct(): Product {
        val name = binding.formProductFieldName.editText?.text.toString()
        val price = binding.formProductFieldPrice.editText?.text.toString()
        return Product(
            id = productId,
            name = name,
            price = BigDecimal(price)
        )
    }

    private fun tryToSearchProduct() {
        productId?.let { id ->
            viewModel.searchBy(id).observe(viewLifecycleOwner) {
                it?.let { product ->
                    val name = product.name
                    val price = product.price.toString()
                    binding.formProductFieldName.editText?.setText(name)
                    binding.formProductFieldPrice.editText?.setText(price)
                    requireActivity().title = "Edit product"
                }
            }
        }
    }
}