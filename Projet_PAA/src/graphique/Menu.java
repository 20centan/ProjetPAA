package graphique;

import java.util.Scanner;

import systeme.entite.Colon;
import systeme.entite.Colonie;

public class Menu {
    private MenuSaisir ms;

    public Menu(){
        this.ms = new MenuSaisir(new Scanner(System.in)); 
    }

    public void afficherColonie(Colonie colonie){
        System.out.println(colonie.getColons());
    }

    public void afficherRelation(Colonie colonie){
        StringBuffer buffer = new StringBuffer("\nColon & Enemis: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getEnnemis().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString());
    }

    public void afficherPreference(Colonie colonie){
        StringBuffer buffer = new StringBuffer("\nColon & Preference: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getPreference().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString());
    }

    public void afficherRessource(Colonie colonie){
        StringBuffer buffer = new StringBuffer("\nColon & Ressource: \n");

        for(Colon colon : colonie.getColons()){
            buffer.append(colon + ": " + colon.getRessource().toString() + "\n");
        }
        
        if(!colonie.getColons().isEmpty()){
            buffer.setLength(buffer.length() - 1);
        }

        System.out.println(buffer.toString());
    }

    public void afficherSeparateur(int tour){
        System.out.println("\n====================[" + tour + "]====================\n");
    }

    public void close(){
        ms.closeSaisir();
    }

    public MenuSaisir getMs() {
        return ms;
    }
}
