package seedu.us.among.logic.endpoint;

import java.io.IOException;

import org.apache.http.client.methods.HttpDelete;

import seedu.us.among.logic.endpoint.exceptions.RequestException;
import seedu.us.among.model.endpoint.Endpoint;
import seedu.us.among.model.endpoint.Response;

/**
 * Contains the logic for sending delete requests.
 */
public class DeleteRequest extends Request {

    /**
     * Constructor for DeleteRequest.
     *
     * @param endpoint endpoint to make API call on
     */
    public DeleteRequest(Endpoint endpoint) {
        super(endpoint);
    }

    /**
     * Executes the API call with a delete request.
     *
     * @return returns the response from the API call
     */
    @Override
    public Response send() throws IOException, RequestException {
        HttpDelete request = new HttpDelete(super.getAddress());
        request = (HttpDelete) super.setHeaders(request, super.getHeaders());
        return super.execute(request);
    }
}
