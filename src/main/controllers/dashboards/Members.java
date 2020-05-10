package main.controllers.dashboards;

import javafx.scene.input.MouseEvent;
import main.resources.models.ViewSelect;

import java.io.IOException;

public interface Members {

    void showPaymentView() throws IOException;

    void setAddSchool() throws IOException;

    void viewTable(ViewSelect viewSchools) throws IOException;

    void closeAction(MouseEvent event);

    void minimizeApp(MouseEvent event);

    void showMain() throws IOException;
}
