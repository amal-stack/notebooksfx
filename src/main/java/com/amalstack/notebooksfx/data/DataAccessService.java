package com.amalstack.notebooksfx.data;

import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.data.repository.PageRepository;
import com.amalstack.notebooksfx.data.repository.SectionRepository;
import com.amalstack.notebooksfx.data.repository.UserRepository;

public interface DataAccessService {
    NotebookRepository notebooks();

    SectionRepository sections();

    PageRepository pages();

    UserRepository users();
}
