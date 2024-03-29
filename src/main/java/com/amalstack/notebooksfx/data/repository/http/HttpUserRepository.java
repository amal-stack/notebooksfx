package com.amalstack.notebooksfx.data.repository.http;

import com.amalstack.notebooksfx.AppRouteNames;
import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.repository.UserRepository;
import com.amalstack.notebooksfx.util.http.Endpoint;
import com.amalstack.notebooksfx.util.http.UrlProvider;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;


public class HttpUserRepository implements UserRepository {
    private final UrlProvider urlProvider;

    public HttpUserRepository(UrlProvider urlProvider) {
        this.urlProvider = urlProvider;
    }

    @Override
    public User findById(Long id) {
        try {
            var request = HttpRequest.newBuilder()
                    .uri(urlProvider.getEndpoint(
                            Endpoint.named(AppRouteNames.NOTEBOOK, AppRouteNames.USER)
                                    .queryParameters("id", id.toString())))
                    .header("Authorization", "")
                    .GET()
                    .build();


            var json = HttpClient.newBuilder()
                    .build()
                    .send(request, BodyHandlers.ofString());
            throw new UnsupportedOperationException();
        } catch (InterruptedException | IOException e) {
            throw new RuntimeException(e);
        }
    }

//    @Override
//    public void create(User user) {
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(urlProvider.getEndpoint(Endpoint.ofName(AppRouteNames.USERS)))
//                .POST(BodyPublishers.noBody())
//                .build();
//
//
//        var client = HttpClient.newHttpClient();
//        HttpResponse<String> response;
//        try {
//            response = client.send(request, BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }


}



