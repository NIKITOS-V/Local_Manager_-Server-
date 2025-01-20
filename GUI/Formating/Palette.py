from typing import Final

from GUI.Formating.Errors import ColorError
from GUI.Formating.Objectless import Objectless


class Palette(Objectless):
    min_shade_value: Final[int] = 0
    max_shade_value: Final[int] = 255

    @classmethod
    def __is_correct_values(cls, red: int, green: int, blue: int, alfa: int) -> bool:
        """
        Функция проверки введённых оттенков на корректность.

        :param red: *int* ∈ [0, 255]
        :param green: *int* ∈ [0, 255]
        :param blue: *int* ∈ [0, 255]
        :param alfa: *float* ∈ [0, 255]

        :return: *bool*
        """

        return (
                cls.min_shade_value <= red <= cls.max_shade_value and
                cls.min_shade_value <= green <= cls.max_shade_value and
                cls.min_shade_value <= blue <= cls.max_shade_value and
                cls.min_shade_value <= alfa <= cls.max_shade_value
        )

    @classmethod
    def get_color(cls, red: int, green: int, blue: int, alfa: int) -> list[float]:
        """
        Функция перевода цвета rgba формата в процентный вид.

        :param red: *int* ∈ [0, 255]
        :param green: *int* ∈ [0, 255]
        :param blue: *int* ∈ [0, 255]
        :param alfa: *float* ∈ [0, 1]
        :return: *list[float]*
        """

        if cls.__is_correct_values(red, blue, green, alfa):
            return [
                float(red / cls.max_shade_value),
                float(green / cls.max_shade_value),
                float(blue / cls.max_shade_value),
                float(alfa / cls.max_shade_value)
            ]

        else:
            raise ColorError()
