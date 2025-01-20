from enum import Enum

from kivy.uix.screenmanager import ScreenManager, SlideTransition, Screen


class ScreenName(str, Enum):
    main_screen = "main_screen"


class ScreenController(ScreenManager):
    def __init__(
            self,
            main_screen: Screen,
            **kwargs
    ):
        super().__init__(**kwargs)

        self.add_widget(main_screen)

        self.transition = SlideTransition(duration=0.5)

    def open_main_screen(self):
        self.current = ScreenName.main_screen
