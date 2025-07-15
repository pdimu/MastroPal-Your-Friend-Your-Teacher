package com.example.tutorapp1

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.tutorapp1.ui.theme.TutorApp1Theme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        //  TEST DB WRITE
        FirebaseDatabase.getInstance().reference
            .child("connection_test")
            .setValue("Connected! + 4")
            .addOnSuccessListener {
                Log.d("FirebaseCheck", "✅ Firebase connected and write successful")
            }
            .addOnFailureListener {
                Log.e("FirebaseCheck", "❌ Firebase write failed: ${it.message}")
            }

        val auth = FirebaseAuth.getInstance()
        Log.d("AUTH_CHECK", "Current user: ${auth.currentUser?.email}")

//        val dbRef = FirebaseDatabase.getInstance().reference.child("connection_test")
//
//        dbRef.runTransaction(object : Transaction.Handler {
//            override fun doTransaction(currentData: MutableData): Transaction.Result {
//                val current = currentData.getValue(Int::class.java) ?: 0
//                currentData.value = current + 1
//                return Transaction.success(currentData)
//            }
//
//            override fun onComplete(error: DatabaseError?, committed: Boolean, currentData: DataSnapshot?) {
//                if (error != null) {
//                    Log.e("FirebaseCheck", "❌ Transaction failed: ${error.message}")
//                } else {
//                    Log.d("FirebaseCheck", "✅ Connection count updated: ${currentData?.value}")
//                }
//            }
//        })


        enableEdgeToEdge()
        setContent {
            var loggedInUid by remember { mutableStateOf<String?>(null) }
            var showSignUp by remember { mutableStateOf(false) }
            var loggedInEmail by remember { mutableStateOf<String?>(null) }

            TutorApp1Theme {

                when {
                    loggedInUid != null -> {
                        HomeScreen(
                            userId = loggedInUid!!,
                            userEmail = loggedInEmail!!,
                            onLogout = {
                                loggedInUid = null
                                showSignUp = false
                            }
                        )

//                        NotesList(
//                            userName = loggedInUid!!,
//                            onLogout = { loggedInUid = null }
//                        )
                    }
                    showSignUp -> {
                        SignUp(
                            onSignUpSuccess = { uid ->
                                val user = FirebaseAuth.getInstance().currentUser
                                loggedInUid = uid
                                loggedInEmail = user?.email
                            },
                            onSwitchToLogin = { showSignUp = false }
                        )
                    }
                    else -> {
                        LoginScreen(
                            onLoginSuccess = { uid ->
                                val user = FirebaseAuth.getInstance().currentUser
                                loggedInUid = uid
                                loggedInEmail = user?.email
                            },
                            onSwitchToSignUp = { showSignUp = true }
                        )
                    }

//                    showSignUp -> {
//                        SignUp(
//                            onSignUpSuccess = { uid -> loggedInUid = uid },
//                            onSwitchToLogin = { showSignUp = false }
//
//                        )
//                    }
//
//                    else -> {
//                        LoginScreen(
//                            onLoginSuccess = { uid -> loggedInUid = uid },
//                            onSwitchToSignUp = { showSignUp = true }
//                        )
//                    }
                }

                // HomeScreen()

//                Scaffold(
//                    topBar = {
//                        TopAppBar(
//                            title = { Text("Tutor App") },
//                            colors = TopAppBarDefaults.topAppBarColors(
//                                containerColor = AppColors.Surface
//                            )
//                        )
//                    }
//                ) { innerPadding ->
//                    Box(modifier = Modifier.padding(innerPadding)) {
//
//                    }
//                }
            }
        }

    }
}
