package com.accommodation.service

import com.accommodation.model.Item

interface ItemService {
    fun getAllItemsForHotelier(hotelierId: Long): List<Item>
    fun getItemById(itemId: Long): Item?
    fun createItem(hotelierId: Long, newItem: Item): Item
    fun updateItem(itemId: Long, updatedItem: Item): Item?
    fun deleteItem(itemId: Long): Boolean
    fun bookItem(itemId: Long): Item?
}