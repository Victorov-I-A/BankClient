fun main() {
    BankClient().startClient(9999, "put 500")
    BankClient().startClient(9999, "put 500")
    BankClient().startClient(9999, "put 800")
    BankClient().startClient(9999, "get 1700")
}