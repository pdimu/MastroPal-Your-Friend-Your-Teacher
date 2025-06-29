package com.example.tutorapp1

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
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
