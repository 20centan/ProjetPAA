package systeme.entite;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

public class Colonie {
    private int nbColons;
    private List<Colon> colons;
    private List<Ressource> ressources; 

    public Colonie(){
        colons = new ArrayList<>();
        ressources = new ArrayList<>();
    }

    public void ajouterColon(String nomColon){
        colons.add(new Colon(nomColon));
    }
    public void ajoutRessources(String nomRessource){
        ressources.add(new Ressource(nomRessource));
    }

    public void initialisationColonie(int nbColons){
        this.nbColons = nbColons;

        for(int i = 0; i < nbColons; i++){
            ajouterColon(String.valueOf((char) ('A' + i)));

            ajoutRessources(String.valueOf(i + 1));
        }
    }

    public boolean appartientColonie(Colon colon){
        return getColon(colon.getNom()) != null;
    }

    public boolean appartientColonie(String nom){
        return getColon(nom) != null;
    }

    public boolean preferenceVide(){
        for(Colon colon : colons){
            if(colon.getPreference().isEmpty()){
                return true;
            }
        }
        return false;
    }

    public boolean preferenceValide(String [] preferences) {
        Set<String> setPreference = new HashSet<>(Arrays.asList(preferences));

        for(Ressource ressource : ressources){
            if(!setPreference.contains(ressource.getNom())){
                return false;
            }
        }

        return true;
    }

    public Colon getColon(String nom){ //Vérifie si le colon existe dans la colonie
        for(Colon colon : colons){
            if(colon.getNom().equals(nom)) {
                return colon;
            }
        }
        System.out.println("Le colon " + nom + " n'existe pas dans la colonie");
        
        return null;
    }

    public List<Colon> getColons(){
        return colons;
    }

    public List<Ressource> getRessources(){
        return ressources;
    }

    public Ressource getRessource(String nomRessource){
        for(Ressource ressource : ressources){
            if(ressource.getNom().equals(nomRessource)){
                return ressource;
            }
        }

        return null;
    }

    public int getNbColons(){
        return nbColons;
    }
}