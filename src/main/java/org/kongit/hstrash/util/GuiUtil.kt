package org.kongit.hstrash.util

import com.github.horangshop.lib.util.common.ComponentUtil
import com.github.horangshop.lib.util.support.ItemUtil
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType


class GuiUtil {
    fun getSlot(slot: String) : List<Int> {
        val returnSlot:MutableList<Int> = mutableListOf()
        if (slot == "empty") return listOf(0,1,2,3,4,5,6,7,8)
        for (key in slot.split(",")) {
            if (key.contains("~")) {
                val start = key.split("~")[0].toInt() ?: continue
                val end = key.split("~")[1].toInt() ?: continue
                for (i in start..end) { if (!returnSlot.contains(i)) { returnSlot.add(returnSlot.size,i.toInt()) } }
                continue
            }
            returnSlot.add(returnSlot.size,key.toInt() ?: continue)
        }
        return returnSlot
    }
    fun hasEnoughSpace(player: Player, item: ItemStack?): Boolean {
        val inventory: Inventory = player.inventory
        if (item == null) return false
        val maxStackSize = item.maxStackSize
        var amountToFit = item.amount

        for (i in 0..35) {
            val existingItem = inventory.getItem(i)
            if (existingItem == null) { amountToFit -= maxStackSize
            } else if (existingItem.isSimilar(item)) { amountToFit -= (maxStackSize - existingItem.amount) }

            if (amountToFit <= 0) { return true }
        }

        return amountToFit <= 0
    }

}