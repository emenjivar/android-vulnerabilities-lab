package com.emenjivar.myapplication

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.room.Room
import com.emenjivar.myapplication.data.AppDatabase
import com.emenjivar.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "lab-database"
        ).build()
        val userDao = db.userDao()

        setContent {
            val coroutineContext = rememberCoroutineScope()

            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        modifier = Modifier.padding(innerPadding),
                        onClick = { userName, password ->
                            coroutineContext.launch(Dispatchers.IO) {
                                Log.wtf("MainActivity", "credentials: $userName, $password")
                                val list = userDao.login(userName, password)
                                if (list.isNotEmpty()) {
                                    // TODO: login successful
                                }
                            }
                        }
                    )
                }
            }
        }

        Log.d("MainActivity", "user password: $password")
        Log.d("MainActivity", "API KEY: $API_KEY")
    }
}

private const val password = "sensitive_password"
private const val API_KEY = "hardcoded_api_key_12345"

@Composable
fun Greeting(
    modifier: Modifier = Modifier,
    onClick: (userName: String, password: String) -> Unit
) {
    val userName = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    Column(modifier = modifier) {
        Text("Login screen")
        TextField(value = userName.value, onValueChange = { userName.value = it })
        TextField(
            value = password.value,
            onValueChange = { password.value = it },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            )
        )
        Button(onClick = {
            onClick(userName.value, password.value)
        }) {
            Text("Authenticate")
        }
    }
}
