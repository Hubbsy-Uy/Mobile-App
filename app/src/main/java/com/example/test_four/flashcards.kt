package com.example.test_four

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.test_four.theme.Game2Theme

class flashcards : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Game2Theme {
                AlphabetScreen()
            }
        }
    }
}

@Composable
fun AlphabetScreen() {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background image covering the entire screen
        Image(
            painter = painterResource(id = R.drawable.bg1), // bg.png in drawable folder
            contentDescription = "Background",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.FillBounds // Ensures the image covers the entire screen
        )
        3
        // Use LazyColumn for scrollable content, which will be displayed on top of the background
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp), // Padding around the content
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            val flashcards = listOf(
                FlashcardData(R.drawable.tatang, "TATANG", "Father"),
                FlashcardData(R.drawable.inang, "NANANG", "Mother"),
                FlashcardData(R.drawable.ubing, "UBING", "Child"),
                FlashcardData(R.drawable.lolo, "LOLO", "Grandfather"),
                FlashcardData(R.drawable.lola, "LOLA", "Grandmother"),
                FlashcardData(R.drawable.pusa, "PUSA", "Cat"),
            )

            items(flashcards.size) { index ->
                // Pass each flashcard data into the Flashcard composable
                Flashcard(
                    imageResId = flashcards[index].imageResId,
                    mainText = flashcards[index].mainText,
                    descriptionText = flashcards[index].descriptionText
                )
            }
        }
    }
}@Composable
fun Flashcard(imageResId: Int, mainText: String, descriptionText: String) {
    var isFlipped by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(8.dp)
            .clickable { isFlipped = !isFlipped }
            .border(
                width = 2.dp, // Adjust the border thickness
                color = Color(0xFF273617), // Border color (273617)
                shape = RoundedCornerShape(16.dp) // Rounded corners for the border
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White // Set the background to white (FFFFFF)
        )
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (isFlipped) {
                // Show main text and description when flipped
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = mainText,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        ),
                        color = Color.Black
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Space between the two texts
                    Text(
                        text = descriptionText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 18.sp
                        ),
                        color = Color.Gray
                    )
                }
            } else {
                // Show image when not flipped
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "Flashcard Image",
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
// Data class to hold the flashcard information
data class FlashcardData(
    val imageResId: Int,
    val mainText: String,
    val descriptionText: String
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Game2Theme {
        AlphabetScreen()
    }
}