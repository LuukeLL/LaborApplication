package sample;


import javafx.scene.image.Image;


public class Aufgabe {
    protected String title;
    protected String loesung;                     // TODO Mehrere Loseungen ermöglichen.
    private Image image;



    public Aufgabe(String newtitle, String newloesung){
        this.title = newtitle;
        this.loesung = newloesung;
        this.image = null;
    }

    public Aufgabe(String newtitle){
        this.title = newtitle;
        this.loesung = "EMPTY";
        this.image = null;
    }

    public void setImage(Image newimage){
        this.image = newimage;
    }

    public String getTitle(){
        return title;
    }

    public void setLoesung(String newLoesung){
        this.loesung = newLoesung;
    }

    public String getLoesung(){
        return this.loesung;
    }

    public Image getImage(){
        return image;
    }
}
