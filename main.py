from jpype import startJVM, shutdownJVM, JClass
from kivy.core.window import Window


if __name__ == "__main__":
    #Window.maximize()

    resources_path: str = "Resources/Server.jar"
    jvm_path: str = "Resources/jre1.8.0_431/bin/server/jvm.dll"

    startJVM(jvm_path, '-ea', f"-Djava.class.path={resources_path}")

    from src.ServerWindow import ServerWindow

    try:
        ServerWindow(
            JClass("Server")()
        ).run()

    except Exception as e:
        print(e)

    shutdownJVM()
