from enum import Enum

from kivy.properties import ListProperty
from kivy.uix.button import Button

from GUI.Formating.Colors import Colors


class State(str, Enum):
    normal = "normal"
    down = "down"


class CButton(Button):
    bg_normal = ListProperty(Colors.button_normal_bg_color)
    bg_down = ListProperty(Colors.button_down_bg_color)

    def __init__(self, **kwargs):
        self.background_normal = ""
        self.background_down = ""

        self.background_color = self.bg_normal

        super().__init__(**kwargs)

    def on_state(self, instance, state):
        if state == State.normal:
            self.background_color = self.bg_normal
            self.do_on_state_normal()

        else:
            self.background_color = self.bg_down
            self.do_on_state_down()

    def on_bg_normal(self, instance, color):
        if self.state == State.normal:
            self.background_color = color

    def on_bg_down(self, instance, color):
        if self.state == State.down:
            self.background_color = color

    def do_on_state_normal(self):
        pass

    def do_on_state_down(self):
        pass
