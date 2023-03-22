package hu.bme.aut.bethere.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val photo: String = "",
    val friends: List<String> = listOf(),
    val events: List<String> = listOf()
) {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}


