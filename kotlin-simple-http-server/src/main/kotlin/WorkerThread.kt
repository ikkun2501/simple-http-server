import java.io.IOException
import java.net.Socket

class WorkerThread(private val socket: Socket,
                   private val parser: RequestParser,
                   private val handler: RequestHandler) : Thread() {

    override fun run() {
        try {

            // 拡張関数のUSEを使うとCloseされる
            // それぞれでUse関数を使用するので、階層が深くなる。
            socket.use { s ->
                s.getInputStream().use { `in` ->
                    s.getOutputStream().use { out ->

                        handler.handlerRequest(parser.fromInputStream(`in`)).writeTo(out)

                    }
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

}
