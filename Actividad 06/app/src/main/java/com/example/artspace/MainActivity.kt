package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.artspace.ui.theme.ArtSpaceTheme
import com.example.artspace.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArtSpaceTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ArtSpaceApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}


@Composable
fun ArtSpaceApp(modifier: Modifier = Modifier) {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
    val artworks = listOf(
        Pair(R.drawable.starry_night, Triple(R.string.art_title_1, R.string.art_artist_1, R.string.art_year_1)),
        Pair(R.drawable.the_scream, Triple(R.string.art_title_2, R.string.art_artist_2, R.string.art_year_2)),
        Pair(R.drawable.persistence_of_memory, Triple(R.string.art_title_3, R.string.art_artist_3, R.string.art_year_3))
    )
    val total = artworks.size
    val (current, setCurrent) = remember { mutableStateOf(0) }
    val (imgRes, titleArtistYear) = artworks[current]
    val (titleRes, artistRes, yearRes) = titleArtistYear

    val isLight = !isSystemInDarkTheme()
    val backgroundColor = if (isLight) Color(0xFFF8F8F8) else Color(0xFF181818)
    val cardColor = if (isLight) Color.White else Color(0xFF232323)
    val borderColor = if (isLight) Color(0xFF222222) else Color(0xFFEEEEEE)
    val textColor = if (isLight) Color(0xFF181818) else Color(0xFFF8F8F8)
    val buttonColor = if (isLight) Color(0xFF222222) else Color(0xFFF8F8F8)
    val buttonTextColor = if (isLight) Color.White else Color(0xFF181818)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            backgroundColor,
            cardColor,
            backgroundColor
        )
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ArtImage(
                    imgRes = imgRes,
                    titleRes = titleRes,
                    cardColor = cardColor,
                    borderColor = borderColor,
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight()
                )
                Spacer(modifier = Modifier.width(24.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ArtInfoCard(
                        titleRes = titleRes,
                        artistRes = artistRes,
                        yearRes = yearRes,
                        cardColor = cardColor,
                        borderColor = borderColor,
                        textColor = textColor
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    ArtControls(
                        onPrevious = { setCurrent(if (current == 0) total - 1 else current - 1) },
                        onNext = { setCurrent((current + 1) % total) },
                        buttonColor = buttonColor,
                        buttonTextColor = buttonTextColor
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ArtImage(
                    imgRes = imgRes,
                    titleRes = titleRes,
                    cardColor = cardColor,
                    borderColor = borderColor,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ArtInfoCard(
                    titleRes = titleRes,
                    artistRes = artistRes,
                    yearRes = yearRes,
                    cardColor = cardColor,
                    borderColor = borderColor,
                    textColor = textColor
                )
                Spacer(modifier = Modifier.height(12.dp))
                ArtControls(
                    onPrevious = { setCurrent(if (current == 0) total - 1 else current - 1) },
                    onNext = { setCurrent((current + 1) % total) },
                    buttonColor = buttonColor,
                    buttonTextColor = buttonTextColor
                )
            }
        }
    }
}

@Composable
fun ArtImage(
    imgRes: Int,
    titleRes: Int,
    cardColor: Color,
    borderColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .shadow(24.dp, RoundedCornerShape(24.dp))
            .clip(RoundedCornerShape(24.dp))
            .background(cardColor)
    ) {
        Image(
            painter = painterResource(id = imgRes),
            contentDescription = stringResource(id = titleRes),
            contentScale = androidx.compose.ui.layout.ContentScale.Fit,
            modifier = Modifier
                .fillMaxSize()
        )
    }
}

@Composable
fun ArtInfoCard(
    titleRes: Int,
    artistRes: Int,
    yearRes: Int,
    cardColor: Color,
    borderColor: Color,
    textColor: Color
) {
    Surface(
        tonalElevation = 2.dp,
        shadowElevation = 2.dp,
        shape = RoundedCornerShape(20.dp),
        color = cardColor,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = titleRes),
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                color = textColor
            )
            Divider(
                color = borderColor.copy(alpha = 0.2f),
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(id = artistRes),
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
                color = textColor,
                modifier = Modifier.padding(top = 2.dp)
            )
            Text(
                text = stringResource(id = yearRes),
                style = MaterialTheme.typography.bodyMedium,
                color = textColor,
                modifier = Modifier.padding(top = 2.dp)
            )
        }
    }
}

@Composable
fun ArtControls(
    onPrevious: () -> Unit,
    onNext: () -> Unit,
    buttonColor: Color,
    buttonTextColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Button(
            onClick = onPrevious,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = buttonTextColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Anterior", fontWeight = FontWeight.SemiBold)
        }
        Spacer(modifier = Modifier.width(24.dp))
        Button(
            onClick = onNext,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(
                containerColor = buttonColor,
                contentColor = buttonTextColor
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Siguiente", fontWeight = FontWeight.SemiBold)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceAppPreview() {
    ArtSpaceTheme {
        ArtSpaceApp()
    }
}