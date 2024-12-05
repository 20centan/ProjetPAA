import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central central;

    public Application(String [] args){
        menu = new Menu();

        if(args.length != 0){
            central = new Central(args[0]);
        }
        else{
            central = new Central();
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
