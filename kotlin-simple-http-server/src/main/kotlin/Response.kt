import enums.Status

import java.io.IOException
import java.io.OutputStream
import java.nio.charset.StandardCharsets
import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Response(private val status: Status, private val contentType: String, private val body: ByteArray) {
    private val contentLength: Int = body.size

    @Throws(IOException::class)
    fun writeTo(out: OutputStream) {

        val now = OffsetDateTime.now(ZoneOffset.UTC)

        val response = "HTTP/1.1 " + status.statusCode + CRLF +
                "Date: " + rfc1123Formatter.format(now) + CRLF +
                "Server: KotlinSimpleHttpServer" + CRLF +
                "Content-Type: " + contentType + CRLF +
                "Content-Length: " + contentLength.toString() + CRLF +
                "Connection: Close" + CRLF +
                CRLF

        out.write(response.toByteArray(StandardCharsets.UTF_8))
        out.write(body)

        out.flush()
    }

    companion object {

        private val rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME
        private const val CRLF = "\r\n"
    }
}
