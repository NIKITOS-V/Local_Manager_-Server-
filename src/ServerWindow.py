from typing import Final

from jpype import JClass
from kivy.app import App

from src.ScreenController import ScreenController
from src.ARLayout import ARLayout


class ServerWindow(App):
    def __init__(self, java_connect_driver: JClass, **kwargs):
        super().__init__(**kwargs)

        self.__java_connect_driver: Final[JClass] = java_connect_driver

    def build(self):
        screenController: ScreenController = ScreenController(
            self.__java_connect_driver
        )
        screenController.open_main_screen()

        arLayout: ARLayout = ARLayout()
        arLayout.add_widget(screenController)

        return arLayout

    def on_stop(self):
        self.__java_connect_driver.stopServer()
