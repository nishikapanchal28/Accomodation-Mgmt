package com.accommodation.controller

import com.accommodation.model.Item
import com.accommodation.service.ItemService
import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI
import org.slf4j.Logger

@RestController
class ItemController(private val itemService: ItemService) {

    //get all the items for the given hotelier.
    private val logger: Logger = org.slf4j.LoggerFactory.getLogger(ItemController::class.java)
    @Operation(summary = "Get all items for a hotelier",
            description = "Retrieves a list of all items associated with a specific hotelier.")
    @GetMapping("/hoteliers/{hotelierId}/items")
    fun getAllItemsForHotelier(@PathVariable hotelierId: Long): ResponseEntity<List<Item>> {
        logger.info("Getting all items for hotelier with ID: $hotelierId")
        val items = itemService.getAllItemsForHotelier(hotelierId)
        return ResponseEntity.ok(items)
    }

    //get a single item.
    @Operation(summary = "Get an item by ID",
            description = "Retrieves a single item by its ID")
    @GetMapping("/items/{itemId}")
    fun getItemById(@PathVariable itemId: Long): ResponseEntity<Item> {
        logger.info("Getting an item with ID: $itemId")
        val item = itemService.getItemById(itemId)
        return item?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    //create new entries.
    @Operation(summary = "Create an items",
            description = "Creates a new item in the application.")
    @PostMapping("/hoteliers/{hotelierId}/items")
    fun createItem(@PathVariable hotelierId: Long, @RequestBody newItem: Item): ResponseEntity<Item> {
        logger.info("Creating a new item for hotelier with ID: $hotelierId")
        val createdItem = itemService.createItem(hotelierId, newItem)
        logger.info("Item created successfully with ID: ${createdItem.id}")
        return ResponseEntity.created(URI("/items/${createdItem.id}")).body(createdItem)
    }

    //update information of items
    @Operation(summary = "Update information of item",
            description = "Update items which is already present in the application.")
    @PutMapping("/items/{itemId}")
    fun updateItem(@PathVariable itemId: Long, @RequestBody updatedItem: Item): ResponseEntity<Item> {
        logger.info("Updating an item with ID: $itemId")
        val item = itemService.updateItem(itemId, updatedItem)
        logger.info("Item updated successfully with ID: ${updatedItem.id}")
        return item?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.NOT_FOUND).build()
    }

    //delete any item.
    @Operation(summary = "Delete an item",
            description = "Delete an item from the application.")
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

    //booking endpoint
    @Operation(summary = "Booking an item",
            description = "Booking of hotel by its Id and availability")
    @PostMapping("/items/{itemId}/bookItem")
    fun bookItem(@PathVariable itemId: Long): ResponseEntity<Item> {
        logger.info("Booking an item with ID: $itemId")
        val bookedItem = itemService.bookItem(itemId)
        logger.info("Item booked successfully with ID: $itemId")
        return bookedItem?.let { ResponseEntity.ok(it) } ?: ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
    }

}