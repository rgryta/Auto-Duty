package eu.gryta.autoduty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import eu.gryta.autoduty.pagerduty.PDClient
import eu.gryta.autoduty.theme.AppTheme
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            val currentUser: MutableState<String> = remember { mutableStateOf("None") }
            val apiResponse: MutableState<Int> = remember { mutableIntStateOf(0) }
            AppTheme {
                Scaffold { paddingValues ->
                    Box(
                        modifier = Modifier
                            .padding(paddingValues)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        Column {
                            Text("User ID: ${currentUser.value}")
                            Text("Last API Response: ${apiResponse.value}")
                        }
                        Button(
                            onClick = {
                                val endpoint = PDClient.Users.Me
                                scope.launch {
                                    val response = endpoint.get()
                                    apiResponse.value = response.status.value
                                    if (response.status.isSuccess()) {
                                        currentUser.value = response.body().id
                                    }
                                }
                            }
                        ) { Text("Start") }
                    }
                }
            }
        }
    }
}