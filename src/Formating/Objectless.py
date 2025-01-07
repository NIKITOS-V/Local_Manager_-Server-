from src.Formating.Errors import InstantError


class Objectless:
    def __new__(cls, *args, **kwargs):
        raise InstantError(message=f"{cls} should not be instantiated")
