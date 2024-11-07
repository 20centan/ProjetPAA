package systeme.operation;


import java.util.ArrayList;

import graphique.Menu;
import graphique.MenuInput;
import systeme.entite.Colon;
import systeme.entite.Colonie;

public abstract class ConstructionColonie {
    public static void run(Colonie colonie, Menu menu){
        construction(colonie, menu);
    }

    public static void ajoutRelation(Colonie colonie, MenuInput mi) {
		ArrayList<String> relation; 

        relation = mi.saisirRelation(colonie);
		
        Colon colon1 = colonie.getColon(relation.get(0).charAt(0));
        Colon colon2 = colonie.getColon(relation.get(1).charAt(0));

        colon1.ajoutEnnemi(colon2);
	}
	
	public static void ajoutPreferences(Colonie colonie, MenuInput mi) {
        ArrayList<String> colon_preferences = mi.saisirPreferences(colonie);

        // On récupère le colon
        Colon colon = colonie.getColon(colon_preferences.get(0).charAt(0));

        //On mets maitenant les préférence dans le tableau preference du colon
        for(String element : colon_preferences.subList(1, colon_preferences.size())){
            int index = Integer.valueOf(element) - 1;
            
            colon.ajoutPreference(colonie.getRessources().get(index));
        }
	}


	public static void construction(Colonie colonie, Menu menu){
        MenuInput mi = menu.getMi();

        int nbColons;

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
		colonie.initialisationColonie(nbColons);
        System.out.println();

		boolean lancer = true;
		while(lancer) {
            menu.afficherSeparateur();

            switch(mi.saisirInt("Choisir une option: \n" +
                                "[1] Ajouter une relation entre deux colons \n" + 
                                "[2] Ajouter les préférences d'un colon \n" + 
                                "[3] fin")) {
			    case 1: 
                    System.out.println();
                    menu.afficherRelation(colonie);
                    
                    ajoutRelation(colonie, mi);
			        break;
                    
                case 2: 
                    System.out.println();
                    menu.afficherPreference(colonie);
                   
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
    }
}
