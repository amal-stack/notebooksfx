package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Section;
import com.amalstack.notebooksfx.data.repository.SectionRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;
import com.amalstack.notebooksfx.util.http.RouteName;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;

public class HttpSectionRepository implements SectionRepository {

    private final HttpClientService httpClient;

    public HttpSectionRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Section find(Long id) {
        return null;
    }

    @Override
    public void add(Section item) {

    }

    @Override
    public void update(Section item) {

    }

    @Override
    public void delete(Section item) {

    }

    @Override
    public Collection<Section> findByNotebookId(Long notebookId) {
        var endpoint = Endpoint.ofName(RouteName.of(SECTIONS, NOTEBOOK, ID), notebookId);
        return List.of(httpClient.get(endpoint, Section[].class).getObjectOrThrow());
    }

    @Override
    public int countByNotebookId(Long notebookId) {
        return 0;
    }
}
