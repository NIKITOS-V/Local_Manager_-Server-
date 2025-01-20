from jpype import startJVM, shutdownJVM
from kivy.core.window import Window


if __name__ == "__main__":
    Window.maximize()

    resources_path: str = "Resources/LMServerBackend.jar"
    jvm_path: str = "Resources/jre1.8.0_431/bin/server/jvm.dll"

    startJVM(jvm_path, '-ea', f"-Djava.class.path={resources_path}")

    from GUI.ServerWindow import ServerWindow

    try:
        ServerWindow().run()

    except Exception as e:
        print(e)

    shutdownJVM()
