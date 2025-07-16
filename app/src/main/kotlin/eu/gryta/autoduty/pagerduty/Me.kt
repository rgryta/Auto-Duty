package eu.gryta.autoduty.pagerduty

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Me(
    val id: String,
    val type: String,
    val name: String,
    val email: String,
    @SerialName("time_zone") val timeZone: String,
    val color: String,
    val role: String,
    @SerialName("job_title") val jobTitle: String,
    @SerialName("avatar_url") val avatarUrl: String,
    val description: String
)
