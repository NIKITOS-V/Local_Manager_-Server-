<h1 align="center">Local messenger (Client)</h1> 

* **

<p align="center">Творение моего больного воображения, моей любви к python kivy и java, а также ненависти ко swing.</p>

* **

## Короче, в чём суть... 

**Local messenger** - Примитивный мессенджер, работающий в локальной сети. Главная его задача - оправлять и принимать
сообщения пользователей. Его графический интерфейс максимально прост. Для большего он и не создавался. 
Как и написано выше, данный проект является экспериментов, в котором я объединил сильные стороны двух языков.
Этот мессенджер - ответ на вопрос: **"А что если...?"**, плод простого выражения: **"А почему бы и нет"**.

## Что вы тут найдёте
В этом репозитории храниться, по сути, два проекта. В корне находятся файлы *PyCharm*, а уже в **_src/LMClient_** - файлы
*IntelliJ IDEA*. Соответственно, *frontend* и *backend* можно (и даже нужно) открывать в разных редакторах. Зачем я так
сделал? А почему бы и нет. Каждый редактор хорошо работает со своим языком, а мучиться с установкой плагина для 
*IntelliJ* мне не хотелось.  

В файлах ресурсов (*Resources*) уже есть нужная java машина. Не знаю, хорошо ли я сделал или нет, 
что сохранил столь большой модуль, но мне удобно иметь java машину под рукой для разработки 
и создания исполняемого файла. Да, этот "Франкенштейн" нормально компилируется *pyinstaller'ом*. 

## А где клиентская часть?
<a href="https://github.com/NIKITOS-V/Local_Manager_-Client-.git">Вот тут.</a>

## Небольшой тур
**"Не лезь туда, оно тебя сожрёт!"**. Если такой смелый, то о значении директорий и некоторых файлов
читай ниже:

**Frontend**:

* **Resources** -файл .kv для главного окна, .jar архив с backend'ом.
* **src**
  * **MainScreen**
    * **CommonSettings.py** - Стандартные цветовые значения элементов граф. интерфейса.
    * **ChatScreen.py** - логика окна с чатом.
    * **CSBinder.py** - класс, связующий главное окно и backend.
  * **Formating** - вспомогательные .py файлы.
  * **ScreenController.py** - хранит окна и переключает их.
  * **ServerWindow.py** - класс приложения. Собирает граф. интерфейс и корректно завершат работу.
    (отключает пользователей от сервера, останавливает вспомогательные потоки, сохраняет историю чата).

**Backend**:  

* **Resources** - .jar для граф. интерфейса, java машина.
* **src / LMServer/ java**
  * **Interfaces** - интерфейсы для java классов и некоторых python классов.
  * **Writers** - Директория с классами для логирования и сериализации.
  * **RequestTypes** - Enum'ы, хранящие типы запросов клиента на сервер и наоборот.
  * **ClientConnectDriver** - Директория с классом, ответственным за связь с пользователем.
  * **Server.java** - Главный java класс, именно он обеспечивает связь сервера с пользователем.
