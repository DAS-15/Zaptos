package com.example.zaptos.login_screen

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zaptos.MainScreenActivity
import com.example.zaptos.ui.theme.BGBlue
import com.example.zaptos.ui.theme.DPColor
import com.example.zaptos.ui.theme.ZaptosTheme

@Composable
fun LoginScreen(context: Context) {
    ZaptosTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .background(BGBlue.copy(alpha = 0.5f)),
            ) {

                Card(
                    elevation = 5.dp,
                    shape = CircleShape,
                ) {
                    Image(
                        painterResource(id = com.example.zaptos.R.drawable.zaptos),
                        contentDescription = "",
                        modifier = Modifier
                            .height((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                            .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
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

                        val emailTF = remember {
                            mutableStateOf("")
                        }

                        val passwordTF = remember {
                            mutableStateOf("")
                        }

                        val isPasswordVisible = remember {
                            mutableStateOf(false)
                        }


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
                        val mainScreenIntent = Intent(context, MainScreenActivity::class.java)
                        context.startActivity(mainScreenIntent)
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

            }
        }
    }
}