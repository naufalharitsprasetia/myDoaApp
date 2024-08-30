package com.example.mydoa

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun MyDoaApp(noteViewModel: NoteViewModel) {
    val notes by noteViewModel.notes.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    var isEditing by remember { mutableStateOf(false) }
    var isAdding by remember { mutableStateOf(false) }
    var selectedNote by remember { mutableStateOf<Note?>(null) }
    var newTitle by remember { mutableStateOf("") }
    var newContent by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        notes.forEach { note ->
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        selectedNote = note
                        newTitle = note.title
                        newContent = note.content
                        isEditing = true
                    }
                    .padding(8.dp)
                    .padding(bottom = 16.dp) // Space between items
            ) {
                Text(text = note.title, fontSize = 18.sp, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = note.content, fontSize = 14.sp, style = MaterialTheme.typography.bodyMedium)

                Row(
                    horizontalArrangement = Arrangement.End,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            noteViewModel.deleteNoteById(note.id)
                        }
                    }) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { isAdding = true }) {
            Text("Tambahkan")
        }

        if (isAdding) {
            NoteAddDialog(
                onDismiss = { isAdding = false },
                onSave = { title, content ->
                    coroutineScope.launch {
                        noteViewModel.insertNote(Note(title = title, content = content))
                        isAdding = false
                    }
                }
            )
        }

        if (isEditing) {
            NoteEditDialog(
                note = selectedNote!!,
                onDismiss = { isEditing = false },
                onSave = { title, content ->
                    coroutineScope.launch {
                        noteViewModel.updateNote(selectedNote!!.id, title, content)
                        isEditing = false
                    }
                }
            )
        }
    }
}

@Composable
fun NoteAddDialog(onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Catat Doa Baru") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Judul") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Isi/Doa") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(title, content)
            }) {
                Text("Simpan")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Batalkan")
            }
        }
    )
}

@Composable
fun NoteEditDialog(note: Note, onDismiss: () -> Unit, onSave: (String, String) -> Unit) {
    var title by remember { mutableStateOf(note.title) }
    var content by remember { mutableStateOf(note.content) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Edit Note") },
        text = {
            Column {
                TextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Title") }
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = content,
                    onValueChange = { content = it },
                    label = { Text("Content") }
                )
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(title, content)
            }) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
