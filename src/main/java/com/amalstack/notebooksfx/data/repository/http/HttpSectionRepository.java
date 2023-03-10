package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Section;
import com.amalstack.notebooksfx.data.model.SectionInput;
import com.amalstack.notebooksfx.data.repository.SectionRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;

public class HttpSectionRepository implements SectionRepository {

    private final HttpClientService httpClient;

    public HttpSectionRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }


    @Override
    public Collection<Section> findByNotebookId(Long notebookId) {
        var endpoint = Endpoint.named(SECTIONS, NOTEBOOK, ID)
                .pathParameters(notebookId);

        return List.of(httpClient.
                get(endpoint, Section[].class)
                .getObjectOrThrow());
    }

    @Override
    public int countByNotebookId(Long notebookId) {
        return 0;
    }

    @Override
    public void create(SectionInput sectionInput) {

    }

    @Override
    public void update(SectionInput sectionInput) {

    }
}
