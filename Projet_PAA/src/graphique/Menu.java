package graphic;

import java.util.Scanner;

import system.entite.Colonie;

public class Menu {
    private MenuInput mi;

    public Menu(){
        this.mi = new MenuInput(new Scanner(System.in)); 
    }

    public void afficherColonie(Colonie colonie){
        System.out.println(colonie.getColons());
    }

    public void close(){
        mi.closeUserInput();
    }

    public MenuInput getMi() {
        return mi;
    }
}
