import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.regex.Pattern

class RequestParser {

    @Throws(IOException::class)
    fun fromInputStream(inputStream: InputStream): Request? {
        val reader = BufferedReader(InputStreamReader(inputStream))
        val requestLine = reader.readLine()

        println(requestLine)

        if (requestLine == null) {
            return null
        }

        val matcher = requestLinePattern.matcher(requestLine)

        if (!matcher.find()) {
            return null
        }

        val method = matcher.group("method")
        val targetPath = matcher.group("path")
        val httpVersion = matcher.group("version")

        return Request(method, targetPath, httpVersion)

    }

    companion object {

        var requestLinePattern = Pattern.compile("(?<method>.*) (?<path>.*?) (?<version>.*?)")
    }
}
