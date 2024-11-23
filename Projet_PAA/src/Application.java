import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central central;

    public Application(){
        menu = new Menu();
        central = new Central();
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

    public Menu getMenu() {
        return menu;
    }

    public Central getCentral() {
        return central;
    }
}
