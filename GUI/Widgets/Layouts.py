from kivy.properties import NumericProperty
from kivy.uix.relativelayout import RelativeLayout


class ARLayout(RelativeLayout):
    ratio = NumericProperty(16 / 9)

    def do_layout(self, *args):
        for child in self.children:
            self.apply_ratio(child)
        super(ARLayout, self).do_layout()

    def apply_ratio(self, child) -> None:
        child.size_hint = None, None
        child.pos_hint = {"center_x": .5, "center_y": .5}

        w, h = self.size
        h2 = w * self.ratio
        if h2 > self.height:
            w = h / self.ratio
        else:
            h = h2
        child.size = w, h
