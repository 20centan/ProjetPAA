import graphique.Menu;
import systeme.Central;

public class Application {
    private Menu menu;
    private Central core;

    public Application(){
        menu = new Menu();
        core = new Central();
    }

    public void run(){
        boolean running = true;
        while(running){
            core.construireColonie(menu);

            core.distribuerColonie(menu);

            running = false;
        }

        menu.close();
    }

    public Menu getMenu() {
        return menu;
    }

    public Central getcore() {
        return core;
    }
}
