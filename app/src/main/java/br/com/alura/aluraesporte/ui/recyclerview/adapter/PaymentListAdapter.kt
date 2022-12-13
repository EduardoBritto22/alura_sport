package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.databinding.ItemPaymentBinding
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Payment

class PaymentListAdapter(
    private val context: Context,
    payments: List<Payment> = listOf()
) : RecyclerView.Adapter<PaymentListAdapter.ViewHolder>() {

    private val payments = payments.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val itemBinding = ItemPaymentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int = payments.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val payment = payments[position]
        holder.attach(payment)
    }

    fun add(payments: List<Payment>) {
        notifyItemRangeRemoved(0, this.payments.size)
        this.payments.clear()
        this.payments.addAll(payments)
        notifyItemRangeInserted(0, this.payments.size)
    }

    class ViewHolder(itemView: ItemPaymentBinding) : RecyclerView.ViewHolder(itemView.root) {

        private val id by lazy {
            itemView.itemPaymentId
        }
        private val price by lazy {
            itemView.itemPaymentPrice
        }

        fun attach(payment: Payment) {
            id.text = payment.id.toString()
            price.text = payment.price.formatToBrazilianCurrency()
        }

    }
}
