from typing import Final

from GUI.Formating.Objectless import Objectless
from GUI.Formating.Palette import Palette


class Colors(Objectless):
    bg_color: Final[list[float]] = Palette.get_color(10, 10, 10, 255)

    button_down_bg_color: Final[list[float]] = Palette.get_color(20, 20, 20, 255)
    button_normal_bg_color: Final[list[float]] = Palette.get_color(30, 30, 30, 255)

    text_input_bg_color: Final[list[float]] = Palette.get_color(55, 55, 55, 255)
    text_color: Final[list[float]] = Palette.get_color(255, 255, 255, 255)

