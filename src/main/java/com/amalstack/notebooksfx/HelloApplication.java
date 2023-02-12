package com.amalstack.notebooksfx;

import com.amalstack.notebooksfx.data.model.User;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.di.Container;
import com.amalstack.notebooksfx.di.Lifetime;
import com.amalstack.notebooksfx.editor.Configuration;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.nav.*;
import com.amalstack.notebooksfx.notebook.DefaultNotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.util.JacksonMapper;
import com.amalstack.notebooksfx.util.JsonMapper;
import com.amalstack.notebooksfx.util.http.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class HelloApplication extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        System.setProperty("prism.lcdtext", "false");
        stage.setScene(new Scene(new Group()));
        Container container = new Container();
        initServices(container);
        initNav(container, stage);
        container.getService(NavigationManager.class)
                .navigateTo(Parents.AUTH, stage);

        var endpointProvider = container.getService(EndpointProvider.class);
        System.out.println("Notebooks::" + endpointProvider.getEndpoint(RouteNames.NOTEBOOKS));
        System.out.println("Notebooks/User::" + endpointProvider.getEndpoint(RouteNames.NOTEBOOKS, RouteNames.USER));
        System.out.println("Base URL::" + endpointProvider.getBaseUrl());
        System.out.println("Auth::" + container.getService(AuthenticationService.class).authenticate("user_1", "pwd".toCharArray(), User.class));
        //        loadScene(
//                stage,
//                HelloApplication.class.getResource("editor-view.fxml"),
//                "Notebooks",
//                x -> new EditorController(new Configuration.DefaultEditorContextFactory(), new DefaultGraphicNodeProvider())
//        );
//        loadScene(stage,
//                HelloApplication.class.getResource("notebooks-view.fxml"),
//                "Notebooks",
//                x -> new NotebooksController(new NotebookRepository() {
//                    final Random random = new Random();
//                    @Override
//                    public Collection<Notebook> findByUserId(Long userId) {
//                        return List.of(
//                                new Notebook(1L,
//                                        "My First Notebook",
//                                        LocalDateTime.now(),
//                                        random.nextInt(),
//                                        random.nextInt()),
//                                new Notebook(2L,
//                                        "My Second Notebook",
//                                        LocalDateTime.now(),
//                                        random.nextInt(),
//                                        random.nextInt())
//                        );
//                    }
//                },
//                        new DefaultNotebookTableViewFactory(),
//                        new User(0L, "example@example.com")));
    }

    //Delete
    private void loadScene(
            Stage stage,
            URL url,
            String title,
            Callback<Class<?>, Object> controllerFactory)
            throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(url);
        fxmlLoader.setControllerFactory(controllerFactory);
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("appstyle.css")).toString());
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();

    }

    private void initServices(Container container) {
        container.addService(NotebookRepository.class, MockNotebookRepository.class, Lifetime.TRANSIENT);
        container.addService(GraphicNodeProvider.class, DefaultGraphicNodeProvider.class, Lifetime.SINGLETON);
        container.addService(EditorContextFactory.class, Configuration.DefaultEditorContextFactory.class, Lifetime.SINGLETON);
        container.addService(NotebookTableViewFactory.class, DefaultNotebookTableViewFactory.class, Lifetime.SINGLETON);
        container.addService(EndpointProvider.class, DefaultEndpointProvider.class, Lifetime.SINGLETON, this::createEndpointProvider);
        container.addService(AuthenticationContext.class, DefaultAuthenticationContext.class, Lifetime.SINGLETON);
        container.addService(AuthenticationService.class, HttpBasicAuthenticationService.class, Lifetime.SINGLETON);
        container.addService(JsonMapper.class, JacksonMapper.class, Lifetime.TRANSIENT, () -> new JacksonMapper(
                        new ObjectMapper()
                                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
                                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                                .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true)
                )
        );
        container.addService(HttpClientService.class, BasicHttpClientService.class, Lifetime.SINGLETON);
        container.addService(ErrorResponseTypeProvider.class, DefaultErrorResponseTypeProvider.class, Lifetime.SINGLETON);
    }

    private void initNav(Container container, Stage stage) {
        container.addService(NavigationManager.class, SimpleNavigationManager.class, Lifetime.SINGLETON, () -> SimpleNavigationManagerFactory.create(stage));
        var nav = container.getService(NavigationManager.class);

        nav.addParent(new ParentParameters(Parents.HOME,
                HelloApplication.class.getResource("notebooks-view.fxml"),
                "Home",
                container::injectAndConstruct));

        nav.addParent(new ParentParameters(Parents.EDITOR,
                getClass().getResource("editor-view.fxml"),
                "Editor",
                container::injectAndConstruct));

        nav.addParent(new ParentParameters(Parents.AUTH,
                getClass().getResource("auth-view.fxml"),
                "Login or Sign Up",
                container::injectAndConstruct));
    }

    public DefaultEndpointProvider createEndpointProvider() {
        RouteTable routeTable = RouteTable.builder()
                .mapGroup(RouteNames.NOTEBOOKS, "/notebooks", group -> {
                    group.map("", "/");
                    group.map(RouteNames.USER, "/user");
                })
                .mapGroup(RouteNames.SECTIONS, "/sections", group -> {
                    group.map("", "/");
                    group.map(RouteNames.NOTEBOOK, "/notebook");
                })
                .mapGroup(RouteNames.PAGES, "/pages", group -> {
                    group.map("", "/");
                    group.map(RouteNames.SECTION, "/section");
                })
                .map(RouteNames.USERS, "/users")
                .build();


        return new DefaultEndpointProvider(
                "http://localhost:8080",
                routeTable);
    }
}


