package systeme.entite;

import java.util.ArrayList;
import java.util.List;

public class Colonie {
    private int nbColons;
    private List<Colon> colons;
    private List<Ressource> ressources; 

    public Colonie(){
        colons = new ArrayList<>();
        ressources = new ArrayList<>();
    }

    public void initialisationColonie(int nbColons){
        this.nbColons = nbColons;

        for(int i = 0; i < nbColons; i++){
            colons.add(new Colon(String.valueOf((char) ('A' + i))));

            ressources.add(new Ressource());
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

    public boolean preferenceValide(ArrayList<String> colon_preference) {
        List<String> preference = colon_preference.subList(1, colon_preference.size());
		int somme = 0;

        for(String element : preference){
            somme += Integer.parseInt(element);
        }
        
        int somme_attendu = (preference.size() * (preference.size() + 1)) / 2;

        return somme_attendu - somme == 0;
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

    public int getNbColons(){
        return nbColons;
    }
}