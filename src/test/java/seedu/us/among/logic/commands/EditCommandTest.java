package seedu.us.among.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.us.among.logic.commands.CommandTestUtil.DESC_GET;
import static seedu.us.among.logic.commands.CommandTestUtil.DESC_POST;
import static seedu.us.among.logic.commands.CommandTestUtil.VALID_METHOD_POST;
import static seedu.us.among.logic.commands.CommandTestUtil.VALID_TAG_CAT;
import static seedu.us.among.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.us.among.logic.commands.CommandTestUtil.assertCommandSuccess;
// import static seedu.us.among.logic.commands.CommandTestUtil.showEndpointAtIndex;
//to-do to fix this class, fix showEndpointAtIndex
import static seedu.us.among.testutil.TypicalEndpoints.getTypicalEndpointList;
import static seedu.us.among.testutil.TypicalIndexes.INDEX_FIRST_ENDPOINT;
import static seedu.us.among.testutil.TypicalIndexes.INDEX_SECOND_ENDPOINT;

import org.junit.jupiter.api.Test;

import seedu.us.among.commons.core.Messages;
import seedu.us.among.commons.core.index.Index;
import seedu.us.among.logic.commands.EditCommand.EditEndpointDescriptor;
import seedu.us.among.model.EndpointList;
import seedu.us.among.model.Model;
import seedu.us.among.model.ModelManager;
import seedu.us.among.model.UserPrefs;
import seedu.us.among.model.endpoint.Endpoint;
import seedu.us.among.testutil.EditEndpointDescriptorBuilder;
import seedu.us.among.testutil.EndpointBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalEndpointList(), new UserPrefs());

    /*
    //to-do fix test cases in this file

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Endpoint editedEndpoint = new EndpointBuilder().build();
        EditEndpointDescriptor descriptor = new EditEndpointDescriptorBuilder(editedEndpoint).build();
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENDPOINT, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENDPOINT_SUCCESS, editedEndpoint);

        Model expectedModel = new ModelManager(new EndpointList(model.getEndpointList()), new UserPrefs());
        expectedModel.setEndpoint(model.getFilteredEndpointList().get(0), editedEndpoint);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }
    */

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastEndpoint = Index.fromOneBased(model.getFilteredEndpointList().size());
        Endpoint lastEndpoint = model.getFilteredEndpointList().get(indexLastEndpoint.getZeroBased());

        EndpointBuilder endpointInList = new EndpointBuilder(lastEndpoint);
        Endpoint editedEndpoint = endpointInList.withMethod(VALID_METHOD_POST).withTags(VALID_TAG_CAT).build();

        EditEndpointDescriptor descriptor = new EditEndpointDescriptorBuilder().withName(VALID_METHOD_POST)
                .withTags(VALID_TAG_CAT).build();
        EditCommand editCommand = new EditCommand(indexLastEndpoint, descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENDPOINT_SUCCESS, editedEndpoint);

        Model expectedModel = new ModelManager(new EndpointList(model.getEndpointList()), new UserPrefs());
        expectedModel.setEndpoint(lastEndpoint, editedEndpoint);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(INDEX_FIRST_ENDPOINT, new EditEndpointDescriptor());
        Endpoint editedEndpoint = model.getFilteredEndpointList().get(INDEX_FIRST_ENDPOINT.getZeroBased());

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENDPOINT_SUCCESS, editedEndpoint);

        Model expectedModel = new ModelManager(new EndpointList(model.getEndpointList()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    // @Test
    // public void execute_filteredList_success() {
    //     showEndpointAtIndex(model, INDEX_FIRST_ENDPOINT); //to-do to fix this test, fix showEndpointAtIndex

    //     Endpoint endpointInFilteredList = model.getFilteredEndpointList().get(INDEX_FIRST_ENDPOINT.getZeroBased());
    //     Endpoint editedEndpoint = new EndpointBuilder(endpointInFilteredList).withMethod(VALID_METHOD_POST).build();
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_ENDPOINT,
    //             new EditEndpointDescriptorBuilder().withName(VALID_METHOD_POST).build());

    //     String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_ENDPOINT_SUCCESS, editedEndpoint);

    //     Model expectedModel = new ModelManager(new EndpointList(model.getEndpointList()), new UserPrefs());
    //     expectedModel.setEndpoint(model.getFilteredEndpointList().get(0), editedEndpoint);

    //     assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    // }

    @Test
    public void execute_duplicateEndpointUnfilteredList_failure() {
        Endpoint firstEndpoint = model.getFilteredEndpointList().get(INDEX_FIRST_ENDPOINT.getZeroBased());
        EditEndpointDescriptor descriptor = new EditEndpointDescriptorBuilder(firstEndpoint).build();
        EditCommand editCommand = new EditCommand(INDEX_SECOND_ENDPOINT, descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENDPOINT);
    }

    // @Test
    // public void execute_duplicateEndpointFilteredList_failure() {
    //     showEndpointAtIndex(model, INDEX_FIRST_ENDPOINT); //to-do to fix this test, fix showEndpointAtIndex
    //     // edit endpoint in filtered list into a duplicate in the API endpoint list
    //     Endpoint endpointInList = model.getEndpointList().getEndpointList()
    //              .get(INDEX_SECOND_ENDPOINT.getZeroBased());
    //     EditCommand editCommand = new EditCommand(INDEX_FIRST_ENDPOINT,
    //             new EditEndpointDescriptorBuilder(endpointInList).build());
    //     assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_ENDPOINT);
    // }

    @Test
    public void execute_invalidEndpointIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEndpointList().size() + 1);
        EditEndpointDescriptor descriptor = new EditEndpointDescriptorBuilder().withName(VALID_METHOD_POST).build();
        EditCommand editCommand = new EditCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENDPOINT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list, but
     * smaller than size of the API endpoint list
     */
    // @Test
    // public void execute_invalidEndpointIndexFilteredList_failure() {
    //     showEndpointAtIndex(model, INDEX_FIRST_ENDPOINT); //to-do to fix this test, fix showEndpointAtIndex
    //     Index outOfBoundIndex = INDEX_SECOND_ENDPOINT;
    //     // ensures that outOfBoundIndex is still in bounds of API endpoint list
    //     assertTrue(outOfBoundIndex.getZeroBased() < model.getEndpointList().getEndpointList().size());

    //     EditCommand editCommand = new EditCommand(outOfBoundIndex,
    //             new EditEndpointDescriptorBuilder().withName(VALID_METHOD_POST).build());

    //     assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_ENDPOINT_DISPLAYED_INDEX);
    // }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_ENDPOINT, DESC_GET);

        // same values -> returns true
        EditEndpointDescriptor copyDescriptor = new EditEndpointDescriptor(DESC_GET);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_ENDPOINT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_ENDPOINT, DESC_GET)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_ENDPOINT, DESC_POST)));
    }

}
