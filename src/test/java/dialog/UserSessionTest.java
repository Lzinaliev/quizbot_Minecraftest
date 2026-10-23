package dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import questions.Question;
import questions.QuestionBank;

/** Тесты для сессии пользователя. */
class UserSessionTest {

    private final QuestionBank bank = new QuestionBank();
    private final DialogEngine engine = new DialogEngine(bank);

    @Test
    void startReturnsGreetingAndQuestion() {
        UserSession session = new UserSession("user1", engine);
        String start = session.start();
        assertTrue(start.contains("\u041f\u0440\u0438\u0432\u0435\u0442")); // Привет
        assertTrue(start.contains("\u0412\u043e\u043f\u0440\u043e\u0441:")); // Вопрос:
    }

    @Test
    void correctAnswerIncrementsScore() {
        UserSession session = new UserSession("user2", engine);
        session.start();
        String response = session.handle("любой ответ");
        assertTrue(response.contains("\u0441\u0447\u0451\u0442:")); // счёт:
    }

    @Test
    void helpDuringSessionReturnsHelpAndPrompt() {
        UserSession session = new UserSession("user3", engine);
        session.start();
        String response = session.handle("\\help");
        assertTrue(response.contains("\u0427\u0442\u043e \u044f \u0443\u043c\u0435\u044e")); // Что я умею
        assertTrue(response.contains("\u0412\u043e\u043f\u0440\u043e\u0441:")); // Вопрос:
    }

    @Test
    void blankAnswerIsRejected() {
        UserSession session = new UserSession("user4", engine);
        session.start();
        String response = session.handle("   ");
        assertTrue(response.contains("\u041f\u0443\u0441\u0442\u043e\u0439 \u043e\u0442\u0432\u0435\u0442")); // Пустой ответ
    }

    @Test
    void userIdIsStored() {
        UserSession session = new UserSession("specific-user", engine);
        assertEquals("specific-user", session.getUserId());
    }

    @Test
    void userIdCannotBeBlank() {
        assertThrows(IllegalArgumentException.class, () -> new UserSession("", engine));
        assertThrows(IllegalArgumentException.class, () -> new UserSession("   ", engine));
        assertThrows(IllegalArgumentException.class, () -> new UserSession(null, engine));
    }
}
