import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public static void ajoutRelation(Colonie colonie, MenuInput mi) {
		ArrayList<String> relation; 

        System.out.println("Colons: " + colonie.getColons().toString());
        relation = mi.saisirRelation(colonie);
		
        Colon colon1 = colonie.getColon(relation.get(0).charAt(0));
        Colon colon2 = colonie.getColon(relation.get(1).charAt(0));

        colon1.ajoutEnnemi(colon2);
	}
	
	public static void ajoutPreferences(Colonie colonie, MenuInput mi) {
        System.out.println("Colons: " + colonie.getColons().toString());
        System.out.println("Ressources: " + colonie.getRessources().toString());

        ArrayList<String> colon_preferences = mi.saisirPreferences(colonie);

        // On récupère le colon
        Colon colon = colonie.getColon(colon_preferences.get(0).charAt(0));

        //On mets maitenant les préférence dans le tableau preference du colon
        for(String element : colon_preferences.subList(1, colon_preferences.size())){
            int index = Integer.valueOf(element) - 1;
            
            colon.ajoutPreference(colonie.getRessources().get(index));
        }
	}

	public static void echangeRessource(Colonie colonie, MenuInput mi) {
        System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
	
        Colon colon1 = mi.saisirColon(colonie);
        Colon colon2 = mi.saisirColon(colonie);
        
        Ressource tmp = colon1.getRessource();
        colon1.ajoutRessource(colon2.getRessource());
        colon2.ajoutRessource(tmp);
	}


	public static void construction(){
        MenuInput mi = new MenuInput(new Scanner(System.in));
        
        Integer nbColons;

        while(true){
            nbColons = mi.saisirInt("De combien de colons dispose votre colonie ? (<=26)");

            if(nbColons <= 0 || 26 < nbColons){
                continue;
            }
            break;
        }

        System.out.println();
		System.out.println("Initialisation de la colonie...");
		System.out.println("Initialisation des ressources...");
		Colonie colonie = new Colonie(nbColons);
        System.out.println();

		boolean lancer = true;
		while(lancer) {
			System.out.println("Choisir une option:");
			System.out.println("[1] Ajouter une relation entre deux colons \n" + 
                                "[2] Ajouter les préférences d'un colon \n" + 
                                "[3] fin");
			
			switch(mi.saisirInt("")) {
			    case 1: 
                    System.out.println();
                    ajoutRelation(colonie, mi);
			        break;
                    
                case 2: 
                    System.out.println();
                    ajoutPreferences(colonie, mi);
                    break;
                
                case 3: 
                    if(colonie.preferenceVide()){
                        System.out.println("Les préférences d'un colon sont manquantes.");
                    }
                    else{
                        lancer = false;
                    }
                    break;
                
                default: 
                    System.out.println("Commande invalide, veuillez ressayer.");
                }
                System.out.println();
		}		
        mi.closeUserInput();
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