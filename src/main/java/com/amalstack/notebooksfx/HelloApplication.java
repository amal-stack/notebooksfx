package com.amalstack.notebooksfx;

import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.data.HttpDataAccessService;
import com.amalstack.notebooksfx.data.repository.NotebookRepository;
import com.amalstack.notebooksfx.data.repository.PageRepository;
import com.amalstack.notebooksfx.data.repository.SectionRepository;
import com.amalstack.notebooksfx.data.repository.UserRepository;
import com.amalstack.notebooksfx.data.repository.http.HttpNotebookRepository;
import com.amalstack.notebooksfx.data.repository.http.HttpPageRepository;
import com.amalstack.notebooksfx.data.repository.http.HttpSectionRepository;
import com.amalstack.notebooksfx.data.repository.http.HttpUserRepository;
import com.amalstack.notebooksfx.di.Container;
import com.amalstack.notebooksfx.di.Lifetime;
import com.amalstack.notebooksfx.editor.DefaultEditorContextFactory;
import com.amalstack.notebooksfx.editor.EditorContextFactory;
import com.amalstack.notebooksfx.nav.*;
import com.amalstack.notebooksfx.notebook.DefaultNotebookTableViewFactory;
import com.amalstack.notebooksfx.notebook.NotebookTableViewFactory;
import com.amalstack.notebooksfx.util.JacksonMapper;
import com.amalstack.notebooksfx.util.JsonMapper;
import com.amalstack.notebooksfx.util.controls.DefaultGraphicNodeProvider;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.http.*;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import static com.amalstack.notebooksfx.AppRouteNames.*;

public class HelloApplication extends Application {

    // Main method removed for exceptions to be caught by Thread.setDefaultUncaughtExceptionHandler()

    private static void addDataAccess(Container container) {
        container.addService(NotebookRepository.class, HttpNotebookRepository.class, Lifetime.TRANSIENT);
        container.addService(SectionRepository.class, HttpSectionRepository.class, Lifetime.TRANSIENT);
        container.addService(PageRepository.class, HttpPageRepository.class, Lifetime.TRANSIENT);
        container.addService(UserRepository.class, HttpUserRepository.class, Lifetime.TRANSIENT);
        container.addService(DataAccessService.class, HttpDataAccessService.class, Lifetime.TRANSIENT);
    }

    @Override
    public void start(Stage stage) {
        try {
            //Thread.setDefaultUncaughtExceptionHandler(this::showError);
            System.setProperty("prism.lcdtext", "false");
            stage.setScene(new Scene(new Group()));
            Container container = new Container();
            initServices(container);
            initNav(container, stage);
            container.getService(NavigationManager.class)
                    .navigateTo(Parents.AUTH, stage);

            var endpointProvider = container.getService(UrlProvider.class);
            System.out.println("Notebooks::" + endpointProvider.getEndpoint(Endpoint.named(NOTEBOOKS)));
            System.out.println("Notebooks/User::" + endpointProvider.getEndpoint(Endpoint.named(NOTEBOOKS, USER)));
            System.out.println("Base URL::" + endpointProvider.getBaseUrl());
            //System.out.println("Auth::" + container.getService(AuthenticationService.class).authenticate("user_1", "pwd".toCharArray(), User.class));
        } catch (HttpResponseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle("Server Response Error");
            alert.setHeaderText("Server returned an error");
            alert.setContentText(e.getErrorResponse().orElseThrow().error());
            alert.showAndWait();
        }
    }

    private void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.WINDOW_MODAL);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getLocalizedMessage());
            alert.showAndWait();
        }
        e.printStackTrace();

    }

    private void initServices(Container container) {

        container.addService(GraphicNodeProvider.class, DefaultGraphicNodeProvider.class, Lifetime.SINGLETON);
        container.addService(EditorContextFactory.class, DefaultEditorContextFactory.class, Lifetime.SINGLETON);
        container.addService(NotebookTableViewFactory.class, DefaultNotebookTableViewFactory.class, Lifetime.SINGLETON);

        container.addService(UrlProvider.class, DefaultUrlProvider.class, Lifetime.SINGLETON, this::createEndpointProvider);

        //container.addService(JsonMapper.class, GsonMapper.class, Lifetime.TRANSIENT, () -> GsonMapper.Factory.create(builder -> {}));
        container.addService(JsonMapper.class, JacksonMapper.class, Lifetime.TRANSIENT, () -> new JacksonMapper(createObjectMapper()));
        container.addService(HttpClientService.class, BasicHttpClientService.class, Lifetime.SINGLETON);
        container.addService(ErrorResponseTypeProvider.class, DefaultErrorResponseTypeProvider.class, Lifetime.SINGLETON);

        container.addService(AuthenticationContext.class, DefaultAuthenticationContext.class, Lifetime.SINGLETON);
        container.addService(AuthenticationService.class, HttpBasicAuthenticationService.class, Lifetime.SINGLETON);

        addDataAccess(container);

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

    public DefaultUrlProvider createEndpointProvider() {

        RouteTable routeTable = RouteTable.builder()
                .mapGroup(NOTEBOOKS, "/notebooks", group -> {
                    group.map("", "");
                    group.map(ID, "/{0}");
                    group.map(USER, "/user");
                })
                .mapGroup(SECTIONS, "/sections", group -> {
                    group.map("", "");
                    group.map(ID, "/{0}");
                    group.mapGroup(NOTEBOOK, "/notebook", subgroup
                            -> subgroup.map(ID, "/{0}"));
                })
                .mapGroup(PAGES, "/pages", group -> {
                    group.map("", "");
                    group.map(ID, "/{0}");
                    group.mapGroup(SECTION, "/section", subgroup
                            -> subgroup.map(ID, "/{0}"));
                })
                .map(USERS, "/users")
                .build();


        return new DefaultUrlProvider(
                "http://localhost:8080",
                routeTable);
    }

    private ObjectMapper createObjectMapper() {
        return new ObjectMapper()
                .configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false)
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true)
                .configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, true);
    }
}
