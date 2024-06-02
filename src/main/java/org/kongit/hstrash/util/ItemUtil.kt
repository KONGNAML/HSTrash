package org.kongit.hstrash.util

import com.github.horangshop.lib.util.support.ItemUtil
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack


class ItemUtil : ItemUtil() {

    private val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting().create()
    private val gson = Gson()

    fun serializeItemStack(itemStack: ItemStack): String {
        val config = YamlConfiguration()
        config["item"] = itemStack
        val yamlString = config.saveToString()
        // JSON 형식에 맞도록 이스케이프 문자 처리
        return gsonBuilder.toJson(yamlString)
    }

    fun deserializeItemStack(json: String): ItemStack? {
        // JSON에서 YAML 문자열로 변환
        val yamlString = gson.fromJson(json.replace("\\\\", "\\").replace("\\\"",""), String::class.java)
        val config = YamlConfiguration()
        try {
            config.loadFromString(yamlString)
            return config.getItemStack("item", ItemStack(Material.AIR))
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
    fun serializeInventory(inv : Inventory) : String {
        val config = YamlConfiguration()
        val slot = 0
        for (itemStack in inv.contents) {
            config["item.${slot}"] = itemStack
        }
        val yamlString = config.saveToString()
        return gsonBuilder.toJson(yamlString)
    }
    fun deserializeInventory(json: String) : MutableList<ItemStack?>? {
        val itemStacks:MutableList<ItemStack?> = mutableListOf()
        val yamlString = gson.fromJson(json.replace("\\\\", "\\").replace("\\\"",""), String::class.java)
        val config = YamlConfiguration()
        try {
            config.loadFromString(yamlString)
            for (slot in 0..54) {
                itemStacks.add(config.getItemStack("item.${slot}"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        return itemStacks
    }


}