package system;


import graphic.Menu;
import system.entite.Colonie;
import system.operation.ConstructionColonie;
import system.operation.DistributionColonie;

public class Core {
    private Colonie colonie;
    
    public Core(){
        colonie = new Colonie();
    }

    public void construireColonie(Menu menu){
        ConstructionColonie.run(colonie, menu);
    }

    public void distribuerColonie(Menu menu){
        DistributionColonie.run(colonie, menu);
    }

    public Colonie getColonie() {
        return colonie;
    }
}
