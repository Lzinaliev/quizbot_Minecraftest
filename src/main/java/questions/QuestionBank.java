package questions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Банк вопросов. Отдаёт вопросы в случайном порядке, не повторяя их,
 * пока не будут заданы все; затем перемешивает заново.
 *
 * Не зависит от классов диалога и консоли.
 */
public class QuestionBank {

    private final List<Question> questions;
    private final List<Question> queue = new ArrayList<>();
    private final Random random = new Random();

    public QuestionBank() {
        this(defaultQuestions());
    }

    public QuestionBank(List<Question> questions) {
        if (questions == null || questions.isEmpty()) {
            throw new IllegalArgumentException("Банк вопросов не может быть пустым");
        }
        this.questions = List.copyOf(questions);
    }

    /** Возвращает следующий вопрос. */
    public Question next() {
        if (queue.isEmpty()) {
            queue.addAll(questions);
            Collections.shuffle(queue, random);
        }
        return queue.remove(queue.size() - 1);
    }

    /** Временные вопросы-заглушки. Замените их, когда выберете тему. */
    private static List<Question> defaultQuestions() {
        return List.of(
                new Question("Столица Франции?", "Париж"),
                new Question("Сколько будет 7 * 8?", "56"),
                new Question("Какая планета Солнечной системы самая большая?", "Юпитер"),
                new Question("Сколько дней в неделе?", "7"),
                new Question("Какая химическая формула воды?", "H2O")
        );
    }
}
