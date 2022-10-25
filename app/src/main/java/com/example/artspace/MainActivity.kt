package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ArtSpaceApp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ArtSpaceApp() {

    var index by remember { mutableStateOf(0) }
    val artwork = DataSource.data[index]

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize()
    ) {
        Surface(
            modifier = Modifier
                .align(alignment = CenterHorizontally)
                .padding(16.dp)
                .weight(2f),
            elevation = 10.dp,
            color = MaterialTheme.colors.background,
            border = BorderStroke(2.dp, Color.DarkGray),
        ) {
            ArtworkWall(artwork.image)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            color = Color.White,
            elevation = 2.dp,
            modifier = Modifier
                .weight(0.50f)
                .fillMaxWidth()
                .padding(horizontal = 24.dp)

        ) {
            ArtworkDescriptor(
                artWorkTitle = artwork.title,
                artist = artwork.artist,
                year = artwork.year
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            modifier = Modifier
                .weight(0.50f)
                .padding(horizontal = 24.dp)
        ) {
            DisplayController(
                onNext = { index = (index + 1) % DataSource.data.size },
                onPrev = { index = if (index == 0) DataSource.data.size - 1 else index - 1 })
        }
    }
}

@Composable
fun ArtworkWall(@DrawableRes image: Int, modifier: Modifier = Modifier) {
    val painter = painterResource(id = image)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .fillMaxWidth(0.75f)
            .fillMaxHeight(0.50f)
            .padding(32.dp),
        contentScale = ContentScale.Fit
    )
}

@Composable
fun ArtworkDescriptor(artWorkTitle: String, artist: String, year: Int) {

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {

        Text(buildAnnotatedString {
            append(artWorkTitle)
            addStyle(
                style = SpanStyle(
                    color = Color.Black,
                    fontWeight = FontWeight.Light,
                    fontSize = 20.sp
                ), start = 0, end = artWorkTitle.length
            )
        })
        
        Spacer(modifier = Modifier.height(16.dp))

        Text(buildAnnotatedString {
            val artistInfo = "$artist ($year)"
            append(artistInfo)
            addStyle(
                style = SpanStyle(
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                ),
                start = 0,
                end = artist.length
            )
            addStyle(
                style = SpanStyle(
                    color = Color.DarkGray,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Light
                ),
                start = artist.length + 1,
                end = artistInfo.length
            )
        })
    }
}

@Composable
fun DisplayController(onNext: () -> Unit, onPrev: () -> Unit) {
    Row(
    ) {
        Button(
            onClick = onPrev,
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp)

        ) {
            Text(text = "Previous")
        }
        Button(
            onClick = onNext,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)

        ) {
            Text(
                text = "Next",
                modifier = Modifier
                    .padding(start = 8.dp)
            )
        }
    }
}