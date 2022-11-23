package br.com.alura.aluraesporte.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.alura.aluraesporte.database.AppDatabase
import br.com.alura.aluraesporte.database.dao.PagamentoDAO
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Produto
import br.com.alura.aluraesporte.repository.PagamentoRepository
import br.com.alura.aluraesporte.repository.ProdutoRepository
import br.com.alura.aluraesporte.ui.fragment.ProductDetailsFragment
import br.com.alura.aluraesporte.ui.fragment.ProductsListFragment
import br.com.alura.aluraesporte.ui.fragment.PaymentFragment
import br.com.alura.aluraesporte.ui.recyclerview.adapter.ProdutosAdapter
import br.com.alura.aluraesporte.ui.viewmodel.ProductDetailsViewModel
import br.com.alura.aluraesporte.ui.viewmodel.PagamentoViewModel
import br.com.alura.aluraesporte.ui.viewmodel.ProdutosViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.dsl.viewModel
//import org.koin.androidx.viewmodel.dsl.viewModel
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
                            Produto(
                                nome = "Bola de futebol",
                                preco = BigDecimal("100")
                            ), Produto(
                                nome = "Camisa",
                                preco = BigDecimal("80")
                            ),
                            Produto(
                                nome = "Chuteira",
                                preco = BigDecimal("120")
                            ), Produto(
                                nome = "Bermuda",
                                preco = BigDecimal("60")
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
    single<ProdutoRepository> { ProdutoRepository(get()) }
    single<PagamentoRepository> { PagamentoRepository(get()) }
}

val uiModule = module {
    factory<ProductDetailsFragment> { ProductDetailsFragment() }
    factory<ProductsListFragment> { ProductsListFragment() }
    factory<PaymentFragment> { PaymentFragment() }
    factory<ProdutosAdapter> { ProdutosAdapter(get()) }
}

val viewModelModule = module {
    viewModel<ProdutosViewModel> { ProdutosViewModel(get()) }
    viewModel<ProductDetailsViewModel> { (id: Long) -> ProductDetailsViewModel(produtoId = id,get()) }
    viewModel<PagamentoViewModel> { PagamentoViewModel(get(), get()) }
}