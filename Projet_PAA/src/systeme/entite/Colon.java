package systeme.entite;

import java.util.List;
import java.util.ArrayList;

public class Colon {
    private char nom;
    private List<Colon> ennemis;
    private List<Ressource> preference;
    private Ressource ressource;

    public Colon(char nom){
        this.nom = nom;

        this.ennemis = new ArrayList<Colon>();
        this.preference = new ArrayList<Ressource>();
    }

    public void ajoutEnnemi(Colon c){
        if(!ennemis.contains(c)){
            ennemis.add(c);
            
            c.ajoutEnnemi(this);
        }
    }

    public void ajoutRessource(Ressource ressource){
        this.ressource = ressource;
    }
    
    
    public void ajoutPreference(Ressource ressource) {
    	preference.add(ressource);
    }


    public char getNom() {
        return nom;
    }

    public Ressource getRessource(){
        return ressource;
    }

    public List<Colon> getEnnemis() {
        return ennemis;
    }

    public List<Ressource> getPreference() {
        return preference;
    }

    @Override
    public String toString(){
        return String.valueOf(nom);
    }

    public void setRessource(Ressource r){
        this.ressource = r;
    }
}
