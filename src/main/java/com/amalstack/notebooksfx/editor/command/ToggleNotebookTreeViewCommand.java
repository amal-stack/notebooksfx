package com.amalstack.notebooksfx.editor.command;

import com.amalstack.notebooksfx.command.Command;
import com.amalstack.notebooksfx.command.ParameterizedCommand;
import com.amalstack.notebooksfx.util.controls.Graphic;
import com.amalstack.notebooksfx.util.controls.GraphicNodeProvider;
import javafx.scene.control.ToggleButton;
import org.controlsfx.control.MasterDetailPane;

public class ToggleNotebookTreeViewCommand implements Command, ParameterizedCommand<Boolean> {

    private final MasterDetailPane masterDetailPane;
    private final ToggleButton viewSectionsBtn;
    private final GraphicNodeProvider graphic;

    public ToggleNotebookTreeViewCommand(MasterDetailPane masterDetailPane,
                                         ToggleButton viewSectionsBtn,
                                         GraphicNodeProvider graphic) {
        this.masterDetailPane = masterDetailPane;
        this.viewSectionsBtn = viewSectionsBtn;
        this.graphic = graphic;
    }

    @Override
    public void execute() {
        boolean shouldShow = !masterDetailPane.isShowDetailNode();
        if (shouldShow) {
            // show
            execute(true);
            return;
        }
        // hide
        execute(false);
    }

    @Override
    public void execute(Boolean show) {
        if (show) {
            // show
            masterDetailPane.setShowDetailNode(true);
            viewSectionsBtn.setGraphic(graphic.getNode(Graphic.HIDE));
            return;
        }
        // hide
        masterDetailPane.setShowDetailNode(false);
        viewSectionsBtn.setGraphic(graphic.getNode(Graphic.SHOW));
    }
}
