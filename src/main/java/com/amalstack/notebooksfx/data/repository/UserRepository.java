package com.amalstack.notebooksfx.data.repository;

import com.amalstack.notebooksfx.data.model.User;

public interface UserRepository {
    User findById(Long id);
}


