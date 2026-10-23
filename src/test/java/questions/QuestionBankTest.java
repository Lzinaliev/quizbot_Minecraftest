package questions;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import java.util.List;

/** Тесты для банка вопросов. */
class QuestionBankTest {

    @Test
    void defaultBankIsNotEmpty() {
        QuestionBank bank = new QuestionBank();
        assertNotNull(bank);
        // банк с 질문-заглушками не пустой
        assertDoesNotThrow(() -> bank.next());
    }

    @Test
    void bankWithCustomQuestions() {
        List<Question> qs = List.of(
                new Question("2+2?", "4"),
                new Question("Столица России?", "Москва")
        );
        QuestionBank bank = new QuestionBank(qs);
        Question q1 = bank.next();
        Question q2 = bank.next();
        assertNotNull(q1);
        assertNotNull(q2);
        // после двух вопросов должен начаться новый цикл (перемешивание)
        Question q3 = bank.next();
        assertNotNull(q3);
    }

    @Test
    void emptyBankThrows() {
        assertThrows(IllegalArgumentException.class, () -> new QuestionBank(List.of()));
        assertThrows(IllegalArgumentException.class, () -> new QuestionBank(null));
    }

    @Test
    void nextReturnsQuestionFromProvidedList() {
        Question q = new Question("Тестовый вопрос?", "Тестовый ответ");
        QuestionBank bank = new QuestionBank(List.of(q));
        Question result = bank.next();
        assertEquals(q.text(), result.text());
        assertEquals(q.answer(), result.answer());
    }
}
