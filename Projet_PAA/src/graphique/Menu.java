package graphique;

import java.util.Scanner;

import systeme.entite.Colon;
import systeme.entite.Colonie;

public class Menu {
    private MenuSaisir ms;

    public Menu(){
        this.ms = new MenuSaisir(new Scanner(System.in)); 
    }

    /**
     * Affiche l'ensemble de la colonie passée en paramètre
     * @param colonie
     */
    public void afficherColonie(Colonie colonie){
        System.out.println(colonie.getColons());
    }

    /**
     * Affiche les relations entre les colons d'une colonie passée en paramètre
     * @param colonie
     */
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

    /**
     * Affiche l'ensemble des préférences de chaque colon d'une colonie
     * @param colonie
     */
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

    /**
     * Affiche la ressource de chaque colon dans la colonie
     * @param colonie
     */
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

    /**
     * Séparateur graphique pour le menu textuel
     * @param tour
     */
    public void afficherSeparateur(int tour){
        System.out.println("\n====================[" + tour + "]====================\n");
    }

    /**
     * Permet de fermer le Scanner de notre classe
     */
    public void close(){
        ms.closeSaisir();
    }

    /**
     * Getter de MenuSaisir
     * @return MenuSaisir
     */
    public MenuSaisir getMs() {
        return ms;
    }
}
