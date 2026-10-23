# QuizBot

Консольный чат-бот викторина. При старте бот рассказывает, что он умеет и как с ним
общаться, а затем в бесконечном цикле задаёт вопрос, получает ответ, оценивает его
и задаёт следующий вопрос. Команда `\help` в любой момент повторяет справку.

## Структура проекта

```
quizbot/
├── .mvn/                         # Maven Wrapper
├── src/main/java/
│   ├── console/ConsoleRunner.java   # Ввод-вывод через консоль
│   ├── dialog/
│   │   ├── DialogEngine.java        # Логика диалога: тексты, команды, проверка ответов
│   │   ├── DialogManager.java       # Менеджер множественных диалогов (по userId)
│   │   └── UserSession.java         # Сессия пользователя: вопрос, счёт, обработка ввода
│   ├── questions/
│   │   ├── Question.java            # Вопрос и ответ (record)
│   │   └── QuestionBank.java        # Банк (генератор) вопросов
│   └── Main.java                    # Точка входа, сборка компонентов
├── src/test/java/                   # Модульные тесты (JUnit 5)
│   ├── questions/QuestionTest.java
│   ├── questions/QuestionBankTest.java
│   ├── dialog/DialogEngineTest.java
│   ├── dialog/UserSessionTest.java
│   └── dialog/DialogManagerTest.java
├── .gitignore
├── pom.xml
└── README.md
```

## Архитектура и требования регламента (ООП, 2026)

Проект следует правилам разделения ответственности из регламента практик:

| Слой | Отвечает за | Не зависит от |
|------|-------------|---------------|
| `questions` | Хранение и выдача вопросов | `dialog`, `console` |
| `dialog` | Логика диалога, приветствие, проверка ответов, управление сессиями | `console` |
| `console` | Ввод-вывод через stdin/stdout | — |

- **QuestionBank** не зависит от классов диалога и консоли (требование регламента п.1).
- **DialogEngine / UserSession / DialogManager** не зависят от классов консоли (требование п.2).
- **DialogManager** обеспечивает поддержку множества одновременных диалогов с разными пользователями (требование для задач со второй и выше, заложено с первой).
- Архитектура подходит для чат-платформ (Телеграм, Алиса, ВК): метод `DialogManager.handle(userId, input)` принимает одну реплику пользователя и возвращает одну реплику бота — без блокирующего чтения из консоли.

## Запуск

Нужны JDK 17+ и Maven.

```
mvn compile exec:java
```

или собрать и запустить jar:

```
mvn package
java -jar target/quizbot-1.0-SNAPSHOT.jar
```

Если в Windows-консоли русский текст отображается некорректно, выполните `chcp 65001`.

## Тесты

Проект покрыт модульными тестами на JUnit 5 (требование регламента).

```
mvn test
```

Запуск вручную (без Maven):

```bash
javac -encoding UTF-8 -cp target/classes;<path-to-junit-jar> -d target/test-classes -sourcepath src/test/java src/test/java/**/*.java
java -cp target/classes;target/test-classes;<path-to-junit-jar> org.junit.platform.console.ConsoleLauncher --select-class questions.QuestionTest --select-class questions.QuestionBankTest --select-class dialog.DialogEngineTest --select-class dialog.UserSessionTest --select-class dialog.DialogManagerTest
```

## Команды

| Команда | Действие |
|---------|----------|
| `\help` | Показать справку о боте |
| любой другой текст | Ответ на текущий вопрос |
| пустой ввод | Предупреждение, вопрос не меняется |

Выход: Ctrl+C (или Ctrl+D / Ctrl+Z в конце ввода).

## Как сменить тему вопросов

Сейчас в `QuestionBank` лежат вопросы-заглушки. Замените их в методе
`defaultQuestions()` или передайте свой список в конструктор
`new QuestionBank(List<Question>)` в `Main`.

## Статус сдачи (регламент)

- Каждая задача оценивается в 2 балла (хорошо), 1 балл (есть проблемы), 0 (не готово).
- Не более 3 попыток на сдачу одной задачи.
- Доп-баллы: качество git (+2), качество unit-тестов (+2), бота для Телеграм/Алисы (+2), особые возможности платформы (+2), техническая сложность (+2), деплой (+1), внешний API (+1).
- Для допуска к зачёту достаточно 10 баллов.
