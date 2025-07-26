package eu.gryta.autoduty.ui

import android.widget.Toast
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import eu.gryta.autoduty.db.RoomInstance
import eu.gryta.autoduty.db.schema.SettingKey
import eu.gryta.autoduty.pagerduty.PDClient
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch


@Composable
fun ServiceStartButton() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            coroutineScope.launch {
                val userId = RoomInstance.instance.dao.getByKey(key = SettingKey.USER_ID)
                if (userId != null) {
                    val incidents = PDClient.Incidents.get(userId.value ?: "")
                    if (incidents.status.isSuccess()) {
                        println(incidents.body())
                    } else {
                        println("Failed incidents")
                    }
                } else {
                    Toast.makeText(context, "No user_id provided", Toast.LENGTH_SHORT).show()
                }
            }
        },
    ) {
        Text("Start Service")
    }
}