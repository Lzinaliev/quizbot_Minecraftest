package dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import questions.QuestionBank;

/** Тесты для менеджера диалогов (поддержка множества пользователей). */
class DialogManagerTest {

    private final QuestionBank bank = new QuestionBank();
    private final DialogEngine engine = new DialogEngine(bank);
    private final DialogManager manager = new DialogManager(engine);

    @Test
    void getSessionCreatesSessionForUser() {
        UserSession session = manager.getSession("alice");
        assertNotNull(session);
        assertEquals("alice", session.getUserId());
    }

    @Test
    void sameUserIdReturnsSameSession() {
        UserSession s1 = manager.getSession("bob");
        UserSession s2 = manager.getSession("bob");
        assertEquals(s1, s2);
    }

    @Test
    void differentUsersGetDifferentSessions() {
        UserSession s1 = manager.getSession("alice");
        UserSession s2 = manager.getSession("bob");
        assertEquals("alice", s1.getUserId());
        assertEquals("bob", s2.getUserId());
    }

    @Test
    void handleReturnsResponse() {
        String response = manager.handle("user1", "\\help");
        assertTrue(response.contains("\u0427\u0442\u043e \u044f \u0443\u043c\u0435\u044e")); // Что я умею
    }

    @Test
    void startReturnsGreeting() {
        String response = manager.start("user2");
        assertTrue(response.contains("\u041f\u0440\u0438\u0432\u0435\u0442")); // Привет
    }

    @Test
    void activeSessionsCount() {
        manager.getSession("u1");
        manager.getSession("u2");
        assertEquals(2, manager.activeSessionsCount());
    }

    @Test
    void removeSessionDecreasesCount() {
        manager.getSession("u1");
        manager.removeSession("u1");
        assertEquals(0, manager.activeSessionsCount());
    }
}
