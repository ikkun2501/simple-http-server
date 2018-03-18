import java.io.IOException
import java.nio.file.Path
import java.util.*

class MimeDetector(configFileName: String) {

    private val prop: Properties

    init {
        val inputStream = this.javaClass.getResourceAsStream(configFileName)
        val p = Properties()
        try {
            p.load(inputStream)
        } catch (e: IOException) {
            System.err.println("Failed to load mime config")
        }

        this.prop = p
    }


    /**
     * 与えられたPathの拡張子に対応するMIMEを返す。
     * 該当する拡張子がない場合はapplication/octed-streamを返す。
     *
     * @param path
     * @return
     */
    fun getMime(path: Path): String {
        val fileName = path.fileName.toString()
        val ext = fileName.substring(fileName.indexOf(".") + 1)
        return this.prop.getProperty(ext, "application/octet-stream")
    }
}
