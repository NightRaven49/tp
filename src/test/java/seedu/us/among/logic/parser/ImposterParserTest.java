package seedu.us.among.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.us.among.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.us.among.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.us.among.testutil.Assert.assertThrows;
import static seedu.us.among.testutil.TypicalIndexes.INDEX_FIRST_ENDPOINT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

//import seedu.us.among.logic.commands.AddCommand;
import seedu.us.among.logic.commands.ClearCommand;
import seedu.us.among.logic.commands.EditCommand;
import seedu.us.among.logic.commands.ExitCommand;
import seedu.us.among.logic.commands.FindCommand;
import seedu.us.among.logic.commands.HelpCommand;
import seedu.us.among.logic.commands.ListCommand;
import seedu.us.among.logic.commands.RemoveCommand;
import seedu.us.among.logic.parser.exceptions.ParseException;
import seedu.us.among.model.endpoint.Endpoint;
import seedu.us.among.model.endpoint.NameContainsKeywordsPredicate;
import seedu.us.among.testutil.EditEndpointDescriptorBuilder;
import seedu.us.among.testutil.EndpointBuilder;
import seedu.us.among.testutil.EndpointUtil;

public class ImposterParserTest {

    private final ImposterParser parser = new ImposterParser();

    /*
    @Test
    public void parseCommand_add() throws Exception {
        Endpoint endpoint = new EndpointBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(EndpointUtil.getAddCommand(endpoint));
        assertEquals(new AddCommand(endpoint), command);
    }
    */

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_remove() throws Exception {
        RemoveCommand command = (RemoveCommand) parser.parseCommand(
                RemoveCommand.COMMAND_WORD + " " + INDEX_FIRST_ENDPOINT.getOneBased());
        assertEquals(new RemoveCommand(INDEX_FIRST_ENDPOINT), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Endpoint endpoint = new EndpointBuilder().build();
        EditCommand.EditEndpointDescriptor descriptor = new EditEndpointDescriptorBuilder(endpoint).build();
        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
                + INDEX_FIRST_ENDPOINT.getOneBased() + " " + EndpointUtil.getEditEndpointDescriptorDetails(descriptor));
        // assertEquals(new EditCommand(INDEX_FIRST_ENDPOINT, descriptor), command); //to-do
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new NameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
