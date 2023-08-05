package com.accommodation

import com.accommodation.repository.ItemRepository
import com.accommodation.service.ItemService
import com.accommodation.service.ItemServiceImpl
import com.accommodation.service.ValidateData
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class AccommodationConfig {

    @Bean
    fun itemService(itemRepository: ItemRepository, validateData: ValidateData): ItemService {
        return ItemServiceImpl(itemRepository, validateData)
    }
}
