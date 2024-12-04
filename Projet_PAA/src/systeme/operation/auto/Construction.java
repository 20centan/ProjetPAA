package systeme.operation.auto;

import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;

import systeme.operation.auto.Fichier.FichierManager;

import java.util.StringTokenizer;

public abstract class Construction {
    public static void run(Colonie colonie, String fichier){
        construction(colonie, fichier);
    }

    public static void ajouterRelation(Colon colon1, Colon colon2) {
        colon1.ajouterEnnemi(colon2);
    }

    public static void ajouterPreferences(Colon colon, Ressource [] preference) {
        for(Ressource ressource : preference){
            colon.ajouterPreference(ressource);
        }
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

    public static void main(String [] args){
        String fichier = "/home/roland/Documents/projets/2024-PAA/Projet_PAA/test.txt";

        Colonie colonie = new Colonie();

        Construction.run(colonie, fichier);

        // afficher colon
        for(Colon colon : colonie.getColons()){
            System.out.println(colon);
        }

        // afficher ressource
        System.out.println(colonie.getRessources());

        // afficher relation
        for(Colon colon : colonie.getColons()){
            System.out.println(colon + " " + colon.getEnnemis());
        }

        // afficher ressource (rien de base)
        for(Colon colon : colonie.getColons()){
            System.out.println(colon + " " + colon.getRessource());
        }
    }
}
