package dialog;

import questions.Question;

/**
 * Сессия одного пользователя: текущий вопрос и счёт.
 * Принимает строку от пользователя и возвращает ответ бота в виде текста.
 * Ничего не знает о консоли.
 */
public class UserSession {

    private final DialogEngine engine;
    private Question current;
    private int correct;
    private int total;

    public UserSession(DialogEngine engine) {
        this.engine = engine;
    }

    /** Начало диалога: приветствие и первый вопрос. */
    public String start() {
        current = engine.nextQuestion();
        return engine.greeting() + "\n\n" + prompt(current);
    }

    /** Обрабатывает ввод пользователя и возвращает реплику бота. */
    public String handle(String input) {
        if (current == null) {
            return start();
        }
        if (engine.isHelpCommand(input)) {
            return engine.help() + "\n\n" + prompt(current);
        }
        if (engine.isCommand(input)) {
            return "Неизвестная команда. Введите " + DialogEngine.HELP_COMMAND
                    + ", чтобы узнать, что я умею.\n\n" + prompt(current);
        }
        if (input.isBlank()) {
            return "Пустой ответ. Попробуйте ещё раз.\n\n" + prompt(current);
        }

        total++;
        String verdict;
        if (engine.isCorrect(current, input)) {
            correct++;
            verdict = "Верно!";
        } else {
            verdict = "Неверно. Правильный ответ: " + current.answer();
        }

        current = engine.nextQuestion();
        return verdict + " (счёт: " + correct + "/" + total + ")\n\n" + prompt(current);
    }

    private static String prompt(Question question) {
        return "Вопрос: " + question.text();
    }
}
