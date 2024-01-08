package com.zara.rickandmorty.ui.characterDetail

import com.zara.rickandmorty.data.remote.repository.CharacterRepository
import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.utils.Resource
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CharacterDetailViewModelTest {

    @RelaxedMockK
    private lateinit var repository: CharacterRepository

    lateinit var characterDetailViewModel: CharacterDetailViewModel

    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        characterDetailViewModel = CharacterDetailViewModel(repository)
    }

    @Test
    fun `get character detail when id is valid`() = runTest {

        val character = mockk<Character>()

        // Given
        coEvery {
            repository.getCharacterInfo(0)
        } returns Resource.Success(character)

        // When
        val response = characterDetailViewModel.getCharacterInfo(0)

        // Then
        coVerify(exactly = 1) { repository.getCharacterInfo(0) }
        assert(response is Resource.Success)
        assert(response.data == character)
    }

}