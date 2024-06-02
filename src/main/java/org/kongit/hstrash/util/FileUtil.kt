package org.kongit.hstrash.util
import com.github.horangshop.lib.util.common.FileUtil
import org.kongit.hstrash.HSTrash
import java.io.File

class FileUtil : FileUtil() {
    fun listAllFiles(directory: File): List<String> {
        val result = mutableListOf<String>()

        fun recurse(dir: File) {
            val files = dir.listFiles() ?: return
            for (file in files) {
                if (file.isDirectory) {
                    recurse(file)
                } else {
                    result.add(file.absolutePath.toString().split(HSTrash.getInstance()!!.dataFolder.path+"\\").last())
                }
            }

        }
        recurse(directory)
        return result
    }
    fun createDirectories(directories: List<String>): List<String> {
        val createdDirs = mutableListOf<String>()
        for (dirPath in directories) {
            val dir = File(dirPath)
            if (dir.mkdirs()) {
                createdDirs.add(dirPath)
            } else if (dir.exists()) { continue
            } else { continue }
        }
        return createdDirs
    }
}