package com.accommodation.controller

import com.accommodation.model.Item
import com.accommodation.service.ItemService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import org.slf4j.Logger

@RestController
class ItemController(private val itemService: ItemService) {

    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(ItemController::class.java)

    @GetMapping("/hoteliers/{hotelierId}/items")
    fun getAllItemsForHotelier(@PathVariable hotelierId: Long): ResponseEntity<List<Item>> {
        logger.info("Getting all items for hotelier with ID: $hotelierId")
        val items = itemService.getAllItemsForHotelier(hotelierId)
        return ResponseEntity.ok(items)
    }

    @GetMapping("/items/{itemId}")
    fun getItemById(@PathVariable itemId: Long, @RequestParam(required = false) rating: Int,
                    @RequestParam(required = false) city: String,
                    @RequestParam(required = false) reputationBadge: String): ResponseEntity<Item> {
        logger.info("Getting an item with ID: $itemId")
        val item = itemService.getItemById(itemId, rating, city, reputationBadge)
        return item?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @PostMapping("/hoteliers/{hotelierId}/items")
    fun createItem(@PathVariable hotelierId: Long, @RequestBody newItem: Item): ResponseEntity<Item> {
        logger.info("Creating a new item for hotelier with ID: $hotelierId")
        val createdItem = itemService.createItem(hotelierId, newItem)
        logger.info("Item created successfully with ID: ${createdItem.id}")
        return ResponseEntity.created(URI("/items/${createdItem.id}")).body(createdItem)
    }

    @PutMapping("/items/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @RequestBody updatedItem: Item): ResponseEntity<Item> {
        logger.info("Updating an item with ID: $itemId")
        val item = itemService.updateItem(itemId, updatedItem)
        logger.info("Item updated successfully with ID: ${updatedItem.id}")
        return item?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    @DeleteMapping("/items/{itemId}")
    fun deleteItem(@PathVariable itemId: Long): ResponseEntity<Void> {
        logger.info("Deleting an item with ID: $itemId")
        if (itemService.deleteItem(itemId)) {
            logger.info("Item deleted successfully with ID: $itemId")
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } else {
            logger.info("Item Not found for deletiony with ID: $itemId")
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }

    @PostMapping("/items/{itemId}/bookItem")
    fun bookItem(@PathVariable itemId: Long): ResponseEntity<Item> {
        logger.info("Booking an item with ID: $itemId")
        val bookedItem = itemService.bookItem(itemId)
        logger.info("Item booked successfully with ID: $itemId")
        return bookedItem?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

}