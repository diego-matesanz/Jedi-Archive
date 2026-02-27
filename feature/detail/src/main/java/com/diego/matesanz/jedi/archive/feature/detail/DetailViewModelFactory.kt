package com.diego.matesanz.jedi.archive.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.diego.matesanz.jedi.archive.core.domain.usecase.GetEntityDetailUseCase

/**
 * Factory para crear instancias de DetailViewModel
 */
class DetailViewModelFactory(
    private val getEntityDetailUseCase: GetEntityDetailUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(getEntityDetailUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
