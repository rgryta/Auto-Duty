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
import eu.gryta.ktor.utils.ApiInstance
import io.ktor.client.call.body
import io.ktor.client.statement.request
import io.ktor.http.HttpHeaders
import io.ktor.client.request.headers
import io.ktor.http.isSuccess
import io.ktor.utils.io.InternalAPI
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

    @OptIn(InternalAPI::class)
    @Composable
    fun AutomationControlView() {
        val apiToken = remember { mutableStateOf("") }
        val userId = remember { mutableStateOf("") }
        val automationCounter = remember { mutableStateOf(0) }
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            TextField(
                value = apiToken.value,
                onValueChange = { apiToken.value = it },
                label = { Text("API Token") },
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = userId.value,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            val me = PDClient.Users.Me.get {
                                headers {
                                    append(HttpHeaders.Authorization,  "Token token=${apiToken.value}")
                                }
                            }
                            if (me.status.isSuccess()){
                                userId.value = me.body().user.id
                            }
                            else {
                                println(me.response.request.headers)
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Change API Token")
                }

                Button(
                    onClick = {
                        automationCounter.value += 1
                        coroutineScope.launch {
                            ApiInstance.getInstance().token = "Token token=${apiToken.value}"
                            val incidents = PDClient.Incidents.get(userId.value)
                            if (incidents.status.isSuccess()){
                                println(incidents.body())
                                println(incidents.response.request.url)
                            } else {
                                println("Failed incidents")
                                println(incidents.status)
                                println(incidents.response.request.url)
                                println(incidents.response.request.headers)
                            }
                        }
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