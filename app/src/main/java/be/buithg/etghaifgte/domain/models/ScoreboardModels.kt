package be.buithg.etghaifgte.domain.models

import java.io.Serializable

// Response from ESPN scoreboard endpoint
// Only necessary fields are declared for mapping to the app domain models

data class ScoreboardResponse(
    val leagues: List<League>?,
    val events: List<Event>?
) : Serializable

data class League(
    val abbreviation: String?
) : Serializable

data class Event(
    val id: String?,
    val date: String?,
    val name: String?,
    val shortName: String?,
    val competitions: List<Competition>?,
    val status: Status?
) : Serializable

data class Competition(
    val startDate: String?,
    val competitors: List<Competitor>?,
    val venue: Venue?,
    val status: Status?
) : Serializable

data class Venue(
    val fullName: String?,
    val address: Address?
) : Serializable

data class Address(
    val city: String?,
    val country: String?
) : Serializable

data class Competitor(
    val homeAway: String?,
    val score: String?,
    val team: Team?
) : Serializable

data class Team(
    val displayName: String?,
    val shortDisplayName: String?
) : Serializable

data class Status(
    val type: StatusType?
) : Serializable

data class StatusType(
    val description: String?,
    val state: String?,
    val completed: Boolean?
) : Serializable
