package com.zara.rickandmorty.ui.characterDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.accompanist.coil.rememberCoilPainter
import com.zara.rickandmorty.R
import com.zara.rickandmorty.data.remote.responses.Character
import com.zara.rickandmorty.ui.characterList.LoadingPortal
import com.zara.rickandmorty.ui.theme.blue
import com.zara.rickandmorty.utils.Resource
import com.zara.rickandmorty.utils.parseGenderToColor
import com.zara.rickandmorty.utils.parseGenderToIcon
import com.zara.rickandmorty.utils.parseSpeciesToColor
import com.zara.rickandmorty.utils.parseSpeciesToIcon
import com.zara.rickandmorty.utils.parseStatusToColor
import com.zara.rickandmorty.utils.parseStatusToIcon

@Composable
fun CharacterDetailScreen(
    id: Int,
    navController: NavController,
    topPadding: Dp = 60.dp,
    imageSize: Dp = 200.dp,
    viewModel: CharacterDetailViewModel = hiltViewModel()
) {
    val characterInfo =
        produceState<Resource<Character>>(initialValue = Resource.Loading()) {
            value = viewModel.getCharacterInfo(id)
        }.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 16.dp)
    ) {
        CharacterTopSection(
            navController = navController,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.2f)
                .align(Alignment.TopCenter)
        )
        CharacterStateWrapper(
            characterInfo = characterInfo,
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = topPadding + imageSize / 2f,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 24.dp
                )
                .shadow(10.dp, RoundedCornerShape(10.dp))
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(16.dp)
                .align(Alignment.BottomCenter)
        )
        Box(
            contentAlignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxSize()
        ) {
            if (characterInfo is Resource.Success) {
                characterInfo.data?.image?.let { url ->
                    Image(
                        painter = rememberCoilPainter(
                            request = url,
                            previewPlaceholder = R.drawable.not_found
                        ),
                        contentDescription = characterInfo.data.name,
                        modifier = Modifier
                            .size(imageSize)
                            .offset(y = topPadding)
                            .clip(CircleShape)
                    )
                }
            }
        }
    }
}

@Composable
fun CharacterTopSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = modifier
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.onBackground,
                        Color.Transparent
                    )
                )
            )
    ) {
        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(36.dp)
                .offset(16.dp, 16.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun CharacterStateWrapper(
    characterInfo: Resource<Character>,
    modifier: Modifier = Modifier
) {
    when (characterInfo) {
        is Resource.Success -> {
            CharacterDetailSection(
                characterInfo = characterInfo.data!!,
                modifier = modifier
                    .offset(y = (-20).dp)
            )
        }

        is Resource.Error -> {
            ErrorSection(characterInfo.message!!, modifier)
        }

        is Resource.Loading -> {
            LoadingSection(modifier)
        }
    }
}

@Composable
fun CharacterDetailSection(
    characterInfo: Character,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .offset(y = 120.dp)
            .padding(bottom = 100.dp)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = characterInfo.name,
            fontWeight = FontWeight.Bold,
            fontSize = 36.sp,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.size(24.dp))
        CharacterCharacteristics("Gender:", characterInfo.gender)
        CharacterCharacteristics("Status:", characterInfo.status)
        CharacterCharacteristics("Species:", characterInfo.species)
        CharacterCharacteristics("Location:", characterInfo.location.name)
    }
}

@Composable
fun CharacterCharacteristics(
    type: String,
    value: String
) {
    Text(
        text = type,
        fontSize = 24.sp,
        textAlign = TextAlign.Start,
        color = MaterialTheme.colorScheme.tertiary,
        modifier = Modifier
            .fillMaxWidth()
            .offset(x = 12.dp)
    )
    Spacer(modifier = Modifier.size(10.dp))
    CharacterDetailValue(
        value = value,
        bgColor = when (type) {
            "Gender:" -> parseGenderToColor(value)
            "Status:" -> parseStatusToColor(value)
            "Species:" -> parseSpeciesToColor(value)
            else -> MaterialTheme.colorScheme.surface
        },
        icon = when (type) {
            "Gender:" -> parseGenderToIcon(value)
            "Status:" -> parseStatusToIcon(value)
            "Species:" -> parseSpeciesToIcon(value)
            else -> Icons.Default.Home
        }
    )
    Spacer(modifier = Modifier.size(20.dp))
}

@Composable
fun CharacterDetailValue(
    value: String,
    bgColor: Color,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .clip(CircleShape)
            .background(bgColor)
            .padding(4.dp)
    ) {
        Text(
            text = value,
            fontSize = 24.sp,
            color = Color.White,
            modifier = Modifier
                .wrapContentWidth()
                .padding(horizontal = 8.dp)
        )
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .size(42.dp)
                .padding(horizontal = 8.dp)
        )
    }
}

@Composable
fun ErrorSection(error: String, modifier: Modifier) {
    Box(
        modifier = modifier,
    ) {
        Column {
            Image(
                painter = painterResource(id = R.drawable.not_found),
                colorFilter = ColorFilter.tint(
                    color = blue,
                    blendMode = BlendMode.Modulate
                ),
                contentDescription = error,
                modifier = Modifier
                    .size(160.dp)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = error,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = blue,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun LoadingSection(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
    ) {
        Column {
            LoadingPortal(
                modifier = Modifier
                    .offset(y = (-100).dp)
                    .fillMaxWidth()
                    .padding(12.dp)
            )
            Spacer(modifier = Modifier.size(24.dp))
            Text(
                text = "Searching data...",
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = (-100).dp)
            )
        }
    }
}