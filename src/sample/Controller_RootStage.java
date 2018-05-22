package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller_RootStage {


    // region Class Members
    private String DATAPATH;
    private List<Versuch> versuche;
    private List<Controller_Tab> myTabController;
    // endregion

    // region Predefined indexes for the Textfile
    private static final int TITLE = 2;
    private static final int IMAGE_NAME = 3;
    private static final int LOESUNG = 3;
    // endregion

    // region Accessible Nodes from the FXML-File
    @FXML
    protected Menu menu_Versuche;
    @FXML
    protected TabPane tp_main;
    @FXML
    protected Button btn_check;
    @FXML
    protected Button btn_reset;
    // endregion

    public void initialize(){

        DATAPATH = "EMPTY";
        versuche = new ArrayList<>();
        myTabController = new ArrayList<>();


        // TODO Replace this with something more dynamic
        DATAPATH = GetFilepathFromResource("tasks.txt");

        // Lese Versuchsnamen aus und erstelle Entsprechende Anzahl an Objekten
        for(String vTitle : LoadVersuchsNamen()){
            versuche.add(CreateVersuch(vTitle));
        }

        // Lade für jeden Versuch die entsprechenden Aufgaben
        // Lade für jeweils selbigen Versuch die Loesungen
        for(Versuch myVersuch : versuche){
            LoadAufgaben(myVersuch);
            LoadLoesungen(myVersuch);

        }


        // Erstelle die MenuItems und die entsprechenden EventHandler
        CreateMenuHandlers();


    }

    private void CreateTab(ParentAufgabe parentAufgabe) throws Exception{
        Tab newTab = new Tab();                 // Tab zur Anzeige der Aufgabe
        newTab.setText(parentAufgabe.getTitle());
        AnchorPane newAP = new AnchorPane();    // AnchorPane zur richtigen Skallierung
        newTab.setContent(newAP);
        FXMLLoader content = new FXMLLoader(getClass().getResource("Layout_Tab.fxml"));
        newAP.getChildren().addAll((Node)content.load());

        tp_main.getTabs().add(newTab);
        Controller_Tab newController = content.getController();
        newController.setTabAufgabe(parentAufgabe);
        newController.displayImage();
        newController.displayInputMask();
        myTabController.add(newController);

    }

    // Erstellt einen Menüeintrag für jeden Versuch
    // Fügt jedem Menüeintrag einen Handler hinzu, welcher die entsprechenden Tabs generieren wird.
    private void CreateMenuHandlers(){
        for(Versuch versuch : versuche){                                // Für jeden Versuch
            MenuItem newItem = new MenuItem();                          // Erstelle Menüeintrag
            newItem.setText(versuch.getTitle());
            menu_Versuche.getItems().add(newItem);

            // Füge für das MenuItem einen Eventhandler hinzu
            newItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Object source = event.getSource();                  // Speichere EventQuelle
                    if(source instanceof MenuItem){                     // Ist die Quelle eine Instanz vom Typ MenuItem
                        try{
                            // Lade die Tabs für den entsprechenden Versuch in die Tabpane
                            tp_main.getTabs().clear();                  // Leere die TabPane zuerst
                            myTabController.clear();                    // Leere die TabController liste


                            // Erstelle für jede Aufgabe einen Tab
                            for(ParentAufgabe PA : GetVersuchByTitle(((MenuItem) source).getText()).getParentAufgaben()){
                                CreateTab(PA);
                            }
                        } catch(Exception e){
                            System.out.println("Fehler beim erstellen des Tabs!\n" + e.toString());
                        }
                    }
                }
            });
        }
    }

    // Lese die Textdatei um die Anzahl und Namen der Versuche zu erhalten
    private List<String> LoadVersuchsNamen(){
        List<String> versuchsTitle = new ArrayList<>();
        try{
            BufferedReader br = new BufferedReader(new FileReader(DATAPATH));
            String sCurrentLine;
            List<String> metaSplitList;

            while ((sCurrentLine = br.readLine()) != null){
                if(sCurrentLine.contains("VERSUCHE") && !sCurrentLine.contains("#")) {                // Zeile ist kein Kommentar

                    metaSplitList = Arrays.asList(sCurrentLine.split(","));
                    for(String versuchName : metaSplitList){
                        versuchsTitle.add(versuchName);
                    }
                }
            }
            br.close();
            versuchsTitle.remove(0);                 // Entferne ersten Eintrag
            return versuchsTitle;
        } catch (Exception e){
            System.out.println("Fehler beim Einlesen der Versuchsnamen.\n" + e.toString());
            return null;
        }
    }

    // Create new Versuch based on vTitle
    private Versuch CreateVersuch(String vTitle){
        try {
            Versuch newVersuch = new Versuch(vTitle);
            return newVersuch;
        } catch (Exception e){
            return null;
        }

    }


    private Versuch GetVersuchByTitle(String title){
        for(Versuch versuch : versuche){
            if(versuch.getTitle().equals(title))
                return versuch;
            }
        return null;
    }

    // Durchsuche die Textdatei nach Aufgaben und lade diese in den Versuch
    private void LoadAufgaben(Versuch aVersuch){
        try{
            BufferedReader br = new BufferedReader(new FileReader(DATAPATH));
            String sCurrentLine;
            List<String> metaSplitList;

            Image newImage = null;
            ParentAufgabe newParentAufgabe = null;


            while ((sCurrentLine = br.readLine()) != null){

                // Lade und erstelle die Aufgaben mit Titel und Bild
                if(sCurrentLine.contains(aVersuch.getTitle())           // Aktueller Versuch
                        && sCurrentLine.contains("IMAGE")               // Zeile gibt Bildinformation
                        && !sCurrentLine.contains("#")){                // Zeile ist kein Kommentar

                    metaSplitList = Arrays.asList(sCurrentLine.split(","));

                    newParentAufgabe = new ParentAufgabe(metaSplitList.get(TITLE));     // Erstelle neue Aufgabe mit Titel
                    if(!metaSplitList.get(IMAGE_NAME).equals("NOIMAGE")){        // Suche nach Bild für die Aufgabe
                        newImage = LoadImageByName(metaSplitList.get(IMAGE_NAME));
                    }
                    newParentAufgabe.setImage(newImage);                          // Setze Bild für die Aufgabe
                    aVersuch.addParentAufgabe(newParentAufgabe);                        // Füge Aufgabe hinzu
                }
            }
            br.close();

        }catch(Exception e) {
            System.out.println("Fehler beim Einlesen der Aufgaben.\n" + e.toString());
        }
    }

    // Durchsuche die Textdatei um die Aufgabenloesungen zu finden
    private void LoadLoesungen(Versuch aVersuch){
        try{
            FileInputStream fin = new FileInputStream(DATAPATH);
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String sCurrentLine;
            List<String> metaSplitList;


            for(ParentAufgabe currentPA : aVersuch.getParentAufgaben()){                  // Suche für jede Aufgabe nach Loesung
                while ((sCurrentLine = br.readLine()) != null){
                    if(sCurrentLine.contains(aVersuch.getTitle())                  // Aktueller Versuch
                            && sCurrentLine.contains("TASK")                // Zeile beschreibt Aufgabe
                            && !sCurrentLine.contains("#")                  // Zeile ist kein Kommentar
                            && sCurrentLine.contains(currentPA.getTitle())){ // Zeile gibt informationen über Aufgabe

                        metaSplitList = Arrays.asList(sCurrentLine.split(","));


                        // Füge ChildAufgaben zu der ParentAufgabe hinzu
                        ChildAufgabe newChild = new ChildAufgabe(metaSplitList.get(TITLE));
                        for(int index = LOESUNG; index < (metaSplitList.size()-1); index++){
                            newChild.addLoesung(metaSplitList.get(index));
                        }
                        currentPA.AddChildAufgabe(newChild);

                    }
                }
                fin.getChannel().position(0);                               // Reset Position of the FileInputStram
                br = new BufferedReader(new InputStreamReader(fin));        // Create new Buffered Reader
            }
            br.close();

        } catch (Exception e){
            System.out.println("Fehler beim Einlesen der Loesungen.\n" + e.toString());
        }
    }

    // Lade Bild anhand des Namens von vorgegebenem Pfad
    // Momentan noch von "imgs/"
    // TODO Ermögliche das Laden von anderem Pfad
    private Image LoadImageByName(String name){
        try{
            Image TaskImage = new Image(Main.class.getResourceAsStream("imgs/" + name));
            return TaskImage;
        }catch(Exception e){
            System.out.println("Fehler beim laden des Aufgabenbildes.\n" + e.toString());
            return null;
        }
    }

    // Get the Path of the taskfile stored in the Program folder
    private String GetFilepathFromResource(String fileName) {
        Object taskFile = getClass().getResource(fileName);
        return ((URL) taskFile).getPath();
    }

    @FXML
    protected void onBTN_Check_Clicked(){
        
    }

    @FXML
    protected void onBTN_Reset_Clicked(){

    }

}
