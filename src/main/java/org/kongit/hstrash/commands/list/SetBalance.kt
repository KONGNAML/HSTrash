package org.kongit.hstrash.commands.list

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.config.Configurations
import com.google.gson.JsonObject
import org.apache.commons.lang3.tuple.Pair
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.kongit.hstrash.HSTrash
import org.kongit.hstrash.util.MessageUtil

class SetBalance constructor(private val sender: CommandSender) {


    private val config: Configurations = HSTrash.getInstance()!!.configurations
    private val storage = HSTrash.getInstance()!!.storage

    fun runCommands(data: CommandData) : Boolean? {

        if (data.args(0) != config.command.get("balance")) return null;
        if (sender !is Player || sender is ConsoleCommandSender) {
            MessageUtil().sendAnnounce(sender,config.message.get("console"))
            return true
        };
        if (!sender.hasPermission("hstrash.commands.balance")) {
            MessageUtil().sendAnnounce(sender,config.message.get("permission"))
            return true
        };

        if (data.length() <= 1) { return false }
        if ((data.args(1).toString().toInt() ?: -1) < 0) { return false }
        storage.set("Trash", Pair.of("setting","trash"), Pair.of("balance",data.args(1)))

        try { MessageUtil().sendAnnounce(sender,config.message.get("commands.balance.success"))
        } catch (e: Error) { MessageUtil().sendAnnounce(sender,config.message.get("commands.balance.failure")) }
        return true
    }

}