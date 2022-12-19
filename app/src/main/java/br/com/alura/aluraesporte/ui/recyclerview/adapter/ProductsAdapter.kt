package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.databinding.ItemProdutoBinding
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Product

class ProductsAdapter(
    private val context: Context,
    private val products: MutableList<Product> = mutableListOf(),
    var onItemClickListener: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProdutoBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(products[position])
    }

    fun update(newProducts: List<Product>) {
        notifyItemRangeRemoved(0, products.size)
        products.clear()
        products.addAll(newProducts)
        notifyItemRangeInserted(0, products.size)
    }

    inner class ViewHolder(itemView: ItemProdutoBinding) :
        RecyclerView.ViewHolder(itemView.root) {

        private lateinit var product: Product
        private val nameField by lazy { itemView.itemProdutoNome }
        private val priceField by lazy { itemView.itemProdutoPreco }

        init {
            itemView.root.setOnClickListener {
                if (::product.isInitialized) {
                    onItemClickListener(product)
                }
            }
        }

        fun bind(product: Product) {
            this.product = product
            nameField.text = product.name
            priceField.text = product.price.formatToBrazilianCurrency()
        }

    }

}
