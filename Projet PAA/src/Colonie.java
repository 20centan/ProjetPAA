import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Colonie {
	
    private int nbColons;
    private List<Colon> colons;
    private List<Ressource> ressources; 

    public void initialisationColonie(int nbColons){
        this.nbColons = nbColons;
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

    public boolean preferenceValide(ArrayList<String> colon_preference) {
        List<String> preference = colon_preference.subList(1, colon_preference.size());
		int somme = 0;

        for(String element : preference){
            somme += Integer.parseInt(element);
        }
        
        int somme_attendu = (preference.size() * (preference.size() + 1)) / 2;

        return somme_attendu - somme == 0;
    }

    public void afficheColonie(){
        StringBuilder str = new StringBuilder("");
        for(Colon c: colons){
            str.append(c.toString());
            str.append("\n");
        }
        System.out.println("Affichage colonie :\n"+str.toString());
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

    public void distribution() {
        System.out.println();
        System.out.println("Début de la distribution des ressources dans la colonie...");

        for (Colon c : colons) {
            boolean attribuer = false;
            int i = 0;
            while (!(attribuer)) {
                if (ressources.contains(c.getPreference().get(i))) {
                    Iterator<Ressource> it = ressources.iterator();
                    boolean trouver = false;
                    while(it.hasNext() && !(trouver)){
                        Ressource r = it.next();
                        if (r.equals(c.getPreference().get(i))) {
                            c.setRessource(r);
                            attribuer = true;
                            ressources.remove(r);
                            trouver = true;
                        }
                    }
                }
                i++;
            }
        }

        System.out.println();
        System.out.println("Fin de la distribution des ressources dans la colonie");
    }

    public int calculJaloux(){
        int nbJaloux = 0;
        for(Colon c : colons){
            Ressource ressource = c.getRessource();
            List<Ressource> preference = c.getPreference();
            List<Colon> ennemis = c.getEnnemis();
            int rangRessource = preference.indexOf(ressource);

            //Si le colon recois sa premiere ressource on ne rentre pas dans le if car il ne sera jamais jaloux
            if(ressource != preference.get(0)){
                Boolean stop = false;
                int i = 0;

                while(!(stop) && i<ennemis.size()){
                    Colon ennemi = ennemis.get(i);
                    Ressource ressourceEnnemi = ennemi.getRessource();
                    int rangRessourceEnnemi = ennemi.getPreference().indexOf(ressourceEnnemi);

                    int j = 0;
                    while(!(stop) && j<=rangRessource){
                        if(ressourceEnnemi.equals(preference.get(j)) && rangRessourceEnnemi < rangRessource){
                            nbJaloux++;
                            stop = true;
                        }
                        j++;
                    }
                    i++;
                }        
            }
        }
        return nbJaloux;
    }

}