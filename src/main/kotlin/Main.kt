fun main() {
    BankClient().startClient(9999, "8888-8888-8888-8888 1234 put 1000")
    BankClient().startClient(9999, "7777-7777-7777-7777 1234 get 1000")
    BankClient().startClient(9999, "6666-6666-6666-6666 1234 check")
}