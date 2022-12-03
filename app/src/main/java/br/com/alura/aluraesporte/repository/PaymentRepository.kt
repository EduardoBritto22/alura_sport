package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.database.dao.PagamentoDAO
import br.com.alura.aluraesporte.model.Payment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PaymentRepository(private val dao: PagamentoDAO) {

    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    fun save(payment: Payment): LiveData<Resource<Long>> {
        return MutableLiveData<Resource<Long>>().also { liveDate ->
            scope.launch {
                val idPagamento = dao.salva(payment)
                liveDate.postValue(Resource(idPagamento))
            }
        }
    }

    fun all(): LiveData<List<Payment>> = dao.all()

}
