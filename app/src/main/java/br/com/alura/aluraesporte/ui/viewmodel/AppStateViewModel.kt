package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {
    val components: LiveData<VisualComponents> get() = _components

    private var _components: MutableLiveData<VisualComponents> =
        MutableLiveData<VisualComponents>().also {
            it.value = hasComponents
        }

    var hasComponents: VisualComponents = VisualComponents()
        set(value) {
            field = value
            _components.value = value
        }
}

class VisualComponents (
    val appBar: Boolean = false,
    val bottomNavigation: Boolean = false
)