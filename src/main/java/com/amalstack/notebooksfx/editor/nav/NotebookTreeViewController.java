package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.command.Commands;
import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.editor.nav.command.CreateSectionCommand;
import com.amalstack.notebooksfx.editor.nav.command.RenameTreeItemCommand;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.util.ParameterizedController;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.controls.builder.TreeViewBuilder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

public class NotebookTreeViewController implements ParameterizedController {

    private final GraphicNodeProvider graphic;

    private final DataAccessService dataAccessService;

    private ControllerParameters parameters;

    @FXML
    private VBox parent;

    @FXML
    private Button newSectionBtn;

    @FXML
    private ToolBar treeToolbar;

    @FXML
    private ResourceBundle resources;

    private NotebookTreeViewContext context;

    private ParameterizedCommand<NotebookTreeViewContext> onContextCreated;


    public NotebookTreeViewController(DataAccessService dataAccessService,
                                      GraphicNodeProvider graphic) {
        this.dataAccessService = dataAccessService;
        this.graphic = graphic;
    }

    public NotebookTreeViewContext getContext() {
        return context;
    }


    @Override
    public ControllerParameters getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(ControllerParameters parameters) {
        this.parameters = parameters;
        onParametersSet();
    }

    private void onParametersSet() {
        Long notebookId = parameters.<Long>get("notebook.id").orElseThrow();
        initNotebookTreeView(notebookId);
    }

    public void setOnContextCreated(ParameterizedCommand<NotebookTreeViewContext> command) {
        this.onContextCreated = command;
    }

    private void initNotebookTreeView(long notebookId) {
        var treeView = buildTreeView();
        NotebookTreeViewContext context = new NotebookTreeViewContext(
                treeView,
                dataAccessService,
                graphic,
                resources);

        context.initialize(notebookId);

        parent.getChildren().add(treeView);

        newSectionBtn.setGraphic(graphic.getNode(Graphic.CREATE));
        newSectionBtn.setOnAction(Commands.eventHandler(
                new CreateSectionCommand(context)));

        this.context = context;
        if (onContextCreated != null) {
            onContextCreated.execute(context);
        }
    }

    private TreeView<TreeItemModel> buildTreeView() {
        return new TreeViewBuilder<TreeItemModel>()
                .withId("notebookTreeView")
                .onEditCommit(onTreeViewEditCommitEventHandler())
                .configure(treeView -> {
                    VBox.setVgrow(treeView, Priority.ALWAYS);
                    treeView.setShowRoot(true);
                    treeView.setEditable(true);
                })
                .build();
    }

    private EventHandler<TreeView.EditEvent<TreeItemModel>> onTreeViewEditCommitEventHandler() {
        return Commands.eventHandlerWithEventArg(
                new RenameTreeItemCommand(dataAccessService),
                TreeView.EditEvent::getOldValue);
    }
}
