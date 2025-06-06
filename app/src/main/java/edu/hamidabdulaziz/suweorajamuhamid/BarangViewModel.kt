package edu.unikom.suweorajamuhamid.viewmodel // Or a more specific package like ui.barang

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import edu.unikom.suweorajamuhamid.data.db.entity.Barang
import edu.unikom.suweorajamuhamid.data.repository.InventoryRepository
import javax.inject.Inject

@HiltViewModel
class BarangViewModel @Inject constructor(
    private val repository: InventoryRepository
) : ViewModel() {

    val allBarang: LiveData<List<Barang>> = repository.getAllBarang()

    fun getBarangById(id: Int): LiveData<Barang?> {
        return repository.getBarangById(id)
    }

    fun insertBarang(barang: Barang) = viewModelScope.launch {
        repository.insertBarang(barang)
    }

    fun updateBarang(barang: Barang) = viewModelScope.launch {
        repository.updateBarang(barang)
    }

    fun deleteBarang(barang: Barang) = viewModelScope.launch {
        repository.deleteBarang(barang)
    }
}