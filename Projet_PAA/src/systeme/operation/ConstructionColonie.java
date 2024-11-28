package systeme.operation;


import java.util.ArrayList;

import graphique.Menu;
import graphique.MenuSaisir;
import systeme.entite.Colon;
import systeme.entite.Colonie;

public abstract class ConstructionColonie {
    public static void run(Colonie colonie, Menu menu){
        construction(colonie, menu);
    }

    public static void ajoutRelation(Colonie colonie, MenuSaisir ms) {
		ArrayList<String> relation; 

        relation = ms.saisirRelation(colonie);
		
        Colon colon1 = colonie.getColon(relation.get(0).charAt(0));
        Colon colon2 = colonie.getColon(relation.get(1).charAt(0));

        colon1.ajoutEnnemi(colon2);
	}
	
	public static void ajoutPreferences(Colonie colonie, MenuSaisir ms) {
        ArrayList<String> colon_preferences = ms.saisirPreferences(colonie);

        // On récupère le colon
        Colon colon = colonie.getColon(colon_preferences.get(0).charAt(0));

        //On mets maitenant les préférence dans le tableau preference du colon
        for(String element : colon_preferences.subList(1, colon_preferences.size())){
            int index = Integer.valueOf(element) - 1;
            
            colon.ajoutPreference(colonie.getRessources().get(index));
        }
	}


	public static void construction(Colonie colonie, Menu menu){
        MenuSaisir ms = menu.getMs();

        int nbColons;

        while(true){
            nbColons = ms.saisirInt("De combien de colons dispose votre colonie ? (<=26)");

            if(nbColons <= 0 || 26 < nbColons){
                continue;
            }
            break;
        }

		System.out.println("\nInitialisation de la colonie...");
		System.out.println("Initialisation des ressources...");
		colonie.initialisationColonie(nbColons);

        int tour = 0;
		boolean lancer = true;
		while(lancer) {
            menu.afficherSeparateur(tour);

            switch(ms.saisirInt("Choisir une option: \n" +
                                "[1] Ajouter une relation entre deux colons \n" + 
                                "[2] Ajouter les préférences d'un colon \n" + 
                                "[3] fin")) {
			    case 1: 
                    menu.afficherRelation(colonie);
                    
                    ajoutRelation(colonie, ms);
			        break;
                    
                case 2: 
                    menu.afficherPreference(colonie);
                   
                    ajoutPreferences(colonie, ms);
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

            tour++;
        }

        System.out.println("\nFin de la construction.");
    }
}
