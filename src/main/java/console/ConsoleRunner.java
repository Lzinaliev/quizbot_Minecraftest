package console;

import dialog.DialogManager;
import dialog.DialogEngine;
import dialog.UserSession;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Консольный интерфейс: читает строки из stdin, передаёт их в сессию
 * и печатает ответы бота. Вся логика диалога находится в классах dialog.
 * Для консоли используется один фиксированный userId.
 */
public class ConsoleRunner {

    private static final String CONSOLE_USER_ID = "console-user";

    private final DialogManager manager;

    public ConsoleRunner(DialogManager manager) {
        this.manager = manager;
    }

    public void run() {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

        out.println(manager.start(CONSOLE_USER_ID));
        while (true) {
            out.print("> ");
            out.flush();
            if (!in.hasNextLine()) {
                // Конец ввода (Ctrl+D / Ctrl+Z) - единственный способ выйти из цикла.
                break;
            }
            out.println(manager.handle(CONSOLE_USER_ID, in.nextLine()));
        }
    }
}
