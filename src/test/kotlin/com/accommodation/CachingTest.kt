package com.accommodation

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cache.CacheManager
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class CachingTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var cacheManager: CacheManager

    @Test
    fun testCaching() {
        // Clear the cache to ensure a clean state
        val cache = cacheManager.getCache("items")
        cache?.clear()

        // Perform the first request
        mockMvc.perform(get("/items/2"))
                .andExpect(status().isOk)

        // The second request should hit the cache
        mockMvc.perform(get("/items/2"))
                .andExpect(status().isOk)
    }
}
