package com.example.zaptos.login_screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.docode.Repository.MainRepository
import com.example.zaptos.MainScreenActivity
import com.example.zaptos.MasterViewModel
import com.example.zaptos.models.MainScreens
import com.example.zaptos.models.SplashScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Job

@Composable
fun LoginScreen(context: Context, navController: NavHostController) {

    ZaptosTheme {
        val scrollState = rememberScrollState()

        Surface(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

//            var userRepository: MainRepository
//            var vm: MasterViewModel? = null
//
//            LaunchedEffect(key1 = 1){
//                userRepository = MainRepository()
//                val job = Job()
//                vm = MasterViewModel(job)
//            }
//
//            val currentUser = vm?.currentUser
//            val auth = vm?.auth

            val auth = Firebase.auth
            val currentUser = auth.currentUser

            if (currentUser != null) {
                val mainScreenIntent = Intent(context, MainScreenActivity::class.java)
                context.startActivity(mainScreenIntent)
            }


            val emailTF = remember {
                mutableStateOf("")
            }

            val passwordTF = remember {
                mutableStateOf("")
            }

            val isPasswordVisible = remember {
                mutableStateOf(false)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BGBlue.copy(alpha = 0.5f)),
            ) {

                Text(
                    text = "Login",
                    modifier = Modifier
                        .padding(bottom = 20.dp)
                        .align(Alignment.CenterHorizontally),
                    fontSize = 30.sp,
                    color = MaterialTheme.colors.primary,
                    fontWeight = FontWeight.Bold
                )

                Card(
                    modifier = Modifier
                        .padding(10.dp)
                        .height(200.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = BGBlue
                ) {

                    Column {
                        OutlinedTextField(
                            value = emailTF.value,
                            onValueChange = {
                                emailTF.value = it
                            },
                            label = {
                                Text(text = "Email")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Email,
                                    contentDescription = "Email Icon"
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 10.dp, top = 20.dp),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            )
                        )
                        OutlinedTextField(
                            value = passwordTF.value,
                            onValueChange = {
                                passwordTF.value = it
                            },
                            label = {
                                Text(text = "Password")
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Password,
                                    contentDescription = "Password Icon"
                                )
                            },
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(bottom = 20.dp, top = 10.dp),
                            trailingIcon = {
                                if (isPasswordVisible.value) {
                                    Icon(
                                        imageVector = Icons.Filled.VisibilityOff,
                                        contentDescription = "Visibility off Icon",
                                        modifier = Modifier.clickable {
                                            isPasswordVisible.value =
                                                !isPasswordVisible.value
                                        }
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Filled.Visibility,
                                        contentDescription = "Visibility Icon",
                                        modifier = Modifier.clickable {
                                            isPasswordVisible.value =
                                                !isPasswordVisible.value
                                        }
                                    )
                                }
                            },
                            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            )
                        )
                    }
                }

                Button(
                    onClick = {

                        auth.signInWithEmailAndPassword(emailTF.value, passwordTF.value)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    val mainScreenIntent =
                                        Intent(context, MainScreenActivity::class.java)
                                    context.startActivity(mainScreenIntent)
                                    Log.i("SignInInfo", "signInWithEmail:success")
                                } else {
                                    Log.i("SignInInfo", "signInWithEmail:failure", task.exception)
                                    Toast.makeText(
                                        context, "Unable to SignIn",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

//                        val mainScreenIntent = Intent(context, MainScreenActivity::class.java)
//                        context.startActivity(mainScreenIntent)
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    colors = ButtonDefaults.buttonColors(
                        DPColor
                    ),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text(text = "SignIn", color = BGBlue, fontSize = 20.sp)
                }

                Text(text = "Register", modifier = Modifier.clickable {
                    navController.navigate(SplashScreens.Register.route)
                })
            }
        }
    }
}

@Preview
@Composable
fun LoginPrev() {
    LoginScreen(context = LocalContext.current, navController = rememberNavController())
}