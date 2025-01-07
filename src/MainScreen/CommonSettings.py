from typing import Final

from src.Formating.Objectless import Objectless
from src.Formating.Palette import Palette


class CommonSettings(Objectless):
    background_color: Final[list[float]] = Palette.get_color(10, 10, 10, 255)

    text_input_bg_color: Final[list[float]] = Palette.get_color(40, 40, 40, 255)
    text_color: Final[list[float]] = Palette.get_color(255, 255, 255, 255)

    button_active_color: Final[list[float]] = Palette.get_color(20, 20, 20, 255)
    button_normal_color: Final[list[float]] = Palette.get_color(30, 30, 30, 255)
