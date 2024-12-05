package systeme.operation;

import java.util.StringTokenizer;

import graphique.Menu;
import graphique.MenuSaisir;
import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;
import systeme.operation.fichier.FichierManager;


public abstract class ConstructionColonie {
    public static void run(Colonie colonie, Menu menu){
        construction(colonie, menu);
    }

    public static void run(Colonie colonie, String fichier){
        construction(colonie, fichier);
    }

    public static void optionRelation(Menu menu, MenuSaisir ms, Colonie colonie){
        menu.afficherRelation(colonie);

        String [] relation;
    
        relation = ms.saisirRelation(colonie);

        Colon colon1 = colonie.getColon(relation[0]);
        Colon colon2 = colonie.getColon(relation[1]);

        ajouterRelation(colon1, colon2);
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

        Ressource [] preferences = new Ressource[colon_preferences.length - 1];

        for(int i = 0; i < colon_preferences.length; i++){
            preferences[i] = colonie.getRessource(colon_preferences[i + 1]); 
        }

        ajouterPreferences(colon, preferences);
    }

    public static void ajouterRelation(Colon colon1, Colon colon2) {
        colon1.ajouterEnnemi(colon2);
	}
	
	public static void ajouterPreferences(Colon colon, Ressource [] preference) {
        for(Ressource nomRessource : preference){
            colon.ajouterPreference(nomRessource);
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


    public static void construction(Colonie colonie, String fichier){
        FichierManager manager = new FichierManager(fichier);
        
        manager.openReader();
        
        String data;
    
        while((data = manager.getNextData()) != null){
            StringTokenizer st = new StringTokenizer(data, "(,).");
    
            switch (st.nextToken()){
                case "colon":
                    colonie.ajouterColon(st.nextToken());
                    break;
    
                case "ressource":
                    colonie.ajouterRessources(st.nextToken());
                    break;
    
                case "deteste":
                    ajouterRelation(colonie.getColon(st.nextToken()), colonie.getColon(st.nextToken()));
                    break;
    
                case "preferences":
                    Colon colon = colonie.getColon(st.nextToken());

                    Ressource [] preference = new Ressource[st.countTokens()];
                    
                    int i = 0;
                    while(st.hasMoreTokens()){
                        preference[i++] = colonie.getRessource(st.nextToken());
                    }

                    ajouterPreferences(colon, preference);
            }
        }

        manager.closeReader();
    }

}
