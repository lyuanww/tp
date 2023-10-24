package seedu.cc.logic.parser;

import static seedu.cc.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.cc.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.cc.commons.core.LogsCenter;
import seedu.cc.logic.commands.AddCommand;
import seedu.cc.logic.commands.ClearCommand;
import seedu.cc.logic.commands.Command;
import seedu.cc.logic.commands.DeleteCommand;
import seedu.cc.logic.commands.EditCommand;
import seedu.cc.logic.commands.ExitCommand;
import seedu.cc.logic.commands.FindCommand;
import seedu.cc.logic.commands.HelpCommand;
import seedu.cc.logic.commands.ListCommand;
import seedu.cc.logic.commands.medhisteventcommands.AddMedicalHistoryEventCommand;
import seedu.cc.logic.commands.medhisteventcommands.DeleteMedicalHistoryEventCommand;
import seedu.cc.logic.commands.medhisteventcommands.EditMedicalHistoryEventCommand;
import seedu.cc.logic.commands.medhisteventcommands.ListMedicalHistoryEventCommand;
import seedu.cc.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class ClinicBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(ClinicBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case AddMedicalHistoryEventCommand.COMMAND_WORD:
            return new AddMedicalHistoryEventParser().parse(arguments);

        case ListMedicalHistoryEventCommand.COMMAND_WORD:
            return new ListMedicalHistoryEventsCommandParser().parse(arguments);

        case EditMedicalHistoryEventCommand.COMMAND_WORD:
            return new EditMedicalHistoryEventCommandParser().parse(arguments);

        case DeleteMedicalHistoryEventCommand.COMMAND_WORD:
            return new DeleteMedicalHistoryEventCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
