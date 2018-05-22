package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller_Tab implements Initializable {

    ParentAufgabe tabAufgabe;

    @FXML
    ImageView iv_task;

    @FXML
    AnchorPane ap_inputMask;


    @Override
    public void initialize(URL location, ResourceBundle resources ){

    }

    public void setTabAufgabe(ParentAufgabe newParentAufgabe){
        this.tabAufgabe = newParentAufgabe;
    }

    public void displayImage(){
        iv_task.setImage(tabAufgabe.getImage());
    }

    public void displayInputMask(){
        VBox tfBox = new VBox();

        // TODO Fill with ChildAufgaben

        ap_inputMask.getChildren().setAll(tfBox);
    }
}
