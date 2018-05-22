package sample;

public class ChildAufgabe extends Aufgabe {

    private String loesung;
    private Aufgabe parentAufgabe;      // TODO Maybe not usefull

    public ChildAufgabe(String newTitle, String newLoesung){
        super(newTitle);
        this.loesung = newLoesung;
    }

    public String getLoesung(){ return this.loesung; }
    public void setLoesung(String newLoesung) { this.loesung = newLoesung; }

}
