package org.kongit.hstrash.commands.list

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.config.Configurations
import org.apache.commons.lang3.tuple.Pair
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.bukkit.permissions.Permission
import org.kongit.hstrash.HSTrash
import org.kongit.hstrash.events.BasketEvents
import org.kongit.hstrash.util.MessageUtil


class OpenBasket constructor(private val sender: CommandSender) {


    private val config: Configurations = HSTrash.getInstance()!!.configurations

    fun runCommands(data: CommandData) : Boolean? {

        if (data.args(0) != config.command.get("basket") && data.args(0) != "") return null;
        if (sender !is Player || sender is ConsoleCommandSender) {
            MessageUtil().sendAnnounce(sender,config.message.get("console"))
            return true
        };
        if (!sender.hasPermission("hstrash.commands.basket")) {
            MessageUtil().sendAnnounce(sender,config.message.get("permission"))
            return true
        };
        try {
            BasketEvents().openInventory(sender)
            MessageUtil().sendAnnounce(sender,config.message.get("commands.basket.success"))
        } catch (e: Error) { MessageUtil().sendAnnounce(sender,config.message.get("commands.basket.failure")) }
        return true
    }

}