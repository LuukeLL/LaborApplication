package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Controller_RootStage {

    private String DATAPATH;
    private Versuch versuch;
    private List<String> versuche;
    private List<Controller_Tab> myTabController;

    @FXML
    protected Menu menu_Versuche;
    @FXML
    protected TabPane tp_main;
    @FXML
    protected Button btn_zurücksetzen;
    @FXML
    protected Button btn_überprüfen;

    public void initialize(){
        versuch = null;
        DATAPATH = "EMPTY";
        versuche = new ArrayList<>();
        myTabController = new ArrayList<>();

        // TODO Replace this with something more dynamic
        DATAPATH = getFilePathFromResource("tasks.txt");
        System.out.println(countVersuche());

        // TODO Throws an Error: Java.io.IOException: Stream not marked
        //System.out.println(loadLoesungen(versuche.get(0)));

        createMenuHandlers();


    }

    private void createTab(Aufgabe aufgabe) throws Exception{
        Tab newTab = new Tab();                 // Tab zur Anzeige der Aufgabe
        newTab.setText(aufgabe.getTitle());
        AnchorPane newAP = new AnchorPane();    // AnchorPane zur richtigen Skallierung
        newTab.setContent(newAP);
        FXMLLoader content = new FXMLLoader(getClass().getResource("Layout_Tab.fxml"));
        newAP.getChildren().addAll((Node)content.load());
        tp_main.getTabs().add(newTab);
        Controller_Tab newController = content.getController();
        newController.setTabAufgabe(aufgabe);
        newController.displayImage();
        myTabController.add(newController);

    }

    // Erstellt einen Menüeintrag für jeden Versuch
    // Fügt jedem Menüeintrag einen Handler hinzu, welcher die entsprechenden Tabs generieren wird.
    private void createMenuHandlers(){
        for(String versuchName : versuche){                             // Für jeden Versuch
            MenuItem newItem = new MenuItem();                          // Erstelle Menüeintrag
            newItem.setText(versuchName);
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

                            loadAufgaben(((MenuItem)source).getText()); // Lade Aufgaben für den gewählten Versuch

                            // Erstelle für jede Aufgabe einen Tab
                            for(Aufgabe aufgabe : versuch.getAufgaben()){
                                createTab(aufgabe);
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
    private int countVersuche(){
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(DATAPATH));
            String sCurrentLine;
            List<String> metaSplitList;

            while ((sCurrentLine = br.readLine()) != null){
                if(sCurrentLine.contains("VERSUCHE") && !sCurrentLine.contains("#")) {                // Zeile ist kein Kommentar

                    metaSplitList = Arrays.asList(sCurrentLine.split(","));
                    for(String versuchName : metaSplitList){
                        versuche.add(versuchName);
                        count += 1;
                    }
                }
            }
            br.close();

            versuche.remove(0);                 // Entferne ersten Eintrag
        } catch (Exception e){
            System.out.println("Fehler beim Einlesen der Versuchsnamen.\n" + e.toString());
        }
       return (count-1);
    }

    // Durchsuche die Textdatei nach Aufgaben und lade diese in den Versuch
    // Gebe Anzahl der geladenen Aufgaben zurück
    private int loadAufgaben(String versuchTitle){
        int count = 0;
        try{
            versuch = new Versuch(versuchTitle);
            BufferedReader br = new BufferedReader(new FileReader(DATAPATH));
            String sCurrentLine;
            List<String> metaSplitList;

            Image newImage = null;
            Aufgabe newAufgabe = null;


            while ((sCurrentLine = br.readLine()) != null){

                // Lade und erstelle die Aufgaben mit Titel und Bild
                if(sCurrentLine.contains(versuchTitle)                  // Aktueller Versuch
                        && sCurrentLine.contains("IMAGE")               // Zeile gibt Bildinformation
                        && !sCurrentLine.contains("#")){                // Zeile ist kein Kommentar

                    metaSplitList = Arrays.asList(sCurrentLine.split(","));

                    newAufgabe = new Aufgabe(metaSplitList.get(2));     // Erstelle neue Aufgabe mit Titel

                    if(!metaSplitList.get(3).equals("NOIMAGE")){        // Suche nach Bild für die Aufgabe
                        newImage = loadImage(metaSplitList.get(3));
                    }
                    newAufgabe.setImage(newImage);                      // Setze Bild für die Aufgabe
                    versuch.addAufgabe(newAufgabe);                     // Füge Aufgabe hinzu
                    count += 1;                                         // Erhöhe Zähler
                }
            }
            br.close();
        }catch(Exception e){
            System.out.println("Fehler beim Einlesen der Aufgaben.\n" + e.toString());
        }
        return count;
    }

    // Durchsuche die Textdatei um die Aufgabenloesungen zu finden
    // Gebe 0 zurück wenn alle Loesungen gefunden wurden ansonsten die verbleibende Zahl
    private int loadLoesungen(String versuchTitle){
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(DATAPATH));
            String sCurrentLine;
            List<String> metaSplitList;

            for(Aufgabe currentA : versuch.getAufgaben()){                  // Suche für jede Aufgabe nach Loesung
                while ((sCurrentLine = br.readLine()) != null){
                    if(sCurrentLine.contains(versuchTitle)                  // Aktueller Versuch
                            && sCurrentLine.contains("TASK")                // Zeile beschreibt Aufgabe
                            && !sCurrentLine.contains("#")                  // Zeile ist kein Kommentar
                            && sCurrentLine.contains(currentA.getTitle())){ // Zeile gibt informationen über Aufgabe

                        metaSplitList = Arrays.asList(sCurrentLine.split(","));
                        currentA.setLoesung(metaSplitList.get(3));
                        count += 1;                                         // Reduziere zu suchende Loesungen um 1
                    }
                }
                br.reset();
            }
            br.close();

        } catch (Exception e){
            System.out.println("Fehler beim einlesen der Loesungen.\n" + e.toString());
        }
        return count;
    }

    // Lade das Aufgabenbild
    private Image loadImage(String name){
        try{
            Image TaskImage = new Image(Main.class.getResourceAsStream("imgs/" + name));
            return TaskImage;
        }catch(Exception e){
            System.out.println("Fehler beim laden des Aufgabenbildes.\n" + e.toString());
            return null;
        }
    }

    // Get the Path of the taskfile stored in the Program folder
    private String getFilePathFromResource(String fileName) {
        Object taskFile = getClass().getResource(fileName);
        return ((URL) taskFile).getPath();
    }

}
