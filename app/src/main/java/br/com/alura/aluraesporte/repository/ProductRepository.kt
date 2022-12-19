package br.com.alura.aluraesporte.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

private const val TAG = "ProductRepository"
class ProductRepository(private val dao: ProdutoDAO,
                        private val firestore: FirebaseFirestore
) {

    fun searchAll(): LiveData<List<Product>> = dao.buscaTodos()

    fun searchById(id: Long): LiveData<Product> = dao.buscaPorId(id)

    private fun save() {
        val product = Product(name = "Football boots", price = BigDecimal("129.99"))
        val mappedProduct = mapOf<String, Any>(
            "name" to product.name,
            "price" to product.price.toDouble()
        )

        firestore.collection("products")
            .add(mappedProduct)
            .addOnSuccessListener {
                Log.i(TAG, "onCreate: product saved ${it?.id}")
            }
    }


    fun searchAllInFireStore(): LiveData<List<Product>> {
        val liveData: MutableLiveData<List<Product>> = MutableLiveData<List<Product>>()
        firestore.collection("products")
            .get()
            .addOnSuccessListener {
                it?.let { snapshot ->
                    val products = mutableListOf<Product>()
                    for (document in snapshot.documents) {
                        Log.i(TAG, "onCreate: Product found ${document.data}")
//                        document.data?.let { dados ->
//                            val nome: String = dados["name"] as String
//                            val preco: Double = dados["price"] as Double
//                            val produto = Product(name = nome, price = BigDecimal(preco))
//                            products.add(produto)
//                        }
                        val productDocument = document.toObject<ProductDocument>()
                        productDocument?.let { notNullProductDocument ->
                            products.add(notNullProductDocument.toProduct())
                        }

                    }
                    liveData.value = products
                }
            }
        return liveData
    }
    private class ProductDocument(
        val name: String = "",
        val price: Double = 0.0
    ) {
        fun toProduct(): Product = Product(
            name = name,
            price = BigDecimal(price)
        )
    }


}
