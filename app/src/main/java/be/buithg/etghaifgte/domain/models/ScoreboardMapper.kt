package be.buithg.etghaifgte.domain.models

// Helper to convert API response to domain model


fun ScoreboardResponse.toMatches(): List<Match> {
    val leagueAbbr = leagues?.firstOrNull()?.abbreviation
    return events.orEmpty().mapNotNull { it.toMatch(leagueAbbr) }
}

fun Event.toMatch(league: String?): Match? {
    val comp = competitions?.firstOrNull() ?: return null
    val venue = comp.venue
    val home = comp.competitors?.find { it.homeAway == "home" }
    val away = comp.competitors?.find { it.homeAway == "away" }
    return Match(
        date = date?.substringBefore("T"),
        dateTimeGMT = comp.startDate,
        status = comp.status?.type?.description,
        matchType = shortName,
        league = league,
        venue = venue?.fullName,
        city = venue?.address?.city,
        country = venue?.address?.country,
        teamA = home?.team?.displayName,
        teamB = away?.team?.displayName,
        scoreA = home?.score?.toIntOrNull(),
        scoreB = away?.score?.toIntOrNull(),
        matchEnded = comp.status?.type?.completed ?: false
    )
}
