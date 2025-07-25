package eu.gryta.autoduty.pagerduty

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Me(
    val id: String,
    val name: String,
    val email: String,
)
