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

    Aufgabe tabAufgabe;

    @FXML
    ImageView iv_task;

    @FXML
    AnchorPane ap_inputMask;


    @Override
    public void initialize(URL location, ResourceBundle resources ){

    }

    public void setTabAufgabe(Aufgabe newAufgabe){
        this.tabAufgabe = newAufgabe;
    }

    public void displayImage(){
        iv_task.setImage(tabAufgabe.getImage());
    }

    public void displayInputMask(){
        VBox tfBox = new VBox();



        ap_inputMask.getChildren().setAll(tfBox);
    }
}
