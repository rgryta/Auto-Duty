package eu.gryta.autoduty.pagerduty

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class Status {
    @SerialName("triggered")
    TRIGGERED,
    @SerialName("acknowledged")
    ACKNOWLEDGED,
    @SerialName("resolved")
    RESOLVED,
}

enum class Urgency {
    @SerialName("high")
    HIGH,
    @SerialName("low")
    LOW
}

@Serializable
data class Incident(
    val id: String,
    val self: String,
    val title: String,
    val status: Status,
    val urgency: Urgency,
)
