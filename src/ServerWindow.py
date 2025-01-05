from kivy.app import App
from kivy.uix.boxlayout import BoxLayout

from src.Widgets import TextPanel


class ServerWindow(App):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

    def build(self):
        boxLayout = BoxLayout()

        boxLayout.add_widget(TextPanel())

        return boxLayout


