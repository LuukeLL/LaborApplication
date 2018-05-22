package sample;


import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;

public class Versuch {

    private String title;
    private List<ParentAufgabe> parentAufgaben;

    public Versuch(){
        this.parentAufgaben = new ArrayList<>();
        this.title = "NoName";
    }

    public Versuch(String newtitle){
        this.parentAufgaben = new ArrayList<>();
        this.title = newtitle;
    }

    public void addParentAufgabe(ParentAufgabe newParentAufgabe)
    {
        parentAufgaben.add(newParentAufgabe);
    }

    public void addChildAufgabeToParent(String parentTitle, ChildAufgabe aChildAufgabe){
        getParentAufgabeByTitle(parentTitle).AddChildAufgabe(aChildAufgabe);
    }

    public ParentAufgabe getParentAufgabeByTitle(String title){
        for(ParentAufgabe aParentAufgabe : parentAufgaben){
            if(aParentAufgabe.equals(title))
                return aParentAufgabe;
        }
        return null;
    }

    public List<ParentAufgabe> getParentAufgaben(){
        return parentAufgaben;
    }

    public Aufgabe getParentAufgabeAtIndex(int index){
        return parentAufgaben.get(index);
    }

    public String getTitle(){ return this.title; }
}
