import java.io.IOException
import java.net.ServerSocket

object KotlinSimpleHttpServer {
    private const val PORT = 8080

    @Throws(IOException::class)
    @JvmStatic
    fun main(args: Array<String>) {

        val serverSocket = ServerSocket(PORT)
        val parser = RequestParser()
        val handler = RequestHandler()

        println("HTTP Server Start! Listening at $PORT!")

        while (true) {
            try {
                val socket = serverSocket.accept()
                val worker = WorkerThread(socket, parser, handler)
                worker.start()
            } catch (e: IOException) {
                System.err.println("Failed to dispatch: " + e.message)
            }

        }
    }
}
