package graphique;

import java.util.Scanner;

import systeme.entite.Colon;
import systeme.entite.Colonie;

public class Menu {
    private MenuInput mi;

    public Menu(){
        this.mi = new MenuInput(new Scanner(System.in)); 
    }

    public void afficherColonie(Colonie colonie){
        System.out.println(colonie.getColons());
    }

    public void afficherRelation(Colonie colonie){
        StringBuffer buffer = new StringBuffer("Colon & Enemis: ");

        buffer.append("[");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getEnnemis().toString());
            buffer.append(", ");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 2);
        }

        buffer.append("]");

        System.out.println(buffer.toString());
    }

    public void afficherPreference(Colonie colonie){
        StringBuffer buffer = new StringBuffer("Colon & Preference: ");

        buffer.append("(");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getPreference().toString());
            buffer.append(", ");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 2);
        }

        buffer.append("]");

        System.out.println(buffer.toString());
    }

    public void close(){
        mi.closeUserInput();
    }

    public MenuInput getMi() {
        return mi;
    }
}
