package graphique;

import java.util.ArrayList;
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

    public ArrayList<String> saisirPreferences(Colonie colonie){
        ArrayList<String> colon_preferences;
    
        while(true){
            colon_preferences = saisirSuite("[a-zA-Z](\\s+\\d+)+",
                                            "Entrez les préférences d'une colon (format: A 1 2 ...)", 
                                            "Erreur - Veuillez entrer le bon format.");

            // ça vérifie si colon existe
            if(!colonie.appartientColonie(colon_preferences.get(0))){
                System.out.println("Erreur - Entrez un colon existant. \n");
                continue;
            }

            // ça vérifie si une préférence n'est pas oublié
            if(colon_preferences.size() - 1 != colonie.getNbColons()){
                System.out.println("Erreur - Entrez le bon nombre de préférence. \n");
                continue;
            }

            //ça vérifie si les préférences sont toutes distinctes (ex: "B 1 1 2" non)
            if(!colonie.preferenceValide(colon_preferences)){
                System.out.println("Erreur - Entrez les bonnes préférences. \n");
                continue;
            }

            break;
        }
        
        return colon_preferences;
    }

    public ArrayList<String> saisirRelation(Colonie colonie){
        ArrayList<String> colon_colon;

        while(true){
            colon_colon = saisirSuite("[a-zA-Z]\\s+[a-zA-Z]",
                                    "Entrez les deux colons qui ne s'aime pas (format: A B)",
                                    "Erreur - Veuillez entrez le bon format.");

            // ça vérifie si les deux colons existent
            if(!colonie.appartientColonie(colon_colon.get(0)) ||
            !colonie.appartientColonie(colon_colon.get(1))){
                System.out.println("Erreur - Entrez des colons existants.");
                continue;
            }  
           
            // ça vérifie si les deux colons sont distincts
            if(colon_colon.get(0).equals(colon_colon.get(1))){
                System.out.println("Erreur - Entrez des colons distincts.");
                continue;
            } 
           
            break;
        }

        return colon_colon;
    }
}
