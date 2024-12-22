import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central central;

    //private String fichier;
    //private String fichier = "/home/roland/Documents/projets/2024-PAA/Projet_PAA/data/config1.txt";
    //private String fichier = "/Users/vincenttan/FAC/test.txt";
    //private String fichier = "users/vincenttan/FAC/paa/projet_paa/projet_paa/equipage1.txt";
    private String fichier;

    public Application(String [] args){
        menu = new Menu();
        central = new Central();

        if(args.length != 0){
            fichier = args[0];
        }
    }

    /**
     * Permet de lancer l'application
     */
    public void run(){
        boolean running = true;
        
        while(running){
            if(fichier == null){
                central.construireColonie(menu);
                central.distribuerColonie(menu);
            }
            else{
                central.construireColonie(fichier);
                central.distribuerColonie(menu,fichier);
            }

            running = false;
        }

        menu.close();
    }
}
