package br.com.alura.aluraesporte.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.aluraesporte.R
import br.com.alura.aluraesporte.extensions.formatToBrazilianCurrency
import br.com.alura.aluraesporte.model.Payment
import kotlinx.android.synthetic.main.item_payment.view.*

class PaymentListAdapter(
    private val context: Context,
    payments: List<Payment> = listOf()
) : RecyclerView.Adapter<PaymentListAdapter.ViewHolder>() {

    private val payments = payments.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val createdView = LayoutInflater.from(context).inflate(
            R.layout.item_payment,
            parent,
            false
        )
        return ViewHolder(createdView)
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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val id by lazy {
            itemView.item_payment_id
        }
        private val price by lazy {
            itemView.item_payment_price
        }

        fun attach(payment: Payment) {
            id.text = payment.id.toString()
            price.text = payment.price.formatToBrazilianCurrency()
        }

    }
}
