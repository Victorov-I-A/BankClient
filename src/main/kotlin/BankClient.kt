import java.io.BufferedReader
import java.net.Socket
import java.net.SocketException
import java.util.*


class BankClient {
    fun startClient(serverPort: Int, message: String): String {
        val socket = Socket("127.0.0.1", serverPort)
        val sender = socket.getOutputStream()
        val receiver = socket.getInputStream()

        sender.write((message + "\r\n").toByteArray()) // отправляем запрос на сервер

        var result = ""
        val timeStart = Date().time
        var exit = false

        while (!exit) {
            if (socket.isConnected) {
                if (receiver.available() > 0) { // выходим из цикла, если сервер ответил
                    result = BufferedReader(receiver.reader()).readLine()
                    exit = true
                }
            }
            if (Date().time - timeStart > 10000) { // выходим из цикла, если сервер долго не отвечал
                result = "Error: timeout for connect or connection lost"
                exit = true
            }
        }
        try {
            socket.close()
        } catch (e: SocketException) {
            println("Error: socket was not closed")
            e.printStackTrace()
        }
        println(result)
        return result
    }
}