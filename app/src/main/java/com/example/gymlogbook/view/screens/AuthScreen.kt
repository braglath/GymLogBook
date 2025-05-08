package com.example.gymlogbook.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymlogbook.R
import com.example.gymlogbook.ui.theme.DarkGray
import com.example.gymlogbook.view.common.CheckSignedIn
import com.example.gymlogbook.view.common.CustomButton
import com.example.gymlogbook.view.common.CustomTextField
import com.example.gymlogbook.viewmodel.AuthViewModel


@Composable
fun AuthScreen(navController: NavController, vm: AuthViewModel) {

    // navigate to home screen when logged in
    CheckSignedIn(navController, vm)

    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 8.dp)
            .verticalScroll(
                rememberScrollState()
            )
            .imePadding(),
//            .clickable(enabled = true) { focusManager.clearFocus() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {

        val emailState = remember { mutableStateOf(TextFieldValue()) }
        val pwdState = remember { mutableStateOf(TextFieldValue()) }

        val emailText = stringResource(id = R.string.email)
        val passwordText = stringResource(id = R.string.password)

        val onLoginClick = {
            focusManager.clearFocus()
            vm.loginBtnClick(emailState.value.text, pwdState.value.text)
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(id = R.drawable.glb_logo_name), // your PNG
                contentDescription = "Logo PNG",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(200.dp)
            )
            Text(
                "Log your progress",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
        }


        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            Text(
                "Enter your credentials",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            Spacer(modifier = Modifier.height(50.dp))

            CustomTextField(
                inputValue = emailState.value,
                onValueChanged = { emailState.value = it },
                label = emailText,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next //  auto focus to next field
                )
            )

            CustomTextField(
                inputValue = pwdState.value,
                onValueChanged = { pwdState.value = it },
                label = passwordText,
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.None,
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Go
                ),
                keyboardActions = KeyboardActions {
                    // on click button action
                    onLoginClick.invoke()
                }
            )

            Spacer(modifier = Modifier.height(50.dp))

            CustomButton(stringResource(R.string.letMeIn)) {
                onLoginClick.invoke()
            }
        }

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            "New User?, we create your profile on the go.\nPlease enter your details and click the button",
            style = MaterialTheme.typography.labelSmall.copy(color = DarkGray),
            modifier = Modifier
                .padding(horizontal = 32.dp),
            textAlign = TextAlign.Center
        )
    }
}