package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppStateViewModel : ViewModel() {
    val appBar: LiveData<Boolean> get() = _appBar
    private var _appBar: MutableLiveData<Boolean> =
        MutableLiveData<Boolean>().also {
            it.value = hasAppBar
        }

    var hasAppBar: Boolean = false
        set(value) {
            field = value
            _appBar.value = value
        }
}
