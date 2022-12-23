package br.com.alura.aluraesporte.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import br.com.alura.aluraesporte.database.AppDatabase
import br.com.alura.aluraesporte.database.dao.PaymentDAO
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository
import br.com.alura.aluraesporte.ui.fragment.PaymentFragment
import br.com.alura.aluraesporte.ui.fragment.ProductDetailsFragment
import br.com.alura.aluraesporte.ui.fragment.ProductsListFragment
import br.com.alura.aluraesporte.ui.recyclerview.adapter.PaymentListAdapter
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProductsAdapter
import br.com.alura.aluraesporte.ui.viewmodel.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private const val NOME_BANCO_DE_DADOS = "aluraesporte.db"

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
}

val firebaseModule = module {
    single<FirebaseAuth> { Firebase.auth }
    single<FirebaseFirestore> { Firebase.firestore }
}

val daoModule = module {
    single<PaymentDAO> { get<AppDatabase>().paymentDAO() }
    single<ProductRepository> { ProductRepository(get()) }
    single<PaymentRepository> { PaymentRepository(get()) }
    single { FirebaseAuthRepository(get()) }
    single { PreferenceManager.getDefaultSharedPreferences(get()) }
}

val uiModule = module {
    factory<ProductDetailsFragment> { ProductDetailsFragment() }
    factory<ProductsListFragment> { ProductsListFragment() }
    factory<PaymentFragment> { PaymentFragment() }
    factory<ProductsAdapter> { ProductsAdapter(get()) }
    factory { PaymentListAdapter(get()) }
}

val viewModelModule = module {
    viewModel<ProductsViewModel> { ProductsViewModel(get()) }
    viewModel<ProductDetailsViewModel> { (id: String) -> ProductDetailsViewModel(productId = id,get()) }
    viewModel<PaymentViewModel> { PaymentViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { AppStateViewModel() }
    viewModel { RegisterUserViewModel(get()) }
    viewModel { MyAccountViewModel(get()) }
    viewModel { ProductFormViewModel(get()) }
}
