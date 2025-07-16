package eu.gryta.autoduty

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
                        /*
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

                         */
                        AutomationControlView()
                    }
                }
            }
        }
    }

    @Composable
    fun AutomationControlView() {
        val userId = remember { mutableStateOf("") }
        val username = remember { mutableStateOf("") }
        val automationCounter = remember { mutableStateOf(0) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = userId.value,
                onValueChange = { userId.value = it },
                label = { Text("User ID") },
                modifier = Modifier.fillMaxWidth()
            )

            TextField(
                value = username.value,
                onValueChange = { username.value = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        // Logic to change API token
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Change API Token")
                }

                Button(
                    onClick = {
                        // Logic to start the service
                        automationCounter.value += 1
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Start Service")
                }
            }

            Text(
                text = "Automation counter: ${automationCounter.value}",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}