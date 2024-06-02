package org.kongit.hstrash.dependency


import com.github.horangshop.lib.plugin.dependency.HSPapi
import org.apache.commons.lang3.tuple.Pair
import org.bukkit.OfflinePlayer
import org.bukkit.entity.Player
import org.kongit.hstrash.HSTrash

class PlaceHolderAPI : HSPapi(HSTrash.getInstance()) {

    override fun request(player: OfflinePlayer?, data: Array<out String>?): String {
        if (data != null) {
            when (data[0]) {
                else -> { return  "ERROR" }
            }
        }
        return "ERROR"
    }


}