package com.streamliners.feature.compose.search_bar

import androidx.compose.runtime.mutableStateOf
import com.streamliners.base.BaseViewModel
import com.streamliners.base.ext.execute
import com.streamliners.base.taskState.loadingTaskStateOf
import com.streamliners.base.taskState.update
import com.streamliners.base.taskState.value
import com.streamliners.compose.comp.appBar.searchAppBarState
import com.streamliners.data.CountryRepository
import com.streamliners.data.CountryRepository.Country
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchBarSampleViewModel @Inject constructor(
    private val countryRepository: CountryRepository
): BaseViewModel() {

    val searchAppBarState = searchAppBarState()

    private val allCountriesState = loadingTaskStateOf<List<Country>>()
    val filteredCountries = mutableStateOf(emptyList<Country>())

    override fun init() {
        execute {
            allCountriesState.update(
                countryRepository.getCountries()
            )
            filteredCountries.value = allCountriesState.value()
        }
    }

    fun filter(query: String) {
        filteredCountries.value = allCountriesState.value().filter {
            it.name.contains(query, ignoreCase = true)
        }
    }

}