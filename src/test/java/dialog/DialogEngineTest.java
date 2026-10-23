package dialog;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import questions.Question;
import questions.QuestionBank;

/** Тесты для логики диалога. */
class DialogEngineTest {

    private final QuestionBank bank = new QuestionBank();
    private final DialogEngine engine = new DialogEngine(bank);

    @Test
    void greetingContainsHelp() {
        String greeting = engine.greeting();
        assertTrue(greeting.contains(DialogEngine.HELP_COMMAND));
    }

    @Test
    void helpContainsCommand() {
        String help = engine.help();
        assertTrue(help.contains(DialogEngine.HELP_COMMAND));
    }

    @Test
    void isHelpCommandRecognizesHelp() {
        assertTrue(engine.isHelpCommand("\\help"));
        assertTrue(engine.isHelpCommand("  \\HELP  "));
        assertTrue(engine.isHelpCommand("\\Help"));
    }

    @Test
    void isHelpCommandRejectsOther() {
        assertFalse(engine.isHelpCommand("ответ"));
        assertFalse(engine.isHelpCommand(""));
        assertFalse(engine.isHelpCommand("\\unknown"));
    }

    @Test
    void isCommandRecognizesBackslash() {
        assertTrue(engine.isCommand("\\help"));
        assertTrue(engine.isCommand("\\anything"));
        assertFalse(engine.isCommand("普通文字"));
        assertFalse(engine.isCommand(""));
    }

    @Test
    void isCorrectIgnoresCaseAndSpaces() {
        Question q = new Question("Столица?", "Париж");
        assertTrue(engine.isCorrect(q, "Париж"));
        assertTrue(engine.isCorrect(q, "  париж  "));
        assertTrue(engine.isCorrect(q, "ПАРИЖ"));
        assertTrue(engine.isCorrect(q, "париж"));
        assertFalse(engine.isCorrect(q, "Лондон"));
    }

    @Test
    void isCorrectHandlesYo() {
        Question q = new Question("Ёлка?", "Ёлка");
        assertTrue(engine.isCorrect(q, "елка"));
        assertTrue(engine.isCorrect(q, "Ёлка"));
    }

    @Test
    void nextQuestionReturnsFromBank() {
        Question q = engine.nextQuestion();
        assertNotNull(q);
    }
}
