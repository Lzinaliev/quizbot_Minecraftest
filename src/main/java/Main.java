import console.ConsoleRunner;
import dialog.DialogEngine;
import dialog.UserSession;
import questions.QuestionBank;

/**
 * Точка входа: собирает компоненты вместе и запускает консольный чат.
 */
public class Main {

    public static void main(String[] args) {
        QuestionBank bank = new QuestionBank();
        DialogEngine engine = new DialogEngine(bank);
        UserSession session = new UserSession(engine);
        new ConsoleRunner(session).run();
    }
}
