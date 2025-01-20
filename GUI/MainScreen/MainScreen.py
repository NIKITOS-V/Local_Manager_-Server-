from jpype import java
from kivy.clock import mainthread
from kivy.properties import ListProperty, NumericProperty
from kivy.uix.anchorlayout import AnchorLayout
from kivy.uix.label import Label
from kivy.uix.popup import Popup
from kivy.uix.screenmanager import Screen
from kivy.uix.textinput import TextInput
from zope.interface import implementer

from GUI.Formating.Palette import Palette
from GUI.Formating.Colors import Colors
from GUI.Interfaces.RecipientMessages import RecipientMessages
from GUI.Binder import MSBinder
from GUI.Widgets.Layouts import ARLayout
from GUI.Widgets.Buttons import CButton


class MessagesPanel(TextInput):
    bg_color = ListProperty([1, 1, 1, 1])
    fg_color = ListProperty([1, 1, 1, 1])

    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        self.readonly = True
        self.multiline = True

        self.bg_color = Palette.get_color(60, 60, 60, 255)
        self.fg_color = Colors.text_color

    def add_text(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None:
        self.text += f"{user_name}({user_id}): {message}\n"

    def clear_panel(self) -> None:
        self.text = ""


@implementer(RecipientMessages)
class MainScreen(Screen):
    text_color = ListProperty(Colors.text_color)
    text_size = NumericProperty(22)

    message_input_color = ListProperty(Colors.text_input_bg_color)

    button_normal_color = ListProperty(Colors.button_normal_bg_color)
    button_active_color = ListProperty(Colors.button_down_bg_color)

    text_input_bg_color = ListProperty(Colors.text_input_bg_color)

    def __init__(self, screen_name: str, **kwargs):
        super().__init__(**kwargs)

        self.name = screen_name

        self.__binder = None

    def set_binder(self, binder: MSBinder) -> None:
        self.__binder = binder

    def start_server(self, port_input) -> None:
        try:
            port = int(port_input.text)

            if self.__binder.start_server(port):
                port_input.readonly = True

            else:
                self.__show_mini_window(
                    "Ошибка",
                    "Не удалось"
                )

        except ValueError:
            self.__show_mini_window(
                "Ошибка",
                "Некорректный порт"
            )

    def stop_server(self, port_input) -> None:
        if self.__binder.stop_server():
            port_input.readonly = False

        else:
            self.__show_mini_window(
                "Ошибка",
                "Не удалось"
            )

    def clear_chat(self) -> None:
        self.ids.messages_panel.clear_panel()

        self.__binder.clear_chat()

    @mainthread
    def accept_messages(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None:
        self.ids.messages_panel.add_text(user_id, user_name, message)

    def __show_mini_window(self, title: str, text: str) -> None:
        layout = AnchorLayout()

        layout.add_widget(
            Label(
                font_size=24,
                color=Colors.text_color,
                text=text
            )
        )

        Popup(
            title=title,
            content=layout,
            size_hint=(None, None),
            size=(600, 200)
        ).open()
