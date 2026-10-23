package questions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

/** Тесты для записи вопроса. */
class QuestionTest {

    @Test
    void questionStoresTextAndAnswer() {
        Question q = new Question("Столица Франции?", "Париж");
        assertEquals("Столица Франции?", q.text());
        assertEquals("Париж", q.answer());
    }

    @Test
    void questionIsNotNull() {
        Question q = new Question("Вопрос", "Ответ");
        assertNotNull(q);
    }
}
