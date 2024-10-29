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

}
