package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatParaMoedaBrasileira
import br.com.alura.aluraesporte.model.Product
import kotlinx.android.synthetic.main.item_produto.view.*

class ProdutosAdapter(
    private val context: Context,
    private val products: MutableList<Product> = mutableListOf(),
    var onItemClickListener: (product: Product) -> Unit = {}
) : RecyclerView.Adapter<ProdutosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewCriada = LayoutInflater.from(context).inflate(
            R.layout.item_produto,
            parent,
            false
        )
        return ViewHolder(viewCriada)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.vincula(products[position])
    }

    fun update(produtosNovos: List<Product>) {
        notifyItemRangeRemoved(0, products.size)
        products.clear()
        products.addAll(produtosNovos)
        notifyItemRangeInserted(0, products.size)
    }

    inner class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var product: Product
        private val campoNome by lazy { itemView.item_produto_nome }
        private val campoPreco by lazy { itemView.item_produto_preco }

        init {
            itemView.setOnClickListener {
                if (::product.isInitialized) {
                    onItemClickListener(product)
                }
            }
        }

        fun vincula(product: Product) {
            this.product = product
            campoNome.text = product.nome
            campoPreco.text = product.price.formatParaMoedaBrasileira()
        }

    }

}
