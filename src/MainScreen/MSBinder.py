from typing import Final

from jpype import JClass, JOverride, JImplements, java

from src.Interfaces.RecipientMessages import RecipientMessages


@JImplements("Interfaces.RecipientMessages")
class MSBinder:
    def __init__(self, screen: RecipientMessages, java_connect_driver: JClass):
        self.__screen: Final[RecipientMessages] = screen
        self.__java_connect_driver: Final[JClass] = java_connect_driver

        self.__java_connect_driver.setMSBinder(self)

    def stop_server(self) -> bool:
        return self.__java_connect_driver.stopServer()

    def start_server(self, port: int) -> bool:
        return self.__java_connect_driver.startServer(java.lang.Integer(port))

    def clear_chat(self) -> None:
        self.__java_connect_driver.clearChat()

    @JOverride()
    def accept_message(self, user_name: java.lang.String, user_id: int, message: java.lang.String) -> None:
        self.__screen.accept_messages(user_name, user_id, message)
