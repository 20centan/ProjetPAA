import java.util.ArrayList;
import java.util.List;


public class Colon {
    private String nom;
    private List<Colon> ennemis;
    private Ressource ressource;

    public Colon(String nom,Ressource ressource){
        this.nom = nom;
        this.ressource = ressource;
        List<Colon> ennemis = new ArrayList<Colon>();
    }

    public void aimePas(Colon c){
        enemis.add(c);
        c.aimePas(this);
    }

    public void ajoutRessource(Ressource ressource){
        this.ressource = ressource;
    }
}
