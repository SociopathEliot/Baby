package be.buithg.etghaifgte.domain.models

import java.io.Serializable

data class TeamInfo(
    val img: String? = null,
    val name: String? = null,
    val shortname: String? = null
) : Serializable
