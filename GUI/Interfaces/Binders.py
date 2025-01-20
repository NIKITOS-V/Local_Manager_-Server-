from zope.interface import Interface


class MSBinder(Interface):

    def stop_server(self) -> bool: pass

    def start_server(self, port: int) -> bool: pass

    def clear_chat(self) -> None: pass
