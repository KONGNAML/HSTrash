package org.kongit.hstrash.dependency

import net.milkbowl.vault.chat.Chat
import net.milkbowl.vault.economy.Economy
import net.milkbowl.vault.permission.Permission
import org.bukkit.Bukkit.getServer
import org.bukkit.entity.Player


class Vault {

    companion object {
        private var econ: Economy? = null
        private val perms: Permission? = null
        private val chat: Chat? = null
    }

    fun setupEconomy(): Boolean {
        if (getServer().pluginManager.getPlugin("Vault") == null) { return false }
        val rsp = getServer().servicesManager.getRegistration(Economy::class.java) ?: return false
        econ = rsp.provider
        return econ != null
    }

    fun addEconomy(player: Player, amount: Double) : Boolean {
        if (econ == null) return false
        val r = econ!!.depositPlayer(player, amount)
        return r.transactionSuccess()
    }


    fun getEconomy(player: Player) : Double {
        if (econ == null) return -1.0
        return econ?.getBalance(player) ?: 0.0
    }

}