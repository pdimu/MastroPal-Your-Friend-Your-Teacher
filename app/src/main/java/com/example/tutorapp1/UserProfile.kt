package com.example.tutorapp1

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun UserProfile(){

    Surface {
        Column {
            Text(
                "Profile Section"
            )
        }
    }
}