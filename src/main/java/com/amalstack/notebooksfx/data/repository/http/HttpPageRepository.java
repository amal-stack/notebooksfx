package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.data.model.Page;
import com.amalstack.notebooksfx.data.model.PageInput;
import com.amalstack.notebooksfx.data.repository.PageRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.HttpClientService;

import java.util.Collection;
import java.util.List;

import static com.amalstack.notebooksfx.AppRouteNames.*;


public class HttpPageRepository implements PageRepository {

    private final HttpClientService httpClient;

    public HttpPageRepository(HttpClientService httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public Page findById(Long pageId) {
        Endpoint endpoint = Endpoint.named(PAGES, ID)
                .pathParameters(pageId);

        return httpClient.get(endpoint, Page.class)
                .getObjectOrThrow();
    }

    @Override
    public Collection<Page> findByNotebookId(Long notebookId) {
        return null;
    }

    @Override
    public Collection<Page> findBySectionId(Long sectionId) {
        Endpoint endpoint = Endpoint.named(PAGES, SECTION, ID)
                .pathParameters(sectionId);

        return List.of(httpClient
                .get(endpoint, Page[].class)
                .getObjectOrThrow());
    }

    @Override
    public int countByNotebookId(Long notebookId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int countBySectionId(Long sectionId) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void create(PageInput pageInput) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(PageInput pageInput) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void rename(Long pageId, String pageName) {

        Page page = findById(pageId);

        if (pageName.equals(page.title())) {
            return;
        }

        Endpoint endpoint = Endpoint.named(PAGES, ID)
                .pathParameters(pageId);
        PageInput input = new PageInput(pageName, page.content(), page.sectionId());

        httpClient.put(endpoint, input)
                .throwIfFailure();
    }

    @Override
    public void setContent(Long pageId, String content) {
        Page page = findById(pageId);
        if (content.equals(page.content())) {
            return;
        }

        Endpoint endpoint = Endpoint.named(PAGES, ID)
                .pathParameters(pageId);
        PageInput input = new PageInput(page.title(), content, page.sectionId());

        httpClient.put(endpoint, input)
                .throwIfFailure();
    }
}
