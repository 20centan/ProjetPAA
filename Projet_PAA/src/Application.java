import graphic.Menu;
import system.Core;

public class Application {
    private Menu menu;
    private Core core;

    public Application(){
        menu = new Menu();
        core = new Core();
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

    public Core getcore() {
        return core;
    }
}
