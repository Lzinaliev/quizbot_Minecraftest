package dialog;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер диалогов: хранит отдельную {@link UserSession} для каждого пользователя
 * (identifying by userId) и переадресует входящие сообщения в нужную сессию.
 * Позволяет боту вести множество диалогов одновременно — ключевое требование
 * для работы с чат-платформами (Телеграм, Алиса, Вконтакте и др.).
 */
public class DialogManager {

    private final DialogEngine engine;
    private final Map<String, UserSession> sessions = new ConcurrentHashMap<>();

    public DialogManager(DialogEngine engine) {
        this.engine = engine;
    }

    /** Возвращает существующую сессию пользователя или создаёт новую. */
    public UserSession getSession(String userId) {
        return sessions.computeIfAbsent(userId, id -> new UserSession(id, engine));
    }

    /**
     * Обрабатывает входящее сообщение пользователя и возвращает ответ бота.
     *
     * @param userId идентификатор пользователя (например, chatId в Телеграме)
     * @param input  текст сообщения
     * @return ответ бота
     */
    public String handle(String userId, String input) {
        return getSession(userId).handle(input);
    }

    /** Начинает диалог с пользователем: возвращает приветствие и первый вопрос. */
    public String start(String userId) {
        return getSession(userId).start();
    }

    /** Удаляет сессию пользователя (например, при завершении диалога). */
    public void removeSession(String userId) {
        sessions.remove(userId);
    }

    /** Количество активных сессий. */
    public int activeSessionsCount() {
        return sessions.size();
    }
}
