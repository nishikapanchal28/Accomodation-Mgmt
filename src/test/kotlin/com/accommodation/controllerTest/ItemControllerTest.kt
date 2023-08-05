package com.accommodation.controllerTest

import com.accommodation.controller.ItemController
import com.accommodation.model.Item
import com.accommodation.service.ItemService
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(ItemController::class)
class ItemControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var itemService: ItemService

    @InjectMocks
    private lateinit var itemController: ItemController

   /* @Test
    fun `Get item by ID should return OK if item exists`() {
        val itemId = 1L
        val item = Item(id = itemId, name = "Sample Item", rating = 5, category = "Hotel")

        `when`(itemService.getItemById(itemId)).thenReturn(item)

        mockMvc.perform(get("/items/$itemId"))
                .andExpect(status().isOk)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Sample Item"))
                .andExpect(jsonPath("$.rating").value(5))
                .andExpect(jsonPath("$.category").value("Hotel"))
    }
*/
   /* @Test
    fun `Get item by ID should return NOT FOUND if item does not exist`() {
        val itemId = 1L

        `when`(itemService.getItemById(itemId)).thenReturn(null)

        mockMvc.perform(get("/items/$itemId"))
                .andExpect(status().isNotFound)
    }*/


}
