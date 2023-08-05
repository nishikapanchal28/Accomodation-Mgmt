package com.accommodation.service

import com.accommodation.model.Item
import com.accommodation.model.ReputationBadge
import com.accommodation.repository.ItemRepository
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class ItemServiceImpl(private val itemRepository: ItemRepository, private val validateData: ValidateData) : ItemService {

    @Cacheable("items")
    override fun getAllItemsForHotelier(hotelierId: Long): List<Item> {
        return itemRepository.findAllByHotelierId(hotelierId)
    }

    @Cacheable("items")
    override fun getItemById(itemId: Long, rating: Int, city: String, reputationBadge: String): Item? {
        val item = itemRepository.findById(itemId).orElse(null)
        if (item != null) {

            if (rating != null && item.rating != rating) {
                return null
            }

            if (city != null && item.location.city != city) {
                return null
            }

            if (reputationBadge != null && item.reputationBadge != reputationBadge) {
                return null
            }
        }
        return item
    }

    @CacheEvict(value = arrayOf("items"), allEntries = true)
    override fun createItem(hotelierId: Long, newItem: Item): Item {
        val validateData = ValidateData()
        validateData.validateItem(newItem)
        validateData.throwIfInvalid()
            newItem.hotelierId = hotelierId
        return itemRepository.save(newItem)
    }

    @CacheEvict(value = arrayOf("items"), allEntries = true)
    override fun updateItem(itemId: Long, updatedItem: Item): Item? {
        val existingItem = itemRepository.findById(itemId).orElse(null)
        if (existingItem != null) {
            existingItem.apply {
                name = updatedItem.name
                rating = updatedItem.rating
                category = updatedItem.category
                location = updatedItem.location
                image = updatedItem.image
                reputation = updatedItem.reputation
                reputationBadge = updatedItem.reputationBadge
                price = updatedItem.price
                availability = updatedItem.availability
            }
            return itemRepository.save(existingItem)
        }
        return null
    }

    @CacheEvict(value = arrayOf("items"), allEntries = true)
    override fun deleteItem(itemId: Long): Boolean {
        if (itemRepository.existsById(itemId)) {
            itemRepository.deleteById(itemId)
            return true
        }
        return false
    }

    override fun bookItem(itemId: Long): Item? {
        val item = itemRepository.findById(itemId).orElse(null)
        if (item != null && item.availability > 0) {
            item.availability -= 1
            return itemRepository.save(item)
        }
        return null
    }

}
