package com.accommodation.controllerTest

import com.accommodation.controller.ItemController
import com.accommodation.model.Item
import com.accommodation.service.ItemService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI

class ItemControllerTest{

    @Mock
    lateinit var itemService: ItemService

    @InjectMocks
    lateinit var itemController: ItemController

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.openMocks(this)
    }
    companion object {
        private val objectMapper = ObjectMapper()

        lateinit var itemJson: String

        @BeforeAll
        @JvmStatic
        fun setup() {
            val locationJson = """
                {
                    "locationId": 1,
                    "city": "Cuernavaca",
                    "state": "Morelos",
                    "country": "Mexico",
                    "zip_code": 62448,
                    "address": "Boulevard Díaz Ordaz No. 9 Cantarranas"
                }
            """.trimIndent()

            itemJson = """
                {
                    "itemName": "Hotel of California",
                    "rating": 5,
                    "category": "hotel",
                    "location": $locationJson,
                    "image": "http://image-url.com",
                    "reputation": 80,
                    "reputationBadge": "green",
                    "price": 1000.0,
                    "availability": 1,
                    "hotelierId": 100
                }
            """.trimIndent()
        }
    }
    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test getAllItemsForHotelier`() {
        val hotelierId = 1L
        val items = listOf(Item(id = 1, name = "Item 1"), Item(id = 2, name = "Item 2"))
        `when`(itemService.getAllItemsForHotelier(hotelierId)).thenReturn(items)

        val response: ResponseEntity<List<Item>> = itemController.getAllItemsForHotelier(hotelierId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(items, response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test getItemById`() {
        val itemId = 1L
        val item = Item(id = itemId, name = "Item 1")
        `when`(itemService.getItemById(itemId)).thenReturn(item)

        val response: ResponseEntity<Item> = itemController.getItemById(itemId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(item, response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test getItemById NotFound`() {
        val itemId = 1L
        `when`(itemService.getItemById(itemId)).thenReturn(null)

        val response: ResponseEntity<Item> = itemController.getItemById(itemId)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test createItem`() {
        val hotelierId = 1L
        val newItem = Item(name = "New Item")
        val createdItem = Item(id = 1, name = "New Item")
        `when`(itemService.createItem(hotelierId, newItem)).thenReturn(createdItem)

        val response: ResponseEntity<Item> = itemController.createItem(hotelierId, newItem)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(URI("/items/${createdItem.id}"), response.headers.location)
        assertEquals(createdItem, response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test updateItem`() {
        val itemId = 1L
        val updatedItem = Item(id = itemId, name = "Updated Item")
        `when`(itemService.updateItem(itemId, updatedItem)).thenReturn(updatedItem)

        val response: ResponseEntity<Item> = itemController.updateItem(itemId, updatedItem)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(updatedItem, response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test updateItem NotFound`() {
        val itemId = 1L
        val updatedItem = Item(id = itemId, name = "Updated Item")
        `when`(itemService.updateItem(itemId, updatedItem)).thenReturn(null)

        val response: ResponseEntity<Item> = itemController.updateItem(itemId, updatedItem)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertNull(response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test deleteItem`() {
        val itemId = 1L
        `when`(itemService.deleteItem(itemId)).thenReturn(true)

        val response: ResponseEntity<Void> = itemController.deleteItem(itemId)

        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
        assertTrue(response.body == null)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test deleteItem NotFound`() {
        val itemId = 1L
        `when`(itemService.deleteItem(itemId)).thenReturn(false)

        val response: ResponseEntity<Void> = itemController.deleteItem(itemId)

        assertEquals(HttpStatus.NOT_FOUND, response.statusCode)
        assertTrue(response.body == null)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test bookItem`() {
        val itemId = 1L
        val bookedItem = Item(id = itemId, name = "Booked Item")
        `when`(itemService.bookItem(itemId)).thenReturn(bookedItem)

        val response: ResponseEntity<Item> = itemController.bookItem(itemId)

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(bookedItem, response.body)
    }

    @Test
    @DisplayName("Test getting all items for a hotelier")
    fun `test bookItem BadRequest`() {
        val itemId = 1L
        `when`(itemService.bookItem(itemId)).thenReturn(null)

        val response: ResponseEntity<Item> = itemController.bookItem(itemId)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertNull(response.body)
    }
}
