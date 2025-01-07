from zope.interface import Interface
from jpype import java


class RecipientMessages(Interface):
    def accept_messages(self, user_id: int, user_name: java.lang.String, message: java.lang.String) -> None: pass
