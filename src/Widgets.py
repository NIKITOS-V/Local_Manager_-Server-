from kivy.uix.textinput import TextInput
from jpype import JImplements, JOverride


class TextPanel(TextInput):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)

        self.multiline = True
        self.readonly = True


