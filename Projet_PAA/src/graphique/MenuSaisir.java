package graphique;

import java.util.Arrays;
import java.util.Scanner;

import systeme.entite.Colon;
import systeme.entite.Colonie;


public class MenuSaisir extends Saisir{
    public MenuSaisir(Scanner sc){
        super(sc);
    }

    public Colon saisirColon(Colonie colonie){
        String character;
        
        while(true){
            character = saisirString("Entrez un colon: ",
                                    "Erreur - Veuillez entrez un nom");

            if(!colonie.appartientColonie(character)){
                System.out.println("Erreur - Entrez un colon existant");
                continue;
            }

            break;
        }

        return colonie.getColon(character);
    }

    public String [] saisirPreferences(Colonie colonie){
        String [] colon_preferences;
    
        while(true){
            colon_preferences = saisirSuite("[a-zA-Z](\\s+\\d+)+",
                                            "Entrez les préférences d'une colon (format: A 1 2 ...)", 
                                            "Erreur - Veuillez entrer le bon format.");

            // ça vérifie si colon existe
            if(!colonie.appartientColonie(colon_preferences[0])){
                System.out.println("Erreur - Entrez un colon existant. \n");
                continue;
            }

            //ça vérifie préférence
            System.out.println(colon_preferences + " " + colonie.getRessources());
            if(!colonie.preferenceValide(Arrays.copyOfRange(colon_preferences, 1, colon_preferences.length))){
                System.out.println("Erreur - Entrez les bonnes préférences. \n");
                continue;
            }

            break;
        }
        
        return colon_preferences;
    }

    public String [] saisirRelation(Colonie colonie){
        String [] colon_colon;

        while(true){
            colon_colon = saisirSuite("[a-zA-Z]\\s+[a-zA-Z]",
                                    "Entrez les deux colons qui ne s'aime pas (format: A B)",
                                    "Erreur - Veuillez entrez le bon format.");

            // ça vérifie si les deux colons existent
            if(!colonie.appartientColonie(colon_colon[0]) ||
            !colonie.appartientColonie(colon_colon[1])){
                System.out.println("Erreur - Entrez des colons existants.");
                continue;
            }  
           
            // ça vérifie si les deux colons sont distincts
            if(colon_colon[0].equals(colon_colon[1])){
                System.out.println("Erreur - Entrez des colons distincts.");
                continue;
            } 
           
            break;
        }

        return colon_colon;
    }
}
