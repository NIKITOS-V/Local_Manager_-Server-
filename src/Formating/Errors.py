class ColorError(Exception):
    def __init__(self, message: str = "Uncorrected rgb value", *args):
        self.message: str = message

    def __str__(self):
        return self.message


class InstantError(Exception):
    def __init__(self, message: str = "This class should not be instantiated", *args):
        self.message: str = message

    def __str__(self):
        return self.message
