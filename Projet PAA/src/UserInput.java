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
            input = sc.nextLine();

            if(input.length() != 1 || !Character.isLetter(input.charAt(0))){
                System.out.println("Erreur - Veuillez entrez un lettre.");
                continue;
            }
            break;
        }

        return input.charAt(0);
    }

    // format
    public ArrayList<String> saisirSuite(String format){
        String input;
        ArrayList<String> trim_input;
        
        while(true){
            input = sc.nextLine();
            input = input.trim();

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

    public void closeUserInput(){
        sc.close();
    }
}