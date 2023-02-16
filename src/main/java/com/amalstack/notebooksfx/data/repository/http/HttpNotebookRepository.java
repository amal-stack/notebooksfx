package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Notebook;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;
import com.amalstack.notebooksfx.util.http.RouteName;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;


public class HttpNotebookRepository implements NotebookRepository {
    private final HttpClientService httpClient;

    public HttpNotebookRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Collection<Notebook> findByCurrentUser() {
        var result = httpClient.get(
                Endpoint.ofName(NOTEBOOKS, USER),
                Notebook[].class);
        return List.of(result.getObjectOrThrow());
    }

    @Override
    public Notebook.Contents getContentsById(Long notebookId) {
        var e = Endpoint.ofName(RouteName.of(NOTEBOOKS, ID), notebookId);
        return httpClient
                .get(e, Notebook.Contents.class)
                .getObjectOrThrow();
    }

    @Override
    public Notebook find(Long id) {
        var endpoint = Endpoint.ofName(RouteName.of(NOTEBOOKS, ID), id);
        var result = httpClient.get(endpoint, Notebook.class);
        return result.getObjectOrThrow();
    }

    @Override
    public void add(Notebook item) {
        var endpoint = Endpoint.ofName(NOTEBOOKS);
        var result = httpClient.post(endpoint, item);
        result.throwIfFailure();
    }

    @Override
    public void update(Notebook item) {

    }

    @Override
    public void delete(Notebook item) {

    }

}

