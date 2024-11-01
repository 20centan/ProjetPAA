import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Menu {
    public static void ajoutRelation(Colonie colonie, MenuInput mi) {
		char [] relation; 

        relation = mi.saisirRelation(colonie);
		
        Colon colon1 = colonie.getColon(relation[0]);
        Colon colon2 = colonie.getColon(relation[1]);

        colon1.ajoutEnnemi(colon2);
	}
	
	public static void ajoutPreferences(Colonie colonie, MenuInput mi) {
        String [] colon_preferences = mi.saisirPreferences(colonie);
        
        Colon colon = colonie.getColon(colon_preferences[0].charAt(0));

        //On mets maitenant les préférence dans le tableau preference du colon
        for(String element : colon_preferences[1:]){
            int index = Integer.valueOf(element) - 1;
            
            colon.ajoutPreference(colonie.getRessources().get(index));
        }
	}

	public static boolean verifListePref(ArrayList<String> preference) {
		int somme = 0;

        for(String element : preference){
            somme += Integer.parseInt(element);
        }

        int somme_attendu = (preference.size() * (preference.size() + 1)) / 2;
	
        return somme_attendu - somme == 0;
    }
	
	public static void echangeRessource(Colonie colonie, MenuInput mi) {
        Colon colon1, colon2;

        System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
	
        colon1 = mi.saisirColon(colonie);
        colon2 = mi.saisirColon(colonie);
        
        Ressource tmp = colon1.getRessource();
        colon1.ajoutRessource(colon2.getRessource());
        colon2.ajoutRessource(tmp);
	}

    public static boolean verifPrefColonie(Colonie colonie){
        for(Colon colon : colonie.getColons()){
            if(colon.getPreference().isEmpty()){
                return false;
            }
        }

        return true;
    }

    // public int inserer_int(String message, String messageErreur, Scanner sc){    }

	public static void construction(){
		Scanner scanner = new Scanner(System.in);
        MenuInput mi = new MenuInput(scanner);
        
        Integer nbColons;

        while(true){
            nbColons = mi.saisirInt("De combien de colons dispose votre colonie ? (<=26)");

            if(nbColons < 0 || 26 < nbColons){
                continue;
            }
            break;
        }

		System.out.println("Initialisation de la colonie...");
		Colonie colonie = new Colonie(nbColons);
			
		System.out.println("Initialisation des ressources...");

		ArrayList<Ressource> ressources = new ArrayList<>();
		for(int i = 0; i < nbColons ; i ++){
			ressources.add(new Ressource());
		}
			
		boolean run = true;
		while(run) {
			System.out.println("Choisir une option:");
			System.out.println("[1] Ajouter une relation entre deux colons \n" + 
                                "[2] Ajouter les préférences d'un colon \n" + 
                                "[3] fin");
			
			switch(mi.saisirInt("")) {
			    case 1: 
                    ajoutRelation(colonie, mi);
			        break;
                    
                case 2: 
                    ajoutPreferences(colonie, mi);
                    break;
                
                case 3: 
                    if(!verifPrefColonie(colonie)){
                        System.out.println("Les préférences d'un colon est manquantes.");
                    }
                    else{
                        run = false;
                    }
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