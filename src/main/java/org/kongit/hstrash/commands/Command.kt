package org.kongit.hstrash.commands

import com.github.horangshop.lib.plugin.command.CommandData
import com.github.horangshop.lib.plugin.command.HSCommand
import com.github.horangshop.lib.plugin.config.Configurations
import org.kongit.hstrash.HSTrash
import org.kongit.hstrash.commands.list.OpenBasket
import org.kongit.hstrash.commands.list.PluginReload
import org.kongit.hstrash.commands.list.SetBalance
import org.kongit.hstrash.dependency.Vault

class Command : HSCommand(HSTrash.getInstance(), listOf("HSTrash","Trash","쓰레기")) {

    private val config: Configurations = HSTrash.getInstance()!!.configurations

    override fun command(data: CommandData?) : Boolean {

        var wrong: Boolean = false

        wrong = data?.let { OpenBasket(sender).runCommands(it) } ?: wrong
        wrong = data?.let { SetBalance(sender).runCommands(it) } ?: wrong
        wrong = data?.let { PluginReload(sender).runCommands(it) } ?: wrong

        return wrong
    }

    override fun reload(command: CommandData?) {
        Vault().setupEconomy()
    }

    override fun wrongUsage(command: CommandData?) {
        when (command?.args(0)) {
            config.command.get("balance") -> {
                sendMessage(config.message.get("wrongUsage.balance"))
            }
        }
    }

    override fun tabComplete(data: CommandData): MutableList<String> {
        val tab: MutableList<String> = mutableListOf()
        when (data.length()) {
            1 -> {
                tab.add(config.command.get("basket"))
                if (sender.isOp){
                    tab.add(config.command.get("balance"))
                    tab.add(config.command.get("reload"))
                }
            }
        }
        return tab
    }
}

