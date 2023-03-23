package com.amalstack.notebooksfx.editor.nav;

import com.amalstack.notebooksfx.command.Commands;
import com.amalstack.notebooksfx.data.DataAccessService;
import com.amalstack.notebooksfx.util.ControllerParameters;
import com.amalstack.notebooksfx.util.ParameterizedController;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import com.amalstack.notebooksfx.util.controls.builder.TreeViewBuilder;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class NotebookTreeViewController implements ParameterizedController {

    private final GraphicNodeProvider graphic;

    private final DataAccessService dataAccessService;

    private final ReadOnlyObjectWrapper<PageTreeItemModel> currentPage = new ReadOnlyObjectWrapper<>();

    private ControllerParameters parameters;

    @FXML
    private VBox parent;

    @FXML
    private Button newSectionBtn;

    @FXML
    private ToolBar treeToolbar;


    public NotebookTreeViewController(DataAccessService dataAccessService,
                                      GraphicNodeProvider graphic) {
        this.dataAccessService = dataAccessService;
        this.graphic = graphic;
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

    private void initNotebookTreeView(long notebookId) {
        var treeView = buildTreeView();
        NotebookTreeViewContext context = new NotebookTreeViewContext(
                treeView,
                dataAccessService,
                graphic);

        context.initialize(notebookId);
        parent.getChildren().add(treeView);
        newSectionBtn.setOnAction(Commands.eventHandler(
                new CreateSectionCommand(context)));
    }

    private TreeView<TreeItemModel> buildTreeView() {
        return new TreeViewBuilder<TreeItemModel>()
                .withId("notebookTreeView")
                .onTreeItemSelect(this::onTreeItemSelect)
                .onEditCommit(onTreeViewEditCommitEventHandler())
                .configure(treeView -> {
                    VBox.setVgrow(treeView, Priority.ALWAYS);
                    treeView.setShowRoot(true);
                    treeView.setEditable(true);
                })
                .build();
    }

    private void onTreeItemSelect(
            ObservableValue<? extends TreeItem<TreeItemModel>> observableValue,
            TreeItem<TreeItemModel> previousItem,
            TreeItem<TreeItemModel> currentItem) {

        if (currentItem != null && currentItem.getValue() instanceof PageTreeItemModel page) {
            currentPage.set(page);
        }
    }

    private EventHandler<TreeView.EditEvent<TreeItemModel>> onTreeViewEditCommitEventHandler() {
        return Commands.eventHandlerWithEventArg(
                new RenameTreeItemCommand(dataAccessService),
                TreeView.EditEvent::getOldValue);
    }


    public PageTreeItemModel getCurrentPage() {
        return currentPage.get();
    }

    public ReadOnlyObjectProperty<PageTreeItemModel> currentPageProperty() {
        return currentPage;
    }

}
