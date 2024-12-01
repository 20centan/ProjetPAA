import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central central;

    private String fichier;

    public Application(String [] args){
        if(args.length == 0){
            menu = new Menu();
            central = new Central();
        }
        else{
            fichier = args[0];
        }
    }

    public void run(){
        boolean running = true;
        while(running){
            central.construireColonie(menu);

            central.distribuerColonie(menu);

            running = false;
        }

        menu.close();
    }
}
