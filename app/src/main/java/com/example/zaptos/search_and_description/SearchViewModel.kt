package com.example.zaptos.search_and_description

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.zaptos.MasterViewModel
import com.example.zaptos.models.ItemModel

class SearchViewModel : ViewModel() {
    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: MutableState<String> = _searchTextState

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    private var _searchRes = MutableLiveData(mutableStateOf(mutableStateListOf<ItemModel>()))
    val searchRes: MutableState<SnapshotStateList<ItemModel>>? = _searchRes.value
    fun updateSearchRes(my_query: String, vm: MasterViewModel) {
        _searchRes = MutableLiveData(mutableStateOf(mutableStateListOf<ItemModel>()))
        vm.nikeList?.value?.let { list ->
            list.forEach { item ->
//                if (Regex("^$my_query").containsMatchIn(item.title))
                _searchRes.value?.value?.add(
                    item
                )
            }
        }
        vm.adidasList?.value?.let { list ->
            list.forEach { item ->
//                if (Regex("^$my_query").containsMatchIn(item.title))
                _searchRes.value?.value?.add(
                    item
                )
            }
        }
        vm.pumaList?.value?.let { list ->
            list.forEach { item ->
//                if (Regex("^$my_query").containsMatchIn(item.title))
                _searchRes.value?.value?.add(
                    item
                )
            }
        }

        _searchRes.value?.value?.filter { item ->
            Regex("^$my_query").containsMatchIn(item.title)
        }
    }
}