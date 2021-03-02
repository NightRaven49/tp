package seedu.us.among.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.us.among.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.us.among.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.us.among.testutil.Assert.assertThrows;
import static seedu.us.among.testutil.TypicalEndpoints.ALICE;
import static seedu.us.among.testutil.TypicalEndpoints.getTypicalEndpointList;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.us.among.model.endpoint.Endpoint;
import seedu.us.among.model.endpoint.exceptions.DuplicateApiEndpointException;
import seedu.us.among.testutil.EndpointBuilder;

public class EndpointListTest {

    private final EndpointList endpointList = new EndpointList();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), endpointList.getEndpointList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> endpointList.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyEndpointList_replacesData() {
        EndpointList newData = getTypicalEndpointList();
        endpointList.resetData(newData);
        assertEquals(newData, endpointList);
    }

    @Test
    public void resetData_withDuplicateEndpoints_throwsDuplicateEndpointException() {
        // Two endpoints with the same identity fields
        Endpoint editedAlice = new EndpointBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        List<Endpoint> newEndpoints = Arrays.asList(ALICE, editedAlice);
        EndpointListStub newData = new EndpointListStub(newEndpoints);

        assertThrows(DuplicateApiEndpointException.class, () -> endpointList.resetData(newData));
    }

    @Test
    public void hasEndpoint_nullEndpoint_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> endpointList.hasEndpoint(null));
    }

    @Test
    public void hasEndpoint_endpointNotInEndpointList_returnsFalse() {
        assertFalse(endpointList.hasEndpoint(ALICE));
    }

    @Test
    public void hasEndpoint_endpointInEndpointList_returnsTrue() {
        endpointList.addEndpoint(ALICE);
        assertTrue(endpointList.hasEndpoint(ALICE));
    }

    @Test
    public void hasEndpoint_endpointWithSameIdentityFieldsInEndpointList_returnsTrue() {
        endpointList.addEndpoint(ALICE);
        Endpoint editedAlice = new EndpointBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(endpointList.hasEndpoint(editedAlice));
    }

    @Test
    public void getEndpointList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> endpointList.getEndpointList().remove(0));
    }

    /**
     * A stub ReadOnlyEndpointList whose endpoints list can violate interface constraints.
     */
    private static class EndpointListStub implements ReadOnlyEndpointList {
        private final ObservableList<Endpoint> endpoints = FXCollections.observableArrayList();

        EndpointListStub(Collection<Endpoint> endpoints) {
            this.endpoints.setAll(endpoints);
        }

        @Override
        public ObservableList<Endpoint> getEndpointList() {
            return endpoints;
        }
    }

}