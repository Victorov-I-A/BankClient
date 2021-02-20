import org.junit.Assert.assertTrue
import org.junit.Test


class BankTest {  //перед каждым тестом выставить 0 в базе данных и запустить сервер

    @Test
    fun oneThreadTest() {
        //правильная работа
        assertTrue(BankClient().startClient(9999, "put 500") == "Success of the operation")
        assertTrue(BankClient().startClient(9999, "get 500") == "Success of the operation")

        //проверка на количество аргументов
        assertTrue(BankClient().startClient(9999, "put") == "Error: arguments are not enough")
        assertTrue(BankClient().startClient(9999, "put 100 100") == "Error: too many arguments")

        //проверка на наличие депозита
        assertTrue(BankClient().startClient(9999, "put 543bh") == "Error: deposit is not found")
        assertTrue(BankClient().startClient(9999, "put -5478") == "Error: deposit is not found")

        //проверка на наличие команды
        assertTrue(BankClient().startClient(9999, "cash 100") == "Error: command is not found")
        assertTrue(BankClient().startClient(9999, "close 100") == "Error: command is not found")

        //проверка размера счёта
        assertTrue(BankClient().startClient(9999, "put 200000") == "Error: too mach money on account")
        assertTrue(BankClient().startClient(9999, "get 10") == "Error: not enough money on account")

        //проверка закрытия сервера
        assertTrue(BankClient().startClient(9999, "close") == "Server is closed")
    }

    @Test
    fun twoThreadTest() { //тест синхронизированного доступа к ячейке
        Thread {
            for (i in 0 until 5) {
                BankClient().startClient(9999, "put 10")
            }
        }.start()

        for (i in 0 until 5) {
            assertTrue(
                BankClient().startClient(9999, "get 5") == "Success of the operation"
            )
        }

        assertTrue(BankClient().startClient(9999, "get 25") == "Success of the operation")

        assertTrue(BankClient().startClient(9999, "close") == "Server is closed")
    }
}