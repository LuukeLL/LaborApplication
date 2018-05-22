package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.w3c.dom.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller_Tab implements Initializable {

    ParentAufgabe tabAufgabe;
    List<TextField> inputFields;

    @FXML
    ImageView iv_task;
    @FXML
    AnchorPane ap_image;
    @FXML
    AnchorPane ap_inputMask;


    @Override
    public void initialize(URL location, ResourceBundle resources ){
        inputFields = new ArrayList<>();
    }

    public void setTabAufgabe(ParentAufgabe newParentAufgabe){
        this.tabAufgabe = newParentAufgabe;
    }

    public void displayImage(){
        AnchorPane.setTopAnchor(iv_task, 10.0);
        AnchorPane.setBottomAnchor(iv_task, 10.0);
        AnchorPane.setLeftAnchor(iv_task, 10.0);
        AnchorPane.setRightAnchor(iv_task, 10.0);
        iv_task.setPreserveRatio(true);
        // TODO Somehow a part on the right is hidden behind the splitter
        iv_task.fitWidthProperty().bind(ap_image.widthProperty());
        iv_task.fitHeightProperty().bind(ap_image.heightProperty());
        iv_task.fitHeightProperty();
        iv_task.setImage(tabAufgabe.getImage());
    }

    public void displayInputMask(){
        VBox tfBox = new VBox();

        // TODO Fill with ChildAufgaben
        for(ChildAufgabe child : tabAufgabe.getChilds()){
            TextField newTF = new TextField();
            newTF.setPromptText("Hier Ihre Lösung eintragen.");
            inputFields.add(newTF);
            tfBox.getChildren().add(new Label(child.getTitle()));
            tfBox.getChildren().add(newTF);
        }

        AnchorPane.setTopAnchor(tfBox, 10.0);
        AnchorPane.setBottomAnchor(tfBox, 10.0);
        AnchorPane.setLeftAnchor(tfBox, 10.0);
        AnchorPane.setRightAnchor(tfBox, 10.0);

        ap_inputMask.getChildren().setAll(tfBox);
    }
}
