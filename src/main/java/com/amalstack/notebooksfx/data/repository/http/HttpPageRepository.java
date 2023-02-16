package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Page;
import com.amalstack.notebooksfx.data.repository.PageRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;
import com.amalstack.notebooksfx.util.http.RouteName;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;

public class HttpPageRepository implements PageRepository {

    private final HttpClientService httpClient;

    public HttpPageRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Collection<Page> findByNotebookId(Long notebookId) {
        return null;
    }

    @Override
    public Collection<Page> findBySectionId(Long sectionId) {
        Endpoint endpoint = Endpoint.ofName(RouteName.of(PAGES, SECTION, ID), sectionId);
        return List.of(httpClient.get(endpoint, Page[].class).getObjectOrThrow());
    }

    @Override
    public int countByNotebookId(Long notebookId) {
        return 0;
    }

    @Override
    public int countBySectionId(Long sectionId) {
        return 0;
    }

    @Override
    public Page find(Long id) {
        return null;
    }

    @Override
    public void add(Page item) {

    }

    @Override
    public void update(Page item) {

    }

    @Override
    public void delete(Page item) {

    }
}
