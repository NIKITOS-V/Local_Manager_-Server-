from src.ServerWindow import ServerWindow
import jpype as jp


if __name__ == "__main__":
    #jar_path: str = "src\\Resources\\Server.jar"

    #jp.startJVM(jp.getDefaultJVMPath(), '-ea', f"-Djava.class.path={jar_path}")

    ServerWindow().run()

    #jp.shutdownJVM()
