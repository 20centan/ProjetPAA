package systeme.operation.auto;

import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;

import systeme.operation.auto.Fichier.FichierManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Construction {
    public static void run(Colonie colonie){
        construction(colonie);
    }

    public static void ajoutRelation(Colon colon1, Colon colon2) {
        colon1.ajoutEnnemi(colon2);
    }

    public static void ajoutPreferences(Colon colon, Ressource [] preference) {
        for(Ressource nomRessource : preference){
            colon.ajoutPreference(nomRessource);
        }
	}

    public static void construction(Colonie colonie){
        
    }

    public static void lectureFichier(String fichier, Colonie colonie){
        FichierManager fileManager = new FichierManager(fichier);

        String data;

        while((data = fileManager.getNextData()) != null){
            StringTokenizer st = new StringTokenizer(data, "(,).");

            switch (st.nextToken()){
                case "colon":
                    colonie.ajouterColon(st.nextToken());
                    break;

                case "ressource":
                    colonie.ajoutRessources(st.nextToken());
                    break;

                case "deteste":
                    ajoutRelation(colonie.getColon(st.nextToken()),colonie.getColon(st.nextToken()));
                    break;

                case "preferences":
                    ArrayList<String> liste = new ArrayList<>();
                    Colon colon = colonie.getColon(st.nextToken());
                    
                    while(st.hasMoreTokens()){
                        liste.add(st.nextToken());
                    }
                    
                    ajoutPreferences(colon, liste);
            }
        }

    }


}
