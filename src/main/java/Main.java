import console.ConsoleRunner;
import dialog.DialogEngine;
import dialog.DialogManager;
import questions.QuestionBank;

/**
 * Точка входа: собирает компоненты вместе и запускает консольный чат.
 */
public class Main {

    public static void main(String[] args) {
        QuestionBank bank = new QuestionBank();
        DialogEngine engine = new DialogEngine(bank);
        DialogManager manager = new DialogManager(engine);
        new ConsoleRunner(manager).run();
    }
}
