import java.util.ArrayList;


public class Colon {
    private char nom;
    private ArrayList<Colon> ennemis;
    private ArrayList<Ressource> preference;
    private Ressource ressource;

    public Colon(char nom, ArrayList<Ressource> preference){
        this.nom = nom;
        this.preference = preference;

        this.ennemis = new ArrayList<Colon>();
    }

    public void aimePas(Colon c){
        ennemis.add(c);
        c.aimePas(this);
    }

    public void ajoutRessource(Ressource ressource){
        this.ressource = ressource;
    }
}
