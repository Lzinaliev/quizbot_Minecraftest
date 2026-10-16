package console;

import dialog.UserSession;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Консольный интерфейс: читает строки из stdin, передаёт их в сессию
 * и печатает ответы бота. Вся логика диалога находится в классах dialog.
 */
public class ConsoleRunner {

    private final UserSession session;

    public ConsoleRunner(UserSession session) {
        this.session = session;
    }

    public void run() {
        PrintStream out = new PrintStream(System.out, true, StandardCharsets.UTF_8);
        Scanner in = new Scanner(System.in, StandardCharsets.UTF_8);

        out.println(session.start());
        while (true) {
            out.print("> ");
            out.flush();
            if (!in.hasNextLine()) {
                // Конец ввода (Ctrl+D / Ctrl+Z) - единственный способ выйти из цикла.
                break;
            }
            out.println(session.handle(in.nextLine()));
        }
    }
}
