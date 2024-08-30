package com.example.mydoa

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Room
import com.example.mydoa.ui.theme.MyDoaTheme

class MainActivity : ComponentActivity() {
    private lateinit var noteDatabase: NoteDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi NoteDatabase
        noteDatabase = Room.databaseBuilder(
            applicationContext,
            NoteDatabase::class.java,
            "note_database"
        ).build()

        setContent {
            MyDoaTheme {
                // A surface container using the background color from the theme
                Surface(color = MaterialTheme.colorScheme.background) {
                    var isWelcomeScreen by remember { mutableStateOf(true) }

                    if (isWelcomeScreen) {
                        WelcomeScreen {
                            isWelcomeScreen = false
                        }
                    } else {
                        MyDoaApp(NoteViewModel(noteDatabase.noteDao()))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyDoaTheme {
        WelcomeScreen {}
    }
}
