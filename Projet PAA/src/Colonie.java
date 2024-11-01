import java.util.List;
import java.util.ArrayList;

public class Colonie {
	
    private int nbColons;
    private List<Colon> colons;
    private List<Ressource> ressources; 

    public Colonie(int nbColons){
        this.nbColons = nbColons;

        initialisationColonie();
    }

    public void initialisationColonie(){
        colons = new ArrayList<>();
        ressources = new ArrayList<>();

        for(int i = 0; i < nbColons; i++){
            colons.add(new Colon((char) ('A' + i)));

            ressources.add(new Ressource());
        }
    }

    public boolean appartientColonie(Colon colon){
        return getColon(colon.getNom()) != null;
    }

    public boolean appartientColonie(char nom){
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

    public boolean preferenceValide(ArrayList<String> preference) {
		int somme = 0;

        for(String element : preference.subList(1, preference.size())){
            somme += Integer.parseInt(element);
        }

        int somme_attendu = (preference.size() * (preference.size() + 1)) / 2;
	
        return somme_attendu - somme == 0;
    }


    public Colon getColon(char nom){ //Vérifie si le colon existe dans la colonie
        for(Colon c : colons){
            if(c.getNom() == nom) {
                return c;
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

    public static void main(String [] args){
        Colonie colonie = new Colonie(5);

        System.out.println(colonie.ressources.get(1).getId());
    }

}
