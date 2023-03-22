package hu.bme.aut.bethere.data.model

data class User(
    val id: String = "",
    val name: String = "",
    val photo: String = "",
    val friends: MutableList<String> = mutableListOf(),
    val events: MutableList<String> = mutableListOf()
) {
    fun doesMatchSearchQuery(query: String) = name.contains(query, ignoreCase = true)
}


