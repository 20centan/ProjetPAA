import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static void ajoutRelation(Colonie colonie, Scanner sc) {
		Colon colon1 = null, colon2 = null;
		String [] relation; 

		//Verif que les colons existent
        while(true){
            System.out.println("Veuillez specifiez les deux colons qui ne s'aiment pas (format: A B) :");
            relation = sc.nextLine().split(" ");

            colon1 = colonie.getColon(relation[0].charAt(0));
            colon2 = colonie.getColon(relation[1].charAt(0));
        
            if(colon1 == null || colon2 == null){
                System.out.println("Erreur - Entrez un colon existant");
                continue;
            }

            break;
        }
	}
	
	public static void ajoutPreference(Colonie colonie, Scanner sc) {
		String [] input;
        
        ArrayList<String> preference;
        Colon colon;

        while(true){
            System.out.println("Veuillez spécifiez les préférences du colon (format: A 1 2 3 ...) :");
            input = sc.nextLine().split(" ");
            
            preference = new ArrayList<>(Arrays.asList(input));
            preference.remove(0);
            
            if(preference.size() != colonie.getNbColons()) {
                System.out.println("Le nombre de ressources mises ne correspond pas au nombre de ressources attendu (Le nombre de ressource = le nombre de colons)");
                System.out.println("Veuillez ré-essayer avec le bon nombre.");
                
                continue;
            }

            colon = colonie.getColon(input[0].charAt(0));

            if(colon == null) {
                System.out.println("Veuillez ré-essayer avec des colons existant.");
                
                continue;
            }

            if(!verifListePref(preference)){
                System.out.println("Veuillez ré-essayer avec les bonnes préférences.");

                continue;
            }
        
            break;
        }

        //On mets maitenant les préférence dans le tableau preference du colon
        for(String element : preference){
            int index = Integer.valueOf(element);
            
            colon.ajoutPreference(colonie.getRessources().get(index));
        }
	}

	public static boolean verifListePref(ArrayList<String> preference) {
		int somme = 0;

        for(String element : preference){
            somme += Integer.parseInt(element);
        }

        int somme_attendu = (preference.size() * (preference.size() - 1)) / 2;
	
        return somme_attendu - somme == 0;
    }
	
	public static void echangeRessource(Colonie c) {
		//a finir
		
		Scanner entree =   new Scanner(System.in);
		System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
		
		System.out.println("Colon numéro 1 :");
		String entree1 = entree.next();
		//Verifier que ce colon existe
		
		System.out.println("Colon numéro 2 :");
		String entree2 = entree.next();
		//Verifier que ce colon existe
		
		//si les deux colons existent alors echanger leurs ressources sinon gerer erreur
	}

    // public int inserer_int(String message, String messageErreur, Scanner sc){    }

	public void construction(){
		Scanner scanner = new Scanner(System.in);
        Integer nbColons;

        while(true){
            System.out.println("De combien de colons dispose votre colonie ? (<=26)");

            try {
                nbColons = Integer.parseInt(scanner.nextLine()); 

                if(nbColons <= 0){
                    System.out.println("Erreur de syntaxe - Veuillez entrer un nombre >= 1. \n");
                }
    
                else if(26 < nbColons){
                    System.out.println("Erreur de syntaxe - Veuillez entrer un nombre <= 26. \n");
                }

                break;
    
            } catch (NumberFormatException e) {
                System.out.println("Erreur de syntaxe - Veuillez entrer un entier. \n");
            }
        }

		System.out.println("Initialisation de la colonie...");
		Colonie colonie = new Colonie(nbColons);
			
		System.out.println("Initialisation des ressources...");

		ArrayList<Ressource> ressources = new ArrayList<>();
		for(int i = 0; i < nbColons ; i ++){
			ressources.add(new Ressource());
		}
			
        Integer commande;
		boolean run = true;
			
		while(run) {
			System.out.println("Choisir une option:");
			System.out.println("[1] Ajouter une relation entre deux colons \n" + 
                                "[2] Ajouter les préférences d'un colon \n" + 
                                "[3] fin");
			
            try {
                commande = Integer.parseInt(scanner.nextLine()); 
    
            } catch (NumberFormatException e) {
                System.out.println("Erreur de syntaxe - Veuillez entrer un entier. \n");
                
                continue;
            }
				
			switch(commande) {
			    case 1: 
                    ajoutRelation(colonie, scanner);
			        break;
                    
                case 2: 
                    ajoutPreference(colonie, scanner);
                    break;
                
                case 3: 
                    //to do;
                    
                    run = false;
                    break;
                
                default: 
                    System.out.println("Commande invalide, veuillez ressayer.");
                }
		}		
        scanner.close();
	}
}

/**while(!(stop)) {
    System.out.println("Choisir une option:");
    System.out.println("[1] Echanger les ressources de deux colons \n [2] Afficher le nombre de colons jaloux \n [3] fin");
    option = entree.nextInt();
    
    switch(option) {
    case 1: echangeRessource(colonie);
    break;
    case 2: colonie.afficheJaloux();
    break;
    case 3: return;
    break;
    default: System.out.println("Commande invalide, veuillez ressayer.");
    }
    
    colonie.afficheRessources();
}**/