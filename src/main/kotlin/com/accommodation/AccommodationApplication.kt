package com.accommodation

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = ["com.accommodation.repository"])
class AccommodationApplication

	fun main(args: Array<String>) {
		runApplication<AccommodationApplication>(*args)
	}

