from zope.interface import Interface
from jpype import java

from GUI.Interfaces.Binders import MSBinder


class RecipientMessages(Interface):
    def accept_messages(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None: pass

    def set_binder(self, binder: MSBinder) -> None: pass
