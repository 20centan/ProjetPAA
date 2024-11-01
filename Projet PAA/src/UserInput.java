import java.util.Scanner;
import java.util.stream.Stream;


public class UserInput{
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

    public char saisirChar(String message, String messageError){
        String input;

        while(true){
            System.out.println(message);
            input = sc.nextLine();

            if(input.length() != 1 || !Character.isLetter(input.charAt(0))){
                System.out.println(messageError);
                continue;
            }
            break;
        }

        return input.charAt(0);
    }

    // format
    public String [] saisirSuite(String format){
        String input;
        String [] trim_input;
        
        while(true){
            input = sc.nextLine();

            // format: A 1 2 3
            if(!input.trim().matches(format)){
                System.out.println("Erreur - Entrez le bon format.");
                continue;
            }
        
            trim_input = Stream.of(input.split(" ")).filter(w -> !w.isEmpty()).toArray(String[]::new);
        
            break;
        }

        return trim_input;
    }


    public void closeUserInput(){
        sc.close();
    }
}