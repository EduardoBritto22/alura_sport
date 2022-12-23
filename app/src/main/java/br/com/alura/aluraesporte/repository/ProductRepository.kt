package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.alura.aluraesporte.model.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import java.math.BigDecimal

private const val FIRESTORE_PRODUCTS_COLLECTION = "products"

class ProductRepository(
    private val firestore: FirebaseFirestore
) {

    fun searchById(id: String): LiveData<Product> = MutableLiveData<Product>().apply {
        firestore.collection(FIRESTORE_PRODUCTS_COLLECTION)
            .document(id)
            .addSnapshotListener { s, _ ->
                s?.let { document ->
                    document.toObject<ProductDocument>()?.toProduct(document.id)
                        ?.let { product ->
                            value = product
                        }
                }
            }
    }

    fun save(product: Product) = MutableLiveData<Boolean>().apply {

        val productDocument = ProductDocument(
            name = product.name,
            price = product.price.toDouble()
        )
        val document = firestore
            .collection(FIRESTORE_PRODUCTS_COLLECTION)
            .document()
        document.set(productDocument)

        value = true
    }

    fun searchAll() = MutableLiveData<List<Product>>().apply {
        firestore.collection(FIRESTORE_PRODUCTS_COLLECTION)
            .addSnapshotListener { snapShot, _ ->

                snapShot?.let { sQuery ->
                    val products: List<Product> = sQuery.documents.mapNotNull { document ->
                        document.toObject<ProductDocument>()?.toProduct(document.id)
                    }
                    value = products
                }
            }
    }

    private class ProductDocument(
        val name: String = "",
        val price: Double = 0.0
    ) {
        fun toProduct(id: String): Product = Product(
            id = id,
            name = name,
            price = BigDecimal(price)
        )
    }


}
