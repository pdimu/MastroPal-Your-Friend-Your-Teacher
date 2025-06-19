package com.example.tutorapp1

import android.graphics.drawable.Icon
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun UserProfile(userEmail: String) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = "Profile Section",
                style = MaterialTheme.typography.subtitle1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "User ID: $userEmail",
                style = MaterialTheme.typography.body1,
                color = Color.Gray
            )
        }
    }
}

@Preview
@Composable
fun ProfileDesign(){
    Surface {
        Column {
            Box {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextWithIcon(
                        iconValue = Icons.Filled.Info,
                        labelValue = "User Details"
                    )
                    TextAfterIcon(
                        NormalText = "User Details"
                    )
                }
            }
        }
    }
}


@Composable
fun TextWithIcon(iconValue: ImageVector, labelValue: String){
    Row(verticalAlignment = Alignment.CenterVertically){
        Icon(
            imageVector = iconValue,
            contentDescription = labelValue,
        )
    }
}

@Composable
fun TextAfterIcon(NormalText: String){
    androidx.compose.material3.Text(
        text = NormalText,
        fontSize = 16.sp,
        color = AppColors.Error,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 8.dp, bottom = 2.dp, top = 8.dp, end = 0.dp)
    )
}