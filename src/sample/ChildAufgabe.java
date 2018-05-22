package sample;

import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;

public class ChildAufgabe extends Aufgabe {

    private List<String> loesungen;
    private Aufgabe parentAufgabe;      // TODO Maybe not usefull
    private TextField eingabeTF;

    public ChildAufgabe(String newTitle){
        super(newTitle);
        loesungen = new ArrayList<>();
    }

    public List<String> getLoesungen(){ return this.loesungen; }
    public void addLoesung(String newLoesung) { this.loesungen.add(newLoesung); }

    public boolean checkLoesung(String newTry){
        if(loesungen.contains(newTry))
            return true;
        else
            return false;
    }

    public boolean checkLoesungFromTF(){
        if(checkLoesung(eingabeTF.getText()))
            return true;
        else
            return false;
    }
}
