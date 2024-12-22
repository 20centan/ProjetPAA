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

    /**
     * Permet d'ajouter un ennemi au colon
     * @param c //c le Colon ennemi
     */
    public void ajouterEnnemi(Colon c){
        if(!ennemis.contains(c)){
            ennemis.add(c);
            
            c.ajouterEnnemi(this);
        }
    }

    /**
     * Ajoute une ressource au colon
     * @param ressource
     */
    public void ajouterRessource(Ressource ressource){
        this.ressource = ressource;
    }

    /**
     * Ajoute une préférence au colon
     * @param ressource
     */
    public void ajouterPreference(Ressource ressource) {
    	preference.add(ressource);
    }

    /**
     * Vide la liste de préférences du colon
     */
    public void viderPreference(){
        preference.clear();
    }

    /**
     * Getter du nom du colon
     * @return String
     */
    public String getNom() {
        return nom;
    }

    /**
     * Getter de la ressource du colon
     * @return Ressource
     */
    public Ressource getRessource(){
        return ressource;
    }

    /**
     * Getter des ennemis du colon
     * @return liste de Colons
     */
    public List<Colon> getEnnemis() {
        return ennemis;
    }

    /**
     * Getter des préférences du colon
     * @return liste de Ressource
     */
    public List<Ressource> getPreference() {
        return preference;
    }

    @Override
    public String toString(){
        return String.valueOf(nom);
    }

    /**
     * Setter d'une ressource
     * @param r
     */
    public void setRessource(Ressource r){
        this.ressource = r;
    }
}
