package day3;

public class FetchSize_2 {
    /*
    Представим что у нас есть джава преложение и сервер с базой данных. Если мы хотим считать данные
    из этого сервера то для начала мы должны установить с ним соединение. Когда соединение будет
    установлено мы начинаем выполнять наши запросы.

    Но что если данных слищком много? Например 10 милионов.
    Если приложение наннет считывать все эти данные то в скором времени оно упадет, потому что
    мы выйдем за пределы памяти.

    Чтобы от этого обезопасится используется такая штука как FetchSize.
    Мы знаем что результат выборки в себя берет ResultSet.
    Поэтому мы устанавливаем FetchSize например 10, и наш ResultSet, из этого сервера
    перетягивает себе в память 10 элементов по которым итерируется, и выбирает нужны нам.
    Как только мы прошли эти 10 элементов и выбрали те которые нам нужны, но нужно еще,
    то при вызове метода next, ResultSet удаляет текущую часть таблицы и перетягивает к себе новую.
    И в этой таюлице тоже выбирает какие-то элементы. Так будет происходить пока
    нужные нам данные не считаются в ResultSet, а потом в самом ResultSet можно итерироватся
    по уже выбраным нами данным.

    Чтобы это сделать используем метод:
    .setFetchSize() - но не стоит ставить слишком малый диапазон, и слишкой большой(так как пользователев
    может быть много, и преложение не выдержит например считывание сразу 100000 данных для 100 пользователей)

    Так же хорошие методы:
    .setQueryTimeOut() - метод с помощью которого мы указываем сколько можем ждать
    выполнение запроса, без этой настройки мы можем ждать очень долго. После окончания времени
    возвращает нам соединение обратно(При выполнении запроса соединение переходит самому запросу)

   .setMaxRows() - ограничивает нашу выборку (аналог LIMIT)
     */
}
