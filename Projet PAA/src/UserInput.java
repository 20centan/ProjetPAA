import java.util.Scanner;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public abstract class UserInput{
    private Scanner sc;

    public UserInput(Scanner sc){
        this.sc = sc;
    }

    public int saisirInt(String message){
        int input;

        while(true){
            System.out.println(message);
            System.out.print("> ");

            // ça vérifie si l'utilisateur a bien écrit un nombre
            try {
                input = Integer.parseInt(sc.nextLine()); 
                break;
    
            } catch (NumberFormatException e) {
                System.out.println("Erreur de syntaxe - Veuillez entrer un entier. \n");
            }
        }

        return input;
    }

    public char saisirChar(String message){
        String input;

        while(true){
            System.out.println(message);
            System.out.print("> ");
            input = sc.nextLine();

            // ça vérifie si l'utilisateur a bien écrit une lettre
            if(input.length() != 1 || !Character.isLetter(input.charAt(0))){
                System.out.println("Erreur - Veuillez entrez un lettre.");
                continue;
            }
            break;
        }

        return input.charAt(0);
    }

    public ArrayList<String> saisirSuite(String message, String format){
        String input;
        ArrayList<String> trim_input;
        
        while(true){
            System.out.println(message);
            System.out.print("> ");
            input = sc.nextLine();

            input = input.trim();
            
            // format = regex (expression régulière). ça vérifie si l'utilisateur a bien écrit l'input.
            if(!input.matches(format)){
                System.out.println("Erreur - Entrez le bon format.");
                continue;
            }
        
            // Permet de retirer les espaces inutils (ex input:" B 1   3   2  " -> {B, 1, 3, 2})
            trim_input = Stream.of(input.split(" ")).filter(w -> !w.isEmpty()).collect(Collectors.toCollection(ArrayList::new));;
        
            break;
        }

        return trim_input;
    }

    // permet de fermer le Scanner
    public void closeUserInput(){
        sc.close();
    }
}