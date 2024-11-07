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
        StringBuffer buffer = new StringBuffer("Colon & Enemis: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getEnnemis().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString() + "\n");
    }

    public void afficherPreference(Colonie colonie){
        StringBuffer buffer = new StringBuffer("Colon & Preference: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getPreference().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString() + "\n");
    }

    public void afficherRessource(Colonie colonie){
        StringBuffer buffer = new StringBuffer("Colon & Ressource: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getRessource().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString() + "\n");
    }

    public void afficherSeparateur(){
        System.out.println("====================[]====================\n");
    }

    public void close(){
        mi.closeUserInput();
    }

    public MenuInput getMi() {
        return mi;
    }
}
