package com.example.tutorapp1

import android.R.attr.icon
import android.R.id.icon
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.materialIcon
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

@Composable
fun SignUp(
    onSignUpSuccess: (String) -> Unit,
    onSwitchToLogin: () -> Unit
){
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance()

    val emailEntered = remember { mutableStateOf("") }
    val passwordEntered = remember { mutableStateOf("") }

    Surface (
        modifier = Modifier
            .fillMaxSize()
            .background(AppColors.Background)
           // .border(1.dp, AppColors.Placeholder, RoundedCornerShape(12.dp))
            .padding(16.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .border(1.dp, AppColors.Placeholder, RoundedCornerShape(12.dp))
            //    .background(AppColors.Placeholder)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
           Box(
               modifier = Modifier
                   .padding(16.dp)
                   .background(AppColors.OnError)
                   //.border(1.dp, AppColors.Placeholder, RoundedCornerShape(12.dp))
           ){
               Column (
                   modifier = Modifier
                       .padding(8.dp)
                       //.border(1.dp, AppColors.Placeholder, RoundedCornerShape(12.dp))
               ) {
                   val interactionSource = remember { MutableInteractionSource() }
                   val isFocused by interactionSource.collectIsFocusedAsState()

                   CallNormalTextFieldText(NormalText = "Email")

                   OutlinedTextField(
                       value = emailEntered.value,
                       onValueChange = { emailEntered.value = it },
                       placeholder = {Text(text = "Enter email")},
                       colors = OutlinedTextFieldDefaults.colors(
                           focusedBorderColor = AppColors.TextFieldFocusedBorder,
                           unfocusedBorderColor = AppColors.TextFieldColorsBorder,
                           cursorColor = AppColors.TextFieldCursor,
                           focusedLabelColor = AppColors.TextFieldFocusedBorder,
                           unfocusedLabelColor = Color.Gray,
                           focusedPlaceholderColor = AppColors.Placeholder,
                           unfocusedPlaceholderColor = Color.LightGray
                       ),
                       keyboardOptions = KeyboardOptions.Default,

                       leadingIcon = {
                           Icon(
                               imageVector = if (isFocused) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
                               contentDescription = "labelValue"
                           )
                       },

                       modifier = Modifier
                           .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                           .clip(RoundedCornerShape(4.dp))
                           .fillMaxWidth()
                   )

                   CallNormalTextFieldText(NormalText = "Password")


                   OutlinedTextField(
                       value = passwordEntered.value,
                       onValueChange = { passwordEntered.value = it },
                       placeholder = {Text(text = "Enter Password")},
                       colors = OutlinedTextFieldDefaults.colors(
                           focusedBorderColor = AppColors.TextFieldFocusedBorder,
                           unfocusedBorderColor = AppColors.TextFieldColorsBorder,
                           cursorColor = AppColors.TextFieldCursor,
                           focusedLabelColor = AppColors.TextFieldFocusedBorder,
                           unfocusedLabelColor = Color.Gray,
                           focusedPlaceholderColor = AppColors.Placeholder,
                           unfocusedPlaceholderColor = Color.LightGray
                       ),
                       visualTransformation = PasswordVisualTransformation(),
                       keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

                       leadingIcon = {
                           Icon(
                               imageVector = if (isFocused) Icons.Filled.Lock else Icons.Outlined.Lock,
                               contentDescription = ""
                           )
                       },
                       interactionSource = interactionSource,

                       modifier = Modifier
                           .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
                           .clip(RoundedCornerShape(4.dp))
                           .fillMaxWidth()
                   )

                   Row (
                       horizontalArrangement = Arrangement.SpaceBetween
                   ){
                       Button(
                           onClick = {
                               val email = emailEntered.value.trim()
                               val password = passwordEntered.value.trim()

                               if(email.isNotEmpty() && password.isNotEmpty()) {
                                   auth.createUserWithEmailAndPassword(email, password)
                                       .addOnCompleteListener { task ->
                                           if (task.isSuccessful) {
                                               val uid = auth.currentUser?.uid ?: ""
                                               FirebaseDatabase.getInstance().reference
                                                   .child("Users")
                                                   .child(uid)
                                                   .setValue(mapOf("email" to email))
                                               onSignUpSuccess(email)
                                           } else {
                                               Toast.makeText(context, task.exception?.message ?: "Error occurred", Toast.LENGTH_SHORT).show()
                                           }
                                       }
                               } else {
                                   Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
                               }
                           },
                           modifier = Modifier
                               .weight(1f)
                               .padding(8.dp),
                           shape = RoundedCornerShape(8.dp),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = AppColors.Primary, // Gray background
                               contentColor = AppColors.Background // Text color
                           )
                       ){
                           Text(
                               text = "Sign Up".uppercase(),
                               textAlign = TextAlign.Center,
                               modifier = Modifier
                                   .padding(16.dp, 8.dp)
                                   .fillMaxWidth()
                           )
                       }
                       Button(
                           onClick = {},
                           modifier = Modifier
                               .weight(1f)
                               .padding(8.dp),
                           shape = RoundedCornerShape(8.dp),
                           colors = ButtonDefaults.buttonColors(
                               containerColor = Color.LightGray, // Gray background
                               contentColor = AppColors.Background // Text color
                           )
                       ) {
                           Text(
                               text = "Cancel".uppercase(),
                               textAlign = TextAlign.Center,
                               color = AppColors.White,
                               modifier = Modifier
                                   .padding(16.dp, 8.dp)
                                   .fillMaxWidth()
                           )
                       }
                   }

                   TextButton(onClick = onSwitchToLogin) {
                       Text("Already have an account? Login")
                   }
               }
           }
        }
    }
}

@Composable
fun CallNormalTextFieldText(NormalText: String){
    Text(
        text = NormalText,
        fontSize = 16.sp,
        color = AppColors.Error,
        textAlign = TextAlign.Start,
        modifier = Modifier.padding(start = 8.dp, bottom = 2.dp, top = 8.dp, end = 0.dp)
    )
}

//
//CallNormalTextField(
////iconValue = Icons.Default.AccountBox,
//iconValue = if (isFocused) Icons.Filled.AccountCircle else Icons.Outlined.AccountCircle,
//interactionSource = interactionSource,
//labelValue = stringResource(id = R.string.user_name_input),
//placeHolderValue = stringResource(id = R.string.user_name_input_placeholder),
//)

@Composable
fun CallNormalTextField(
    iconValue: ImageVector,
    labelValue: String,
    placeHolderValue: String,
    interactionSource: MutableInteractionSource
    ){

    val emailEntered = remember { mutableStateOf("") }

    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = emailEntered.value,
        onValueChange = { emailEntered.value = it },
//        label = {Text(text = labelValue)},
        placeholder = {Text(text = placeHolderValue)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColors.TextFieldFocusedBorder,
            unfocusedBorderColor = AppColors.TextFieldColorsBorder,
            cursorColor = AppColors.TextFieldCursor,
            focusedLabelColor = AppColors.TextFieldFocusedBorder,
            unfocusedLabelColor = Color.Gray,
            focusedPlaceholderColor = AppColors.Placeholder,
            unfocusedPlaceholderColor = Color.LightGray
        ),
        keyboardOptions = KeyboardOptions.Default,

        leadingIcon = {
            Icon(
                imageVector = iconValue,
                contentDescription = labelValue
            )
        },

        modifier = Modifier
            .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
    )
}

//
//CallPasswordTextField(
//iconValue = if (isFocused) Icons.Filled.Lock else Icons.Outlined.Lock,
//interactionSource = interactionSource,
//labelValue = stringResource(id = R.string.password_input),
//placeHolderValue = stringResource(id = R.string.password_input_placeholder),
//)

@Composable
fun CallPasswordTextField(labelValue: String, placeHolderValue: String, iconValue: ImageVector, interactionSource: MutableInteractionSource){

    val passwordEntered = remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    OutlinedTextField(
        value = passwordEntered.value,
        onValueChange = { passwordEntered.value = it },
//        label = {Text(text = labelValue)},
        placeholder = {Text(text = placeHolderValue)},
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = AppColors.TextFieldFocusedBorder,
            unfocusedBorderColor = AppColors.TextFieldColorsBorder,
            cursorColor = AppColors.TextFieldCursor,
            focusedLabelColor = AppColors.TextFieldFocusedBorder,
            unfocusedLabelColor = Color.Gray,
            focusedPlaceholderColor = AppColors.Placeholder,
            unfocusedPlaceholderColor = Color.LightGray
        ),
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),

        leadingIcon = {
            Icon(
                imageVector = iconValue,
                contentDescription = ""
            )
        },
        interactionSource = interactionSource,

        modifier = Modifier
            .padding(top = 0.dp, bottom = 8.dp, start = 8.dp, end = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .fillMaxWidth()
    )

}

