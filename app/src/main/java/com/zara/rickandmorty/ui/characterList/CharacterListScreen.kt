package com.zara.rickandmorty.ui.characterList

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.zara.rickandmorty.R
import com.zara.rickandmorty.data.models.CharacterItem
import com.zara.rickandmorty.ui.theme.blue
import com.zara.rickandmorty.ui.theme.gray

@Composable
fun CharacterListScreen(
    navController: NavController, viewModel: CharacterListViewModel = hiltViewModel()
) {
    Surface(
        color = Color.Black, modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Spacer(modifier = Modifier.height(12.dp))
            Image(
                painter = painterResource(id = R.drawable.name_app),
                contentDescription = "Rick and Morty",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(0.dp)
                    .clip(RoundedCornerShape(30.dp, 30.dp, 0.dp, 0.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Column {
                    SearchBar(
                        hint = "Search character...",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        viewModel.searchCharacter(it)
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    CharacterListView(navController = navController)
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier, hint: String = "", onSearch: (String) -> Unit = {}
) {
    var text by remember {
        mutableStateOf("")
    }
    var isHintDisplayed by remember {
        mutableStateOf(hint != "")
    }
    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        BasicTextField(value = text,
            onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true,
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(MaterialTheme.colorScheme.surface, CircleShape)
                .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHintDisplayed = !it.isFocused && text.isEmpty()
                })
        if (isHintDisplayed) {
            Text(
                text = hint,
                color = MaterialTheme.colorScheme.tertiary,
                fontSize = 20.sp,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
            focusManager.clearFocus()
        }
        Icon(imageVector = Icons.Default.Close,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .size(24.dp)
                .offset(x = (-12).dp)
                .clickable {
                    text = ""
                    onSearch("")
                    focusManager.clearFocus()
                })
    }
}

@Composable
fun CharacterListView(
    navController: NavController, viewModel: CharacterListViewModel = hiltViewModel()
) {
    val characterList by remember {
        viewModel.characterList
    }
    val endReached by remember {
        viewModel.endReached
    }
    val loadError by remember {
        viewModel.loadError
    }
    val isLoading by remember {
        viewModel.isLoading
    }
    val isSearching by remember {
        viewModel.isSearching
    }

    LazyColumn(contentPadding = PaddingValues(16.dp)) {
        val itemCount = if (characterList.size % 2 == 0) {
            characterList.size / 2
        } else {
            characterList.size / 2 + 1
        }
        items(itemCount) {
            if (it >= itemCount - 1 && !endReached && !isLoading && !isSearching) {
                viewModel.loadCharacterPaginated()
            }
            CharacterRow(rowIndex = it, entries = characterList, navController = navController)
        }
    }

    Box(
        contentAlignment = Center, modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            LoadingSection()
        }
        if (loadError.isNotEmpty() && !isLoading) {
            RetrySection(error = loadError) {
                viewModel.loadCharacterPaginated()
            }
        }
        if (characterList.isEmpty() && loadError.isEmpty() && !isLoading) {
            NotFoundSection()
        }
    }
}

@Composable
fun CharacterRow(
    rowIndex: Int, entries: List<CharacterItem>, navController: NavController
) {
    Column {
        Row {
            CharacterEntry(
                item = entries[rowIndex * 2],
                navController = navController,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(16.dp))
            if (entries.size >= rowIndex * 2 + 2) {
                CharacterEntry(
                    item = entries[rowIndex * 2 + 1],
                    navController = navController,
                    modifier = Modifier.weight(1f)
                )
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun CharacterEntry(
    item: CharacterItem, navController: NavController, modifier: Modifier = Modifier
) {
    Box(contentAlignment = Center,
        modifier = modifier
            .shadow(5.dp, RoundedCornerShape(10.dp))
            .clip(RoundedCornerShape(10.dp))
            .aspectRatio(1f)
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground, MaterialTheme.colorScheme.surface
                    )
                )
            )
            .clickable {
                navController.navigate(
                    "character_detail_screen/${item.id}"
                )
            }) {
        Column {
            Image(
                painter = rememberCoilPainter(
                    request = item.imageUrl, previewPlaceholder = R.drawable.not_found
                ),
                contentDescription = item.name,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .shadow(24.dp, CircleShape)
                    .align(CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LoadingSection() {
    Column {
        LoadingPortal(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = "Collecting specimens...",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun RetrySection(
    error: String, onRetry: () -> Unit
) {
    Column {
        Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = error,
            colorFilter = ColorFilter.tint(
                color = blue, blendMode = BlendMode.Modulate
            ),
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error, fontSize = 24.sp, color = blue,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onRetry() }, modifier = Modifier.align(CenterHorizontally)
        ) {
            Text(text = "Retry", fontSize = 18.sp, color = Color.White)
        }
    }
}

@Composable
fun NotFoundSection() {
    Column {
        Image(
            painter = painterResource(id = R.drawable.not_found),
            contentDescription = "Not found",
            modifier = Modifier.size(160.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Character\nNot found",
            fontSize = 24.sp,
            color = gray,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(CenterHorizontally)
        )
    }
}

@Composable
fun LoadingPortal(
    modifier: Modifier = Modifier
) {

    var currentRotation by remember { mutableFloatStateOf(0f) }
    val rotation = remember { Animatable(currentRotation) }

    LaunchedEffect(true) {
        rotation.animateTo(
            targetValue = 360f, animationSpec = infiniteRepeatable(
                animation = tween(3000, easing = LinearEasing), repeatMode = RepeatMode.Restart
            )
        ) {
            currentRotation = value
        }
    }

    Box(
        contentAlignment = Center, modifier = modifier
    ) {
        Image(
            painter = painterResource(id = R.drawable.loading),
            contentDescription = "Loading",
            modifier = Modifier
                .size(160.dp)
                .rotate(rotation.value)
        )
    }
}