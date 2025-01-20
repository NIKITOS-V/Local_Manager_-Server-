from jpype import JOverride, JImplements, java
from zope.interface import implementer

from GUI.Interfaces.RecipientMessages import RecipientMessages
from GUI.Interfaces.Binders import MSBinder


@JImplements("ru.NIKITOS_V.PyInterfaces.RecipientMessages")
@implementer(MSBinder)
class Binder:
    def __init__(
            self,
            screen: RecipientMessages,
            java_connect_driver
    ):
        self.__screen = screen
        self.__java_connect_driver = java_connect_driver

        self.__screen.set_binder(self)
        self.__java_connect_driver.setBinder(self)

    def stop_server(self) -> bool:
        return self.__java_connect_driver.stopServer()

    def start_server(self, port: int) -> bool:
        return self.__java_connect_driver.startServer(java.lang.Integer(port))

    def clear_chat(self) -> None:
        self.__java_connect_driver.clearChat()

    @JOverride()
    def accept_message(self, user_name: java.lang.String, user_id: int, message: java.lang.String) -> None:
        self.__screen.accept_messages(user_name, user_id, message)
