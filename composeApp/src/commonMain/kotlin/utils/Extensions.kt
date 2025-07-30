package utils



fun String.toTitleCase(): String {
    val words = this.split(" ")
    val capitalizedWords = words.map { word ->
        if (word.length > 0) {
            val firstChar = word.substring(0, 1).toUpperCase()
            val remainingChars = word.substring(1).toLowerCase()
            firstChar + remainingChars
        } else {
            word
        }
    }
    return capitalizedWords.joinToString(" ")
}


fun convertDayOfWeek(dayOfWeek: String): String {
    return when (dayOfWeek.toUpperCase()) {
        "MONDAY" -> "Mo"
        "TUESDAY" -> "Tu"
        "WEDNESDAY" -> "Wed"
        "THURSDAY" -> "Th"
        "FRIDAY" -> "Fr"
        "SATURDAY" -> "Sa"
        "SUNDAY" -> "Su"
        else -> "Su"
    }
}



