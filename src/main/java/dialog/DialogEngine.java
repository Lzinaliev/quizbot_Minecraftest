package dialog;

import questions.Question;
import questions.QuestionBank;

import java.util.Locale;

/**
 * Логика диалога: тексты бота, распознавание команд, проверка ответов.
 * Зависит от банка вопросов, но ничего не знает о консоли.
 */
public class DialogEngine {

    public static final String HELP_COMMAND = "\\help";

    private final QuestionBank bank;

    public DialogEngine(QuestionBank bank) {
        this.bank = bank;
    }

    /** Приветствие при старте диалога. */
    public String greeting() {
        return "Привет! Я QuizBot - бот-викторина.\n" + help();
    }

    /** Рассказ о том, что умеет бот и как с ним взаимодействовать. */
    public String help() {
        return "Что я умею: задаю вопросы один за другим и проверяю ваши ответы.\n"
                + "Как со мной общаться:\n"
                + "  - я задаю вопрос, вы вводите ответ и нажимаете Enter;\n"
                + "  - я говорю, правильно ли вы ответили, и сразу задаю следующий вопрос;\n"
                + "  - регистр букв и лишние пробелы не важны;\n"
                + "  - команда " + HELP_COMMAND + " - показать эту справку ещё раз.";
    }

    public Question nextQuestion() {
        return bank.next();
    }

    public boolean isHelpCommand(String input) {
        return HELP_COMMAND.equals(input.trim().toLowerCase(Locale.ROOT));
    }

    /** Строка похожа на команду, если начинается с обратной косой черты. */
    public boolean isCommand(String input) {
        return input.trim().startsWith("\\");
    }

    public boolean isCorrect(Question question, String input) {
        return normalize(question.answer()).equals(normalize(input));
    }

    private static String normalize(String s) {
        return s.trim().replaceAll("\\s+", " ").toLowerCase(Locale.ROOT).replace('\u0451', '\u0435');
    }
}
