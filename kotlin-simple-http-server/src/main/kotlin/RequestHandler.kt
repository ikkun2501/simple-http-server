import enums.Status
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

class RequestHandler {

    @Throws(IOException::class)
    fun handlerRequest(request: Request?): Response {
        if (request == null) {
            return Response(Status.BAD_REQUEST, HTML_MIME, Files.readAllBytes(BAD_REQUEST_HTML_PATH))
        }

        val resourcePath = Paths.get("public", request.path).normalize()

        println(resourcePath)

        if (!resourcePath.startsWith("public/")) {
            return Response(Status.FORBIDDEN, HTML_MIME, Files.readAllBytes(FORBIDDEN_HTML_PATH))
        }

        if (Files.isRegularFile(resourcePath)) {
            val mime = mimeDetector.getMime(resourcePath)
            return Response(Status.OK, mime, Files.readAllBytes(resourcePath))
        }

        val indexFilePath = resourcePath.resolve("index.html")

        return if (Files.isDirectory(resourcePath) && Files.exists(indexFilePath)) {
            Response(Status.OK, HTML_MIME, Files.readAllBytes(indexFilePath))
        } else Response(Status.NOT_FOUND, HTML_MIME, Files.readAllBytes(NOT_FOUND_HTML_PATH))

    }

    companion object {

        private val BAD_REQUEST_HTML_PATH = Paths.get("public/400.html")
        private val FORBIDDEN_HTML_PATH = Paths.get("public/403.html")
        private val NOT_FOUND_HTML_PATH = Paths.get("public/404.html")
        private const val HTML_MIME = "text/html;charset=utf8"
        private val mimeDetector = MimeDetector("mimes.properties")
    }
}
