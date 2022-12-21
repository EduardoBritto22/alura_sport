package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

private const val FIRESTORE_PRODUCTS_COLLECTION = "products"

class ProductRepository(
    private val dao: ProdutoDAO,
    private val firestore: FirebaseFirestore
) {

    fun searchById(id: Long): LiveData<Product> = dao.buscaPorId(id)

    fun save(product: Product) = MutableLiveData<Boolean>().apply {

        val productDocument = ProductDocument(
            name = product.name,
            price = product.price.toDouble()
        )

        firestore.collection(FIRESTORE_PRODUCTS_COLLECTION)
            .add(productDocument)
            .addOnSuccessListener {
                value = true
            }
            .addOnFailureListener {
                value = false
            }
    }

    fun searchAll() = MutableLiveData<List<Product>>().apply {
        firestore.collection(FIRESTORE_PRODUCTS_COLLECTION)
            .addSnapshotListener { snapShot, _ ->

                snapShot?.let { sQuery ->
                    val products: List<Product> = sQuery.documents.mapNotNull { document ->
                        document.toObject<ProductDocument>()?.toProduct()
                    }
                    value = products
                }
            }
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
