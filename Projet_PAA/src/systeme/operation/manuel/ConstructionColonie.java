package systeme.operation.manuel;


import graphique.Menu;
import graphique.MenuSaisir;
import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;

import java.util.Arrays;
import java.util.List;

public abstract class ConstructionColonie {
    public static void run(Colonie colonie, Menu menu){
        construction(colonie, menu);
    }

    public static void optionRelation(Menu menu, MenuSaisir ms, Colonie colonie){
        menu.afficherRelation(colonie);

        String [] relation;
    
        relation = ms.saisirRelation(colonie);

        Colon colon1 = colonie.getColon(relation[0]);
        Colon colon2 = colonie.getColon(relation[1]);

        ajoutRelation(colon1, colon2);
    }

    public static void optionPreference(Menu menu, MenuSaisir ms, Colonie colonie){
        menu.afficherPreference(colonie);

        String [] colon_preferences = ms.saisirPreferences(colonie);

        // On récupère le colon
        Colon colon = colonie.getColon(colon_preferences[0]);

        // Si l'utilisateur veut refaire les préférences d'un colon
        if(!colon.getPreference().isEmpty()){
            colon.viderPreference();
        }

        String [] preference = Arrays.copyOfRange(colon_preferences, 1, colon_preferences.length);

        ajoutPreferences(colon, preference, colonie.getRessources());
    }

    public static void ajoutRelation(Colon colon1, Colon colon2) {
        colon1.ajoutEnnemi(colon2);
	}
	
	public static void ajoutPreferences(Colon colon, String [] preference, List<Ressource>colonieRessource) {
        Ressource objet;
        
        for(String nomRessource : preference){
            //objet = colonieRessource.stream().filter(ressource -> nomRessource.equals(ressource.getNom()))
            //.findFirst()
            //.orElse(null);
            
            colon.ajoutPreference(nomRessource);
        }
	}


	public static void construction(Colonie colonie, Menu menu){
        MenuSaisir ms = menu.getMs();

        int nbColons;

        while(true){
            nbColons = ms.saisirInt("De combien de colons dispose votre colonie ? (<=26)",
                                    "Erreur - Veuillez entrez un bon nombre de colons.");

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
                                "[3] fin",
                                "Erreur - Commande invalide.")) {
			    case 1: 
                    optionRelation(menu, ms, colonie);
			        break;
                    
                case 2: 
                    optionPreference(menu, ms, colonie);
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
                    break;
            }

            tour++;
        }

        System.out.println("\nFin de la construction.");
    }


}
