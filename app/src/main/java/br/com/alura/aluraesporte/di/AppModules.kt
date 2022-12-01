package br.com.alura.aluraesporte.di

import androidx.preference.PreferenceManager
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.aluraesporte.database.AppDatabase
import br.com.alura.aluraesporte.database.dao.PagamentoDAO
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.repository.LoginRepository
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository
import br.com.alura.aluraesporte.ui.fragment.ProductDetailsFragment
import br.com.alura.aluraesporte.ui.fragment.ProductsListFragment
import br.com.alura.aluraesporte.ui.fragment.PaymentFragment
import br.com.alura.aluraesporte.ui.recyclerview.adapter.PaymentListAdapter
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProductsAdapter
import br.com.alura.aluraesporte.ui.viewmodel.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.math.BigDecimal

private const val NOME_BANCO_DE_DADOS = "aluraesporte.db"
private const val NOME_BANCO_DE_DADOS_TESTE = "aluraesporte-test.db"

val testeDatabaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS_TESTE
        ).fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    CoroutineScope(IO).launch {
                        val dao: ProdutoDAO by inject()
                        dao.salva(
                            Product(
                                nome = "Bola de futebol",
                                price = BigDecimal("100")
                            ), Product(
                                nome = "Camisa",
                                price = BigDecimal("80")
                            ),
                            Product(
                                nome = "Chuteira",
                                price = BigDecimal("120")
                            ), Product(
                                nome = "Bermuda",
                                price = BigDecimal("60")
                            )
                        )
                    }
                }
            }).build()
    }
}

val databaseModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(
            get(),
            AppDatabase::class.java,
            NOME_BANCO_DE_DADOS
        ).build()
    }
}

val daoModule = module {
    single<ProdutoDAO> { get<AppDatabase>().produtoDao() }
    single<PagamentoDAO> { get<AppDatabase>().pagamentoDao() }
    single<ProductRepository> { ProductRepository(get()) }
    single<PaymentRepository> { PaymentRepository(get()) }
    single { LoginRepository(get()) }
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
    viewModel<ProductDetailsViewModel> { (id: Long) -> ProductDetailsViewModel(produtoId = id,get()) }
    viewModel<PaymentViewModel> { PaymentViewModel(get(), get()) }
    viewModel { LoginViewModel(get()) }
    viewModel { AppStateViewModel() }
}