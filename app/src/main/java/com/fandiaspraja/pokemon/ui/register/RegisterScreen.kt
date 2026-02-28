package com.fandiaspraja.pokemon.ui.register

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.fandiaspraja.pokemon.R
import com.fandiaspraja.pokemon.core.data.Resource
import com.fandiaspraja.pokemon.core.domain.model.User


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onRegisterSuccess: () -> Unit,
    onBack: () -> Unit,
    ) {

    val context = LocalContext.current

    val state by viewModel.registerState.collectAsState()

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    var isNameError by remember { mutableStateOf(false) }
    var isEmailError by remember { mutableStateOf(false) }
    var isPasswordError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Register") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )

        }
    ) {
        innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
        ){
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                ){
                    Image(
                        painter = painterResource(id =   R.drawable.logo_pokemon),
                        contentDescription = "App Logo",
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Box(modifier = Modifier
                    .weight(2f)) {
                    Image(
                        painter = painterResource(id =   R.drawable.pokemon_text),
                        contentDescription = "Pokemon Text",
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = name,
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                label = { Text("Name") },
                isError = isNameError,
                onValueChange = {
                    name = it
                    isNameError = false // Clear error when user starts typing
                },
                supportingText = {
                    if (isNameError) {
                        Text(
                            text = "Name cannot be empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isEmailError = false // Clear error when user starts typing
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("Email") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                isError = isEmailError, // Set the error state
                supportingText = {
                    if (isEmailError) {
                        Text(
                            text = "Email cannot be empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            )
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    isPasswordError = false // Clear error when user starts typing
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = isPasswordError, // Set the error state
                supportingText = {
                    if (isPasswordError) {
                        Text(
                            text = "Password cannot be empty",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                trailingIcon = {
                    val image = if (passwordVisible)
                        Icons.Filled.Visibility
                    else
                        Icons.Filled.VisibilityOff
                    val description = if (passwordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {passwordVisible = !passwordVisible}){
                        Icon(imageVector  = image, description)
                    }
                }
            )
            Spacer(Modifier.height(16.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    if (name.isEmpty()){
                        Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                    } else if (email.isEmpty()){
                        Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                    } else if (password.isEmpty()){
                        Toast.makeText(context, "Password cannot be empty", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.register(User(0, name, email, password))
                    }
            }) {
                Text("Register")
            }

            Spacer(Modifier.height(16.dp))

            Row (
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                Text("Already have an account ? ")
                TextButton(
                    contentPadding = PaddingValues(0.dp),
                    onClick = onBack) {
                    Text("Login")
                }
            }

            when (state) {
                is Resource.Loading -> CircularProgressIndicator()
                is Resource.Success -> onRegisterSuccess()
                is Resource.Error -> (state as Resource.Error).message?.let { Text(it) }
                else -> {}
            }
        }
    }
}