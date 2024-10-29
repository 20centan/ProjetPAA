import java.util.List;
import java.util.ArrayList;
public class Colonie {
    private List<Colon> colonie;
    private int nbColons;

    public Colonie(int nbColons){
        this.nbColons = nbColons;
        initialisationColons();
    }

    public void initialisationColons(){
        for(int i = 0; i < nbColons ; i ++){
            char nomColon = (char) ('A'+ i);
            ArrayList<Ressource> preferences = new ArrayList<>();
            colonie.add(new Colon(nomColon,preferences));
        }
    }

    public Colon getColon(char nom){ //Vérifie si le colon existe dans la colonie
        for(Colon c : colonie){
            if(c.getNom() == nom) {
                return c;
            }
        }
        System.out.println("Le colon n'existe pas dans la colonie");
        return null;
    }

    public int getNbColons(){
        return nbColons;
    }



}
