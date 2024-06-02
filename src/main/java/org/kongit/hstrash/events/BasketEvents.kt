package org.kongit.hstrash.events

import com.github.horangshop.lib.plugin.config.Configurations
import com.github.horangshop.lib.plugin.inventory.HSInventory
import org.apache.commons.lang3.tuple.Pair
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.kongit.hstrash.dependency.Vault
import org.kongit.hstrash.util.MessageUtil
import org.kongit.hstrash.HSTrash

class BasketEvents : HSInventory(HSTrash.getInstance()) {

    private var inv: Inventory? = null
    private var player: Player? = null
    private val storage = HSTrash.getInstance()!!.storage
    private val config: Configurations = HSTrash.getInstance()!!.configurations

    fun openInventory(player: Player) {
        player.openInventory(this.getInventory())
    }

    override fun getInventory(): Inventory {
        inv = Bukkit.createInventory(this, 54,"쓰레기통")
        return inv!!
    }

    override fun onClick(event: Event?, click: Click?): Boolean {
        if (click == null || event?.inventory?.holder == null) return true
        if (event.inventory.holder is BasketEvents) {
            //
        }
        return true
    }

    override fun onClose(event: Event?) {
        if (event?.inventory?.holder == null) return
        if (event.inventory.holder is BasketEvents) {
            var total = 0.0
            val balance = (storage.get("Trash", Pair.of("setting","trash"))?.get(0)?.get("balance") ?: "0.0").toString().replace("\"","").toDouble() ?: 0.0
            for (item in event.inventory.contents.filterNotNull()) {
                try {
                    total += item.amount.toDouble() * balance
                } catch (e: Error) { continue }
            }
            Vault().addEconomy(event.player,total)
            MessageUtil().sendAnnounce(event.player,config.message.get("commands.basket.reward").replace("{amount}","$total"))
        }
    }
}