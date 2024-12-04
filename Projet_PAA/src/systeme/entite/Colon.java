package systeme.entite;

import java.util.List;
import java.util.ArrayList;

public class Colon {
    private String nom;
    private List<Colon> ennemis;
    private List<Ressource> preference;
    private Ressource ressource;

    public Colon(String nom){
        this.nom = nom;

        this.ennemis = new ArrayList<Colon>();
        this.preference = new ArrayList<Ressource>();
    }

    public void ajouterEnnemi(Colon c){
        if(!ennemis.contains(c)){
            ennemis.add(c);
            
            c.ajouterEnnemi(this);
        }
    }

    public void ajouterRessource(Ressource ressource){
        this.ressource = ressource;
    }
    
    
    public void ajouterPreference(Ressource ressource) {
    	preference.add(ressource);
    }

    public void viderPreference(){
        preference.clear();
    }


    public String getNom() {
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
