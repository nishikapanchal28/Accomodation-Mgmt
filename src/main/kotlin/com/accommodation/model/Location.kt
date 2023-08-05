package com.accommodation.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(name = "locations")
data class Location (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @JsonProperty("locationId")
        @Column(name = "location_id")
        var id: Long? = null,

        @JsonIgnore
        @OneToOne(mappedBy = "location")
        var item: Item? = null,

        @JsonProperty("city")
        @Column(name = "city", nullable = false)
        var city: String = "",

        @JsonProperty("state")
        @Column(name = "state", nullable = false)
        var state: String = "",

        @JsonProperty("country")
        @Column(name = "country", nullable = false)
        var country: String = "",

        @JsonProperty("zip_code")
        @Column(name = "zip_code", nullable = false)
        var zipCode: String = "",

        @JsonProperty("address")
        @Column(name = "address", nullable = false)
        var address: String = ""
)
