from jpype import JClass
from kivy.app import App
from kivy.lang import Builder
from kivy.properties import ListProperty
from kivy.uix.relativelayout import RelativeLayout

from GUI.Binder import Binder
from GUI.Formating.Colors import Colors
from GUI.MainScreen.MainScreen import MainScreen
from GUI.ScreenController import ScreenController, ScreenName


Builder.load_file("Resources/KV/BackgroundView.kv")
Builder.load_file("Resources/KV/MainScreenView.kv")


class Background(RelativeLayout):
    bg_color = ListProperty(Colors.bg_color)


class ServerWindow(App):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        self.__binder = None

    def build(self):
        main_screen = MainScreen(
            ScreenName.main_screen
        )

        self.__binder = Binder(
            main_screen,
            JClass("ru.NIKITOS_V.Server")()
        )

        screen_controller = ScreenController(
            main_screen
        )
        screen_controller.open_main_screen()

        background = Background()

        background.add_widget(
            screen_controller
        )

        return background

    def on_stop(self):
        self.__binder.stop_server()
