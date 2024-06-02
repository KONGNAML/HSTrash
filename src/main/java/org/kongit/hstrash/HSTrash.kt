package org.kongit.hstrash


import com.github.horangshop.lib.plugin.HSPlugin
import com.github.horangshop.lib.util.common.ComponentUtil
import com.google.gson.JsonObject
import org.kongit.hstrash.commands.Command
import org.kongit.hstrash.dependency.Vault
import org.kongit.hstrash.events.BasketEvents


class HSTrash : HSPlugin(ComponentUtil.miniMessage("<gradient:#ff9633:#ffd633>HSTrash | </gradient>")) {

    companion object {
        private var plugins : HSTrash? = null

        fun getInstance() : HSTrash? { return plugins }
    }


    override fun enable() {
        plugins = this
        configurations.initStorage(listOf("Trash","User"))
        val jsonObject =  JsonObject()
        jsonObject.addProperty("setting","trash")
        storage.add("Trash",jsonObject)
        Vault().setupEconomy()
        this.register()
    }


    override fun disable() {
        plugins = null
    }

    private fun register() {

        registerCommand(Command())
        registerEvent(BasketEvents())
    }

}
