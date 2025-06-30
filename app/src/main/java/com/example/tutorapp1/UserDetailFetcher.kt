package com.example.tutorapp1

import android.widget.Toast
import androidx.benchmark.perfetto.PerfettoConfig
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase

@Composable
fun UserDetailFetcher(
    userId: String,
    content: @Composable (email: String, phone: String) -> Unit
) {
    val context = LocalContext.current
    val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)

    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        databaseRef.get().addOnSuccessListener { snapshot ->
            email = snapshot.child("email").getValue(String::class.java) ?: ""
            phone = snapshot.child("phone").getValue(String::class.java) ?: ""
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load user data", Toast.LENGTH_SHORT).show()
        }
    }

    // Reusable slot content
    content(email, phone)
}

@Composable
fun UserUID(userId: String){
    Text(text = "User UID: $userId")
}

@Composable
fun UserPassword(
    userId: String,
    content: @Composable (password: String) -> Unit
    ){
    var passwordEntered by remember { mutableStateOf("") }
    Text(text = "User Password: $passwordEntered")
}


@Composable
fun UserUID1(userId: String) {
    val context = LocalContext.current
    val databaseRef = FirebaseDatabase.getInstance().getReference("Users").child(userId)

    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    LaunchedEffect(userId) {
        databaseRef.get().addOnSuccessListener { snapshot ->
            email = snapshot.child("email").getValue(String::class.java) ?: ""
            phone = snapshot.child("phone").getValue(String::class.java) ?: ""
        }.addOnFailureListener {
            Toast.makeText(context, "Failed to load user data", Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(text = "UID: $userId")
        Text(text = "Email: $email")
        Text(text = "Phone: $phone")
    }
}
