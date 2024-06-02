package org.kongit.hstrash.commands.list

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.config.Configurations
import org.apache.commons.lang3.tuple.Pair
import org.bukkit.command.CommandSender
import org.bukkit.command.ConsoleCommandSender
import org.bukkit.entity.Player
import org.kongit.hstrash.HSTrash
import org.kongit.hstrash.dependency.Vault
import org.kongit.hstrash.util.MessageUtil

class PluginReload constructor(private val sender: CommandSender) {

    private val config: Configurations = HSTrash.getInstance()!!.configurations
    private val plugin = HSTrash.getInstance()!!

    fun runCommands(data: CommandData) : Boolean? {

        if (data.args(0) != config.command.get("reload")) return null;
        if (sender !is Player || sender is ConsoleCommandSender) {
            MessageUtil().sendAnnounce(sender,config.message.get("console"))
            return true
        };
        if (!sender.hasPermission("hstrash.commands.reload")) {
            MessageUtil().sendAnnounce(sender,config.message.get("permission"))
            return true
        };

        try {
            plugin.configurations.reload()
            Vault().setupEconomy()
            MessageUtil().sendAnnounce(sender,config.message.get("commands.reload.success"))
        } catch (e: Error) { MessageUtil().sendAnnounce(sender,config.message.get("commands.reload.failure")) }
        return true
    }

}