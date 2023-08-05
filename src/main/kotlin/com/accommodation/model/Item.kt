package com.accommodation.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "items")
data class Item(
        @get:JsonIgnore
        @JsonProperty("itemId")
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "item_id")
        var id: Long? = null,

        @JsonProperty("itemName")
        @Column(name = "item_name", nullable = false)
        var name: String = "",

        @JsonProperty("rating")
        @Column(name = "item_rating", nullable = false)
        var rating: Int = 0,

        @JsonProperty("category")
        @Column(name = "item_category", nullable = false)
        var category: String = "",

        @JsonIgnore
        @JsonProperty("location")
        @OneToOne(cascade = [CascadeType.ALL])
        @JoinColumn(name = "location_id")
        var location: Location = Location(),

        @JsonProperty("image")
        @Column(name = "item_image", nullable = false)
        var image: String = "",

        @JsonProperty("reputation")
        @Column(name = "item_reputation", nullable = false)
        var reputation: Int = 0,

        @JsonProperty("reputationBadge")
        @Column(name = "item_reputation_badge", nullable = false)
        var reputationBadge: String = "",

        @JsonProperty("price")
        @Column(name = "item_price", nullable = false)
        var price: Double = 0.0,

        @JsonProperty("availability")
        @Column(name = "item_availability", nullable = false)
        var availability: Int = 0,

        @JsonProperty("hotelierId")
        @Column(name = "hotelier_id")
        var hotelierId: Long? = null
) {
    constructor(
            name: String,
            rating: Int,
            category: String,
            location: Location,
            image: String,
            reputation: Int,
            reputationBadge: String,
            price: Double,
            availability: Int,
            hotelierId: Long? = null
    ) : this(
            null,
            name,
            rating,
            category,
            location,
            image,
            reputation,
            reputationBadge,
            price,
            availability,
            hotelierId
    )

}





