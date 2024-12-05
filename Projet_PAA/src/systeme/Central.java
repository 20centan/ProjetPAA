package systeme;


import graphique.Menu;
import systeme.entite.Colonie;
import systeme.operation.ConstructionColonie;
import systeme.operation.DistributionColonie;

public class Central {
    private Colonie colonie;
    
    public Central(){
        colonie = new Colonie();
    }

    public Central(String fichier){
        colonie = new Colonie();
        
    }

    public void construireColonie(Menu menu){
        ConstructionColonie.run(colonie, menu);
    }

    public void distribuerColonie(Menu menu){
        DistributionColonie.run(colonie, menu);
    }

    public void construireColonie(String fichier){
        ConstructionColonie.run(colonie, fichier);
    }

    public void distribuerColonie(Menu menu, String fichier){
        DistributionColonie.run(colonie, menu);
    }

    public Colonie getColonie() {
        return colonie;
    }
}
