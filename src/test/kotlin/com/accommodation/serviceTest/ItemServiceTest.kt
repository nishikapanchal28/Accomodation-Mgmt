package com.accommodation.serviceTest

import com.accommodation.model.Item
import com.accommodation.model.Location
import com.accommodation.repository.ItemRepository
import com.accommodation.service.ItemServiceImpl
import com.accommodation.service.ValidateData
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*

@SpringBootTest
class ItemServiceImplTest {

    @Mock
    private lateinit var itemRepository: ItemRepository

    @Mock
    private lateinit var validateData: ValidateData

    @InjectMocks
    private lateinit var itemService: ItemServiceImpl

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.openMocks(this)
        itemService = ItemServiceImpl(itemRepository, validateData)
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
    fun testGetAllItemsForHotelier() {
        val hotelierId = 1L
        val items = listOf(Item(id = 1, name = "Item 1"), Item(id = 2, name = "Item 2"))
        `when`(itemRepository.findAllByHotelierId(hotelierId)).thenReturn(items)

        val result = itemService.getAllItemsForHotelier(hotelierId)

        verify(itemRepository, times(1)).findAllByHotelierId(hotelierId)
        assertEquals(items, result)
    }

    @Test
    @DisplayName("Test get item By its Id when item exists")
    fun testGetItemById_ItemExists() {
        val itemId = 1L
        val item = Item(id = itemId, name = "Item 1")
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item))

        val result = itemService.getItemById(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        assertEquals(item, result)
    }

    @Test
    @DisplayName("Test get item By its Id when item does not exists")
    fun testGetItemById_ItemNotExists() {
        val itemId = 1L
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.empty())

        val result = itemService.getItemById(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        assertNull(result)
    }

    @Test
    @DisplayName("Test creating a valid item")
    fun testCreateValidItem() {
        val hotelierId = 1L
        val newItem = Item(
                name = "Valid Item Name",
                rating = 4,
                category = "hotel",
                location = Location(
                        id = 2,
                        city = "Delhi",
                        state = "Morelos",
                        country = "India",
                        zipCode = 62448,
                        address = "Boulevard Díaz Ordaz No. 9 Cantarranas"
                ),
                image = "http://valid-image-url.com",
                reputation = 800,
                reputationBadge = "green",
                price = 1000.0,
                availability = 1,
                hotelierId = 100
        )
        val createdItem = Item(id = 1, name = "Valid Item Name")

        `when`(itemRepository.save(newItem)).thenReturn(createdItem)

        val result = itemService.createItem(hotelierId, newItem)

        verify(itemRepository, times(1)).save(newItem)
        assertEquals(createdItem, result)
    }

    @Test
    @DisplayName("Test update item when item exists")
    fun testUpdateItem_ItemExists() {
        val itemId = 1L
        val updatedItem = Item(id = itemId, name = "Updated Item")
        val existingItem = Item(id = itemId, name = "Existing Item")
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(existingItem))
        `when`(itemRepository.save(existingItem)).thenReturn(updatedItem)

        val result = itemService.updateItem(itemId, updatedItem)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(1)).save(existingItem)
        assertEquals(updatedItem, result)
    }

    @Test
    @DisplayName("Test update item when item does not exists")
    fun testUpdateItem_ItemNotExists() {
        val itemId = 1L
        val updatedItem = Item(id = itemId, name = "Updated Item")
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.empty())

        val result = itemService.updateItem(itemId, updatedItem)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(0)).save(any())
        assertNull(result)
    }

    @Test
    @DisplayName("Test delete item when item exists")
    fun testDeleteItem_ItemExists() {
        val itemId = 1L
        `when`(itemRepository.existsById(itemId)).thenReturn(true)

        val result = itemService.deleteItem(itemId)

        verify(itemRepository, times(1)).existsById(itemId)
        verify(itemRepository, times(1)).deleteById(itemId)
        assertTrue(result)
    }

    @Test
    @DisplayName("Test delete item when item does not exists")
    fun testDeleteItem_ItemNotExists() {
        val itemId = 1L
        `when`(itemRepository.existsById(itemId)).thenReturn(false)

        val result = itemService.deleteItem(itemId)

        verify(itemRepository, times(1)).existsById(itemId)
        verify(itemRepository, times(0)).deleteById(anyLong())
        assertFalse(result)
    }

    @Test
    @DisplayName("Test book item when item exists and available")
    fun testBookItem_ItemExistsAndAvailable() {
        val itemId = 1L
        val initialAvailability = 1
        val bookedItem = Item(id = itemId, availability = initialAvailability)

        `when`(itemRepository.findById(itemId)).thenReturn(Optional.of(bookedItem))
        `when`(itemRepository.save(any(Item::class.java))).thenAnswer { it.arguments[0] }

        val result = itemService.bookItem(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(1)).save(any(Item::class.java))
        assertNotNull(result)
        assertEquals(initialAvailability - 1, result?.availability)
    }

    @Test
    @DisplayName("Test book item when item does not exists")
    fun testBookItem_ItemNotExists() {
        val itemId = 1L
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.empty())

        val result = itemService.bookItem(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(0)).save(any())
        assertNull(result)
    }

    @Test
    @DisplayName("Test book item when item exists but not available")
    fun testBookItem_ItemExistsButNotAvailable() {
        val itemId = 1L
        val item = Item(id = itemId, name = "Item 1", availability = 0)
        `when`(itemRepository.findById(itemId)).thenReturn(java.util.Optional.of(item))

        val result = itemService.bookItem(itemId)

        verify(itemRepository, times(1)).findById(itemId)
        verify(itemRepository, times(0)).save(any())
        assertNull(result)
    }
}