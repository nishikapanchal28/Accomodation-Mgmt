package com.accommodation.service

import com.accommodation.model.Item
import com.accommodation.model.ReputationBadge
import org.springframework.stereotype.Service
import java.net.URL

@Service
class ValidateData {
    val errors = mutableListOf<String>()
    fun validateItem(item: Item): List<String> {

        if (item.name.length <= 10 || containsInvalidWords(item.name)) {
            errors.add("Hotel name must be longer than 10 characters and cannot contain invalid words.")
        }

        if (item.rating < 0 || item.rating > 5) {
            errors.add("Rating must be an integer between 0 and 5.")
        }

        val validCategories = setOf("hotel", "alternative", "hostel", "lodge", "resort", "guesthouse")
        if (item.category !in validCategories) {
            errors.add("Invalid category. It should be one of: hotel, alternative, hostel, lodge, resort, guesthouse.")
        }

        if (item.location.zipCode.toString().length != 5) {
            errors.add("Zip code must be an integer and have a length of 5.")
        }

        if (!isValidURL(item.image)) {
            errors.add("Invalid image URL.")
        }

        if (item.reputation < 0 || item.reputation > 1000) {
            errors.add("Reputation must be an integer between 0 and 1000.")
        }

        val reputationBadge = getReputationBadge(item.reputation)
        if (reputationBadge == null) {
            errors.add("Invalid reputation value.")
        }

        return errors
    }

    private fun isValidURL(imageUrl: String): Boolean {
            return try {
                val url = URL(imageUrl)
                val protocol = url.protocol
                protocol.equals("http", ignoreCase = true) || protocol.equals("https", ignoreCase = true)
            } catch (e: Exception) {
                false
            }
        }

    private fun getReputationBadge(reputation: Int): ReputationBadge? {
        return when {
            reputation <= 500 -> ReputationBadge.RED
            reputation <= 799 -> ReputationBadge.YELLOW
            else -> ReputationBadge.GREEN
        }

    }

    private fun containsInvalidWords(name: String): Boolean {
        val invalidWords = setOf("Free", "Offer", "Book", "Website")
        return invalidWords.any { word -> name.contains(word, ignoreCase = true) }
    }
    fun throwIfInvalid() {
        if (errors.isNotEmpty()) {
            throw ValidationException(errors)
        }
    }
}