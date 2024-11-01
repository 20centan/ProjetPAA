import java.util.Scanner;



public class MenuInput extends UserInput{
    public MenuInput(Scanner sc){
        super(sc);
    }

    public Colon saisirColon(Colonie colonie){
        char character;
        
        while(true){
            character = saisirChar("Entrez un colon: ", "Erreur - Veuillez entrez un lettre.");
            character = Character.toUpperCase(character);

            if(colonie.appartientColonie(character)){
                break;
            }
            System.out.println("Erreur - Entrez un colon existant");
        }

        return colonie.getColon(character);
    }

    public String [] saisirPreferences(Colonie colonie){
        String [] colon_preferences;
    
        while(true){
            colon_preferences = saisirSuite("[a-zA-Z](\\s+\\d+)+");

            if(colonie.appartientColonie(colon_preferences[0].charAt(0))){
                System.out.println("Erreur - Entrez un colon existant.");
                continue;
            } 
            if(colon_preferences.length - 1 != colonie.getNbColons()){
                System.out.println("Erreur - Entrez le bon nombre de préférence.");
                continue;
            }
            break;
        }
        
        return colon_preferences;
    }

    public char [] saisirRelation(Colonie colonie){
        String [] colon_colon;

        while(true){
            colon_colon = saisirSuite("[a-zA-Z]\\s+[a-zA-Z]");

            if(colonie.appartientColonie(colon_colon[0].charAt(0)) ||
            colonie.appartientColonie(colon_colon[1].charAt(0))){
                System.out.println("Erreur - Entrez des colons existants.");
                continue;
            }  
           
            if(colon_colon[0] == colon_colon[1]){
                System.out.println("Erreur - Entrez des colons distincts.");
                continue;
            } 
           
            break;
        }

        return new char[] {colon_colon[0].charAt(0), colon_colon[1].charAt(0)};
    }
}
