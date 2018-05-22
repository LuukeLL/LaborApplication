package sample;

import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.List;

public class ParentAufgabe extends Aufgabe {

    private Image image;
    private List<ChildAufgabe> childAufgaben;

    public ParentAufgabe(String newTitle){
        super(newTitle);
        this.childAufgaben = new ArrayList<>();
    }

    public void AddChildAufgabe(ChildAufgabe newChild){
        childAufgaben.add(newChild);
    }
    public ChildAufgabe getChildByTitle(String childTitle){
        for(ChildAufgabe aChild : childAufgaben){
            if(aChild.title.equals(childTitle))
                return aChild;
        }
        return null;
    }

    public void setChildAufgaben(List<ChildAufgabe> newChilds){
        this.childAufgaben = newChilds;
    }
    public List<ChildAufgabe> getChilds(){
        return childAufgaben;
    }

    public void setImage(Image newimage){
        this.image = newimage;
    }
    public Image getImage(){
        return image;
    }
}
