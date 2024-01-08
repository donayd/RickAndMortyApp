package com.zara.rickandmorty.ui.characterList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.zara.rickandmorty.data.models.CharacterItem
import com.zara.rickandmorty.data.remote.repository.CharacterRepository
import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.data.remote.responses.CharacterList
import com.zara.rickandmorty.data.remote.responses.Info
import com.zara.rickandmorty.utils.Resource
import io.mockk.MockK
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class CharacterListViewModelTest {

    @RelaxedMockK
    private lateinit var repository: CharacterRepository

    private lateinit var characterListViewModel: CharacterListViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
        characterListViewModel = CharacterListViewModel(repository)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `search character when query is empty`() = runTest {

        val info = Info(0,null,0,0)
        val characterList = CharacterList(info, emptyList())

        characterListViewModel.characterList.value = emptyList()

        // Given
        coEvery {
            repository.getCharacterList(0)
        } returns Resource.Success(characterList)

        // When
        characterListViewModel.searchCharacter("")

        // Then
        assert(characterListViewModel.characterList.value == emptyList<CharacterItem>())
    }

    @Test
    fun `search character when query is Rick`() = runTest {

        val info = Info(0,null,0,0)
        val character = mockk<Character>(relaxed = true)
        val characterList = CharacterList(info, listOf(character))
        val characterItem = CharacterItem(1, "Rick", "url")

        characterListViewModel.characterList.value = listOf(characterItem)

        // Given
        coEvery {
            repository.getCharacterList(0)
        } returns Resource.Success(characterList)

        // When
        characterListViewModel.searchCharacter("Rick")

        // Then
        assert(characterListViewModel.characterList.value.first() == characterItem)
    }

}