package sample;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Versuch {

    private String title;
    private List<Aufgabe> aufgaben;

    public Versuch(){
        this.aufgaben = new ArrayList<>();
        this.title = "NoName";
    }

    public Versuch(String newtitle){
        this.aufgaben = new ArrayList<>();
        this.title = newtitle;
    }

    public void addAufgabe(Aufgabe newAufgabe)
    {
        aufgaben.add(newAufgabe);
    }

    public Aufgabe getAufgabeIndex(String title){
        for(int index = 0; index < aufgaben.size(); index++){
            if(aufgaben.get(index).equals(title)){
                return aufgaben.get(index);
            }
        }
        return null;
    }

    public List<Aufgabe> getAufgaben(){
        return aufgaben;
    }

    public Aufgabe getAufgabeAtIndex(int index){
        return aufgaben.get(index);
    }

    public String getTitle(){ return this.title; }
}
