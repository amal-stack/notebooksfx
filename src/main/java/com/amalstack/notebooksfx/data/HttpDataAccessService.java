package com.amalstack.notebooksfx.data;

import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.data.repository.PageRepository;
import com.amalstack.notebooksfx.data.repository.SectionRepository;
import com.amalstack.notebooksfx.data.repository.UserRepository;

public class HttpDataAccessService implements DataAccessService {

    private final NotebookRepository notebookRepository;
    private final SectionRepository sectionRepository;
    private final PageRepository pageRepository;
    private final UserRepository userRepository;

    public HttpDataAccessService(NotebookRepository notebookRepository,
                                 SectionRepository sectionRepository,
                                 PageRepository pageRepository,
                                 UserRepository userRepository) {
        this.notebookRepository = notebookRepository;
        this.sectionRepository = sectionRepository;
        this.pageRepository = pageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotebookRepository notebooks() {
        return notebookRepository;
    }

    @Override
    public SectionRepository sections() {

        return sectionRepository;
    }

    @Override
    public PageRepository pages() {
        return pageRepository;
    }

    @Override
    public UserRepository users() {
        return userRepository;
    }
}
