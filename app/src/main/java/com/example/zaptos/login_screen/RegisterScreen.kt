package com.example.zaptos.login_screen

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.zaptos.MainScreenActivity
import com.example.zaptos.models.SplashScreens
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

@Composable
fun RegisterScreen(context: Context, navController: NavHostController) {
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
//            LaunchedEffect(key1 = 1) {
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

            val confirmPasswordTF = remember {
                mutableStateOf("")
            }

            val isPasswordVisible = remember {
                mutableStateOf(false)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BGBlue.copy(alpha = 0.5f)),
            ) {

//                Card(
//                    elevation = 5.dp,
//                    shape = CircleShape,
//                    modifier = Modifier
//                        .shadow(
//                            55.dp,
//                            CircleShape,
//                            ambientColor = DPColor,
//                            spotColor = DPColor
//                        )
//                ) {
//                    Image(
//                        painterResource(id = com.example.zaptos.R.drawable.zaptos),
//                        contentDescription = "",
//                        modifier = Modifier
//                            .height((LocalConfiguration.current.screenWidthDp / 1.5).dp)
//                            .width((LocalConfiguration.current.screenWidthDp / 1.5).dp),
//                    )
//                }
//                Spacer(modifier = Modifier.padding(20.dp))
                Text(
                    text = "Register",
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
                        .height(300.dp)
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

                        OutlinedTextField(
                            value = confirmPasswordTF.value,
                            onValueChange = {
                                confirmPasswordTF.value = it
                            },
                            label = {
                                Text(text = "Confirm Password")
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

                        if (passwordTF.value == confirmPasswordTF.value) {
                            auth.createUserWithEmailAndPassword(emailTF.value, passwordTF.value)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {

//                                        val db = Firebase.firestore
//                                        val currentUser = Firebase.auth.currentUser
//                                        val cartItem = hashMapOf(
//                                            "status" to "down"
//                                        )
//
//                                        db.collection("carts")
//                                            .document("${currentUser?.uid.toString()}_cart")
//                                            .set(cartItem)
//                                            .addOnSuccessListener { documentReference ->
//                                                Log.i(
//                                                    "FirestoreCart",
//                                                    "DocumentSnapshot added with ID: ${documentReference}"
//                                                )
//                                            }
//                                            .addOnFailureListener { e ->
//                                                Log.i("FirestoreCart", "Error adding document", e)
//                                            }


                                        val mainScreenIntent =
                                            Intent(context, MainScreenActivity::class.java)
                                        context.startActivity(mainScreenIntent)
                                        Log.i("SignInInfo", "signInWithEmail:success")
                                    } else {
                                        Log.i(
                                            "SignInInfo",
                                            "signInWithEmail:failure",
                                            task.exception
                                        )
                                        Toast.makeText(
                                            context, "Unable to SignIn",
                                            Toast.LENGTH_SHORT
                                        ).show()
                                    }
                                }
                        } else {
                            Toast.makeText(
                                context,
                                "Confirm password not equal to password!!!",
                                Toast.LENGTH_LONG
                            ).show()
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
                    Text(text = "SignUp", color = BGBlue, fontSize = 20.sp)
                }


                Text(text = "Login", modifier = Modifier.clickable {
                    navController.navigate(SplashScreens.Login.route)
                })

            }
        }
    }
}