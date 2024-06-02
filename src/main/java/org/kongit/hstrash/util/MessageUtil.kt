package org.kongit.hstrash.util

import com.github.horangshop.lib.plugin.config.Configurations
import com.github.horangshop.lib.util.common.ComponentUtil
import net.kyori.adventure.audience.Audience
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.kongit.hstrash.HSTrash
import java.util.UUID

class MessageUtil {

    private val plugin = HSTrash.getInstance()!!
    private val config: Configurations = HSTrash.getInstance()!!.configurations
    companion object { private val limit: MutableMap<UUID,MutableMap<String,Long>> = mutableMapOf() }

    fun sendAnnounce(sender: CommandSender,miniMessage: String) {
        val audience: Audience = plugin.adventure.sender(sender)
        audience.sendMessage(config.message.prefix.append(ComponentUtil.formatted(sender,miniMessage)))
    }

    fun sendLimitMessage(sender: Player, miniMessage: String, time: Int) {
        val now = System.currentTimeMillis()
        if (limit[sender.uniqueId] == null) { limit[sender.uniqueId] = mutableMapOf() }
        val record = limit[sender.uniqueId]!![miniMessage]
        if (((now as Long).minus(record ?: 0L)) > 1000L * time) {
            limit[sender.uniqueId]!![miniMessage] = now as Long
            this.sendAnnounce(sender,miniMessage)
        } else {
            limit[sender.uniqueId]!![miniMessage] = now as Long
        }
    }


    fun formatNumber(input: Int, pattern: String = "#,###"): String {
        val result = StringBuilder()
        val numericString = input.toString().reversed()
        var count = 0
        var sectionLength = 0

        val sectionLengths = pattern.split(',').map { it.length }.reversed()

        for (char in numericString) {
            if (sectionLength >= sectionLengths[count] && count < sectionLengths.size - 1) {
                result.append(',')
                sectionLength = 0
                count++
            }
            result.append(char)
            sectionLength++
        }

        return result.reverse().toString()
    }


}