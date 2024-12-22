package systeme.entite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Colonie {

    private List<Colon> colons;
    private List<Ressource> ressources; 
    private int nbJaloux;

    public Colonie(){
        colons = new ArrayList<>();
        ressources = new ArrayList<>();
    }

    /**
     * Ajoute le colon passée en paramètre à la colonie
     * @param colon
     */
    public void ajouterColon(Colon colon){
        colons.add(colon);
    }

    /**
     * Ajoute un colon depuis un nom de colon passée en paramètre à la colonie
     * @param nomColon
     */
    public void ajouterColon(String nomColon){
        colons.add(new Colon(nomColon));
    }

    /**
     * Ajoute une ressource à la colonie
     * @param ressource
     */
    public void ajouterRessources(Ressource ressource){
        ressources.add(ressource);
    }

    /**
     * Ajoute une ressource depuis un nom de ressource à la colonie
     * @param nomRessource
     */
    public void ajouterRessources(String nomRessource){
        ressources.add(new Ressource(nomRessource));
    }

    /**
     * Initialise la colonie selon le nombre de colons passée en paramètre
     * Initialisation de la colonie pour la construction manuelle de la partie1
     * Chaque colon aura pour nom une lettre de l'alphabet, qui s'incrémente selon le nombre de colons
     * @param nbColons
     */
    public void initialisationColonie(int nbColons){

        for(int i = 0; i < nbColons; i++){
            ajouterColon(String.valueOf((char) ('A' + i)));

            ajouterRessources(String.valueOf(i + 1));
        }
    }

    /**
     * Vérifie si le colon passé en paramètre appartient à la colonie
     * @param colon
     * @return boolean
     */
    public boolean appartientColonie(Colon colon){
        return getColon(colon.getNom()) != null;
    }

    /**
     * Vérifie si le nom passée en paramètre est un colon qui appartient à la colonie
     * @param nom
     * @return boolean
     */
    public boolean appartientColonie(String nom){
        return getColon(nom) != null;
    }

    /**
     * Vérifie si les préferences des colons de la colonie sont vides.
     * @return boolean
     */
    public boolean preferenceVide(){
        for(Colon colon : colons){
            if(colon.getPreference().isEmpty()){
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie si la liste de préférence est valide ( contient bien des ressources)
     * @param preferences
     * @return boolean
     */
    public boolean preferenceValide(List<Ressource> preferences) {
        Set<Ressource> setPreference = new HashSet<>(preferences);

        for(Ressource ressource : ressources){
            if(!setPreference.contains(ressource)){
                return false;
            }
        }

        return true;
    }

    /**
     * Vérifie si la liste de préférence est valide ( contient bien des ressources )
      * @param preferences
     * @return boolean
     */
    public boolean preferenceValide(String [] preferences) {
        Set<String> setPreference = new HashSet<>(Arrays.asList(preferences));

        for(Ressource ressource : ressources){
            if(!setPreference.contains(ressource.getNom())){
                return false;
            }
        }

        return true;
    }

    /**
     * Affiche l'ensemble de la colonie
     * Colons - Ressources - Relations
     */
    public void afficherColonie(){
        // afficher colon
        System.out.println("Colon:");
        System.out.println(colons);

        System.out.println();

        // afficher ressource
        System.out.println("Ressource:");
        System.out.println(ressources);

        System.out.println();


        // afficher relation
        System.out.println("Colon - colon détesté:");
        for(Colon colon : colons){
            System.out.println(colon + " " + colon.getEnnemis());
        }

        System.out.println();

        // afficher ressource (rien de base)
        System.out.println("Colon - ressource:");
        for(Colon colon : colons){
            System.out.println(colon + " " + colon.getRessource());
        }
    }

    /**
     * Getter d'un colon qui vérifie si il existe bien dans la colonie
     * @param nom
     * @return Colon
     */
    public Colon getColon(String nom){ //Vérifie si le colon existe dans la colonie
        for(Colon colon : colons){
            if(colon.getNom().equals(nom)) {
                return colon;
            }
        }
        System.out.println("Le colon " + nom + " n'existe pas dans la colonie");
        
        return null;
    }

    /**
     * Getter de la liste des colons de la colonie
     * @return Liste de colons
     */
    public List<Colon> getColons(){
        return colons;
    }

    /**
     * Getter de la liste des ressources de la colonie
     * @return Liste des ressources
     */
    public List<Ressource> getRessources(){
        return ressources;
    }

    /**
     * Getter d'une ressource précise grâce à son nom passée en paramètre
     * @param nomRessource
     * @return Ressource
     */
    public Ressource getRessource(String nomRessource){
        for(Ressource ressource : ressources){
            if(ressource.getNom().equals(nomRessource)){
                return ressource;
            }
        }

        return null;
    }

    /**
     * Retourne le nombre de colons dans la colonie
     * @return int
     */
    public int getNbColons(){
        return colons.size();
    }

    public int getNbJaloux(){
        return nbJaloux;
    }

    public void setNbJaloux(int nbJaloux){
        this.nbJaloux = nbJaloux;
    }
}