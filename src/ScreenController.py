from enum import Enum

from jpype import JClass
from kivy.uix.screenmanager import ScreenManager, SlideTransition

from src.MainScreen.MainScreen import MainScreen


class Screens(str, Enum):
    main_screen = "main_screen"


class ScreenController(ScreenManager):
    def __init__(self, java_connect_driver: JClass, **kwargs):
        super().__init__(**kwargs)

        self.add_widget(
            MainScreen(
                Screens.main_screen,
                java_connect_driver
            )
        )

        self.transition = SlideTransition(duration=0.5)

    def open_main_screen(self):
        self.current = Screens.main_screen
