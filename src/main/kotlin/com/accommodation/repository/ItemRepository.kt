package com.accommodation.repository

import com.accommodation.model.Item
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ItemRepository : JpaRepository<Item, Long>{
    fun findAllByHotelierId(hotelierId: Long): List<Item>
    override fun findById(itemId:Long): Optional<Item>
    override fun <S : Item?> save(entity: S): S
    override fun deleteById(itemId:Long)

}