package eu.gryta.autoduty.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import eu.gryta.autoduty.App
import eu.gryta.autoduty.db.RoomInstance
import eu.gryta.autoduty.db.schema.Setting
import eu.gryta.autoduty.db.schema.SettingKey
import eu.gryta.autoduty.pagerduty.Me
import eu.gryta.autoduty.pagerduty.PDClient
import io.ktor.http.isSuccess
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking


enum class Status {
    IDLE,
    SUCCESS,
    LOADING,
    ERROR,
}

@Composable
fun TokenInput() {
    val coroutineScope = rememberCoroutineScope()
    val apiToken = remember { mutableStateOf("") }
    val status: MutableState<Status> = remember { mutableStateOf(Status.IDLE) }
    val meInstance: MutableState<Me?> = remember { mutableStateOf(null) }

    runBlocking {
        val dbToken = RoomInstance.instance.dao.getByKey(SettingKey.API_TOKEN)
        if (dbToken != null) {
            apiToken.value = dbToken.value ?: ""
        }
    }
    Column(
        modifier = Modifier.fillMaxWidth(),
    ) {
        TextField(
            value = apiToken.value,
            onValueChange = {
                apiToken.value = it
                coroutineScope.launch {
                }
            },
            label = { Text("API Token") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(
                    enabled = status.value != Status.LOADING,
                    onClick = {
                        status.value = Status.LOADING
                        coroutineScope.launch {
                            App.instance.applyToken(token = apiToken.value)

                            val me = PDClient.Users.Me.get()
                            if (me.status.isSuccess()) {
                                meInstance.value = me.body().user

                                RoomInstance.instance.dao.upsert(
                                    Setting(
                                        key = SettingKey.USER_ID,
                                        value = me.body().user.id
                                    )
                                )
                                status.value = Status.SUCCESS
                            } else {
                                status.value = Status.ERROR
                            }
                        }
                    }
                ) {
                    when (status.value) {
                        Status.IDLE ->
                            Icon(imageVector = Icons.Outlined.Refresh, contentDescription = "idle")

                        Status.SUCCESS ->
                            Icon(imageVector = Icons.Outlined.Check, contentDescription = "success")

                        Status.LOADING ->
                            CircularProgressIndicator(
                                color = LocalContentColor.current,
                                strokeWidth = 2.dp,
                                modifier = Modifier.size(24.dp),
                            )

                        Status.ERROR ->
                            Icon(
                                imageVector = Icons.Outlined.Error,
                                contentDescription = "error"
                            )
                    }
                }

            }
        )
        UserInfo(meInstance)
    }
}