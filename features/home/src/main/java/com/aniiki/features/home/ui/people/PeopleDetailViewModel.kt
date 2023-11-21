package com.aniiki.features.home.ui.people

import androidx.lifecycle.ViewModel
import com.aniiki.features.home.repository.PeopleDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
    private val peopleDetailRepository: PeopleDetailRepository
) : ViewModel() {
}