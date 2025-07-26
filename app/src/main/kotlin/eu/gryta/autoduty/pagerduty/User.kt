package eu.gryta.autoduty.pagerduty

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val user: Me,
)