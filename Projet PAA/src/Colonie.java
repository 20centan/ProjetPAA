import java.util.List;
import java.util.ArrayList;

public class Colonie {
	
    private int nbColons;
    private List<Colon> colons;

    public Colonie(int nbColons){
        this.nbColons = nbColons;

        colons = new ArrayList<>();

        initialisationColons();
    }

    public void initialisationColons(){
        for(int i = 0; i < nbColons; i++){
            char nomColon = (char) ('A' + i);

            colons.add(new Colon(nomColon));
        }
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

    public int getNbColons(){
        return nbColons;
    }

    public static void main(String [] args){
        Colonie colonie = new Colonie(5);

        Colon c = colonie.getColon('B');

        System.out.println(c.getNom());
    }

}
