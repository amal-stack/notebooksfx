package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Notebook;
import com.amalstack.notebooksfx.data.model.NotebookContents;
import com.amalstack.notebooksfx.data.model.NotebookInput;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;


public class HttpNotebookRepository implements NotebookRepository {
    private final HttpClientService httpClient;

    public HttpNotebookRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Notebook findById(Long notebookId) {
        var endpoint = Endpoint.named(NOTEBOOKS, ID)
                .pathParameters(notebookId);

        return httpClient
                .get(endpoint, Notebook.class)
                .getObjectOrThrow();
    }

    @Override
    public Collection<Notebook> findByCurrentUser() {
        var result = httpClient.get(
                Endpoint.named(NOTEBOOKS, USER),
                Notebook[].class);

        return List.of(result.getObjectOrThrow());
    }

    @Override
    public NotebookContents getContentsById(Long notebookId) {
        var e = Endpoint.named(NOTEBOOKS, ID)
                .pathParameters(notebookId);

        return httpClient
                .get(e, NotebookContents.class)
                .getObjectOrThrow();
    }

    @Override
    public Notebook create(NotebookInput notebookInput) {
        var endpoint = Endpoint.named(NOTEBOOKS);
        return httpClient.send(endpoint, "POST", notebookInput, Notebook.class)
                .getObjectOrThrow();
    }

    @Override
    public void update(long notebookId, NotebookInput notebookInput) {
        var endpoint = Endpoint.named(NOTEBOOKS, ID)
                .pathParameters(notebookId);

        httpClient.put(endpoint, notebookInput)
                .getObjectOrThrow();
    }

    @Override
    public void rename(long notebookId, String newName) {
        var endpoint = Endpoint.named(NOTEBOOKS, ID)
                .pathParameters(notebookId);

        Notebook notebook = httpClient.get(endpoint, Notebook.class)
                .getObjectOrThrow();

        if (notebook.name().equals(newName)) {
            return;
        }

        NotebookInput notebookInput = new NotebookInput(newName,
                notebook.description());

        httpClient.put(endpoint, notebookInput)
                .throwIfFailure();
    }

    @Override
    public void delete(Long notebookId) {
        var endpoint = Endpoint.named(NOTEBOOKS, ID)
                .pathParameters(notebookId);

        httpClient.delete(endpoint)
                .throwIfFailure();
    }
}