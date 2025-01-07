from typing import Final

from jpype import JClass, java
from kivy.clock import mainthread
from kivy.lang import Builder
from kivy.properties import ListProperty, NumericProperty, StringProperty
from kivy.uix.anchorlayout import AnchorLayout
from kivy.uix.label import Label
from kivy.uix.popup import Popup
from kivy.uix.screenmanager import Screen
from kivy.uix.textinput import TextInput
from zope.interface import implementer

from src.Formating.Palette import Palette
from src.MainScreen.CommonSettings import CommonSettings
from src.Interfaces.RecipientMessages import RecipientMessages
from src.MainScreen.MSBinder import MSBinder


Builder.load_file("Resources/MainScreenView.kv")


class MessagesPanel(TextInput):
    bg_color = ListProperty([1, 1, 1, 1])
    fg_color = ListProperty([1, 1, 1, 1])

    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        self.readonly = True
        self.multiline = True

        self.bg_color = Palette.get_color(60, 60, 60, 255)
        self.fg_color = CommonSettings.text_color

    def add_text(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None:
        self.text += f"{user_name}({user_id}): {message}\n"

    def clear_panel(self) -> None:
        self.text = ""


@implementer(RecipientMessages)
class MainScreen(Screen):
    bg_color = ListProperty([1, 1, 1, 1])

    text_color = ListProperty([1, 1, 1, 1])
    text_size = NumericProperty(22)

    message_input_color = ListProperty([1, 1, 1, 1])

    button_normal_color = ListProperty([1, 1, 1, 1])
    button_active_color = ListProperty([1, 1, 1, 1])

    text_input_bg_color = ListProperty([1, 1, 1, 1])

    def __init__(self, screen_name: str, java_connect_driver: JClass, **kwargs):
        super().__init__(**kwargs)

        self.name = screen_name

        self.__msBinder: Final[MSBinder] = MSBinder(
            self, java_connect_driver
        )

        self.bg_color = CommonSettings.background_color

        self.text_color = CommonSettings.text_color
        self.message_input_color = CommonSettings.text_input_bg_color

        self.button_normal_color = CommonSettings.button_normal_color
        self.button_active_color = CommonSettings.button_active_color

        self.text_input_bg_color = CommonSettings.text_input_bg_color

    def start_server(self, port_input) -> None:
        try:
            port = int(port_input.text)

            if self.__msBinder.start_server(port):
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
        if self.__msBinder.stop_server():
            port_input.readonly = False

        else:
            self.__show_mini_window(
                "Ошибка",
                "Не удалось"
            )

    def clear_chat(self) -> None:
        self.ids.messages_panel.clear_panel()

        self.__msBinder.clear_chat()

    @mainthread
    def accept_messages(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None:
        self.ids.messages_panel.add_text(user_id, user_name, message)

    def __show_mini_window(self, title: str, text: str) -> None:
        layout = AnchorLayout()

        layout.add_widget(
            Label(
                font_size=24,
                color=CommonSettings.text_color,
                text=text
            )
        )

        Popup(
            title=title,
            content=layout,
            size_hint=(None, None),
            size=(600, 200)
        ).open()
