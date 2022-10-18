package com.example.jetreadingapp.screens.login

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.jetreadingapp.components.EmailInput
import com.example.jetreadingapp.components.PasswordInput
import com.example.jetreadingapp.components.ReaderLogo
import com.example.jetreadingapp.navigation.ReaderScreens


@ExperimentalComposeUiApi
@Composable
fun ReaderLoginScreen(
    navController: NavController,
    viewModel: LoginScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
){
    val showLoginForm = rememberSaveable {
        mutableStateOf(true)

    }
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {
            val headerText = if(showLoginForm.value) "" else "Enter a valid Email and Password to sign up"
            ReaderLogo()
            Text(text = headerText,
                fontWeight = FontWeight.Bold)
            if(showLoginForm.value) UserForm(loading = false, isCreateAccount = false){
                email, password ->
                viewModel.signInWithEmailPassword(email, password){
                    navController.navigate(ReaderScreens.ReaderHomeScreen.name)
                }
            }
            else{
                UserForm(loading = false, isCreateAccount = true){email,password ->
                    viewModel.createUserWithEmailAndPassword(email,password){
                        navController.navigate(ReaderScreens.ReaderLoginScreen.name)
                    }
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(modifier = Modifier.padding(15.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
                val buttonText = if(showLoginForm.value) "Sign up" else "Login"
                val newUserText = if(showLoginForm.value) "New User?" else "Already have an account?"
                Text(text = newUserText)
                Text(text = buttonText,
                modifier = Modifier
                    .clickable {
                        showLoginForm.value = !showLoginForm.value
                    }
                    .padding(start = 5.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondaryVariant)

            }

        }
        
    }

}

@ExperimentalComposeUiApi
@Preview
@Composable
fun UserForm(
    loading: Boolean = false,
    isCreateAccount: Boolean = false,
    onDone: (String, String) -> Unit = {email, pwd ->}
) {
    val email = rememberSaveable { mutableStateOf("") }
    val password = rememberSaveable { mutableStateOf("") }
    val passwordVisibility = rememberSaveable { mutableStateOf(false) }
    val passwordFocusRequest = FocusRequester.Default
    val keyboardController = LocalSoftwareKeyboardController.current
    val valid = remember(email.value, password.value) {
        email.value.trim().isNotEmpty() && password.value.trim().isNotEmpty()
    }

    val modifier = Modifier
        .height(250.dp)
        .background(MaterialTheme.colors.background)
        .verticalScroll(rememberScrollState())

    Column(modifier,
        horizontalAlignment = Alignment.CenterHorizontally) {
        EmailInput(emailState = email,
        enabled = !loading,
        onAction = KeyboardActions{
            passwordFocusRequest.requestFocus()
        })
        PasswordInput(
            modifier = Modifier.focusRequester(passwordFocusRequest),
            passwordState = password,
            enabled= !loading,
            labelId = "password",
            passwordVisibility = passwordVisibility,
            onAction = KeyboardActions{
                if(!valid) return@KeyboardActions
                onDone(email.value.trim(), password.value.trim())
            }
        )
        SubmitButton(
            textId = if (isCreateAccount) "Create Account" else "Login",
            loading = loading,
            validInputs = valid
        ){
            onDone(email.value.trim(), password.value.trim())
            keyboardController?.hide()
        }


    }
}

@Composable
fun SubmitButton(textId: String,
                 loading: Boolean,
                 validInputs: Boolean,
                 onClick: () -> Unit,) {
    Button(
        onClick = onClick,
    modifier = Modifier
        .padding(3.dp)
        .fillMaxWidth(),
    enabled = !loading && validInputs,
    shape = CircleShape) {
        if(loading) CircularProgressIndicator(modifier = Modifier.size(25.dp))
        else Text(text = textId, modifier = Modifier.padding(5.dp))

    }

}

