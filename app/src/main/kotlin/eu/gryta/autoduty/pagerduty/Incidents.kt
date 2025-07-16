package eu.gryta.autoduty.pagerduty

import kotlinx.serialization.Serializable

@Serializable
data class Incidents(
    val offset: Int,
    val limit: Int,
    val more: Boolean,
    val total: Int? = null,
    val incidents: List<Any>
)
