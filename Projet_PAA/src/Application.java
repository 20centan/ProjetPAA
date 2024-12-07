import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central central;

    private String fichier = "/home/roland/Documents/projets/2024-PAA/Projet_PAA/data/config2.txt";

    public Application(String [] args){
        menu = new Menu();
        central = new Central();

        if(args.length != 0){
            fichier = args[0];
        }
    }

    public void run(){
        boolean running = true;
        
        while(running){
            if(fichier == null){
                central.construireColonie(menu);
                central.distribuerColonie(menu);
            }
            else{
                central.construireColonie(fichier);
            }

            running = false;
        }

        menu.close();
    }
}
