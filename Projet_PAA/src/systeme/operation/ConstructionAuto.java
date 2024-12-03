package systeme.operation;

import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;

import java.util.List;

public abstract class ConstructionAuto {
    public static void run(Colonie colonie){
        //constructionauto(colonie,menu);
    }

    public static void ajoutRelation(Colon colon1, Colon colon2) {
        colon1.ajoutEnnemi(colon2);
    }

    public static void ajoutPreferences(Colon colon, String [] preference, List<Ressource> colonieRessource) {
        Ressource objet;

        for(String nomRessource : preference){
            objet = colonieRessource.stream().filter(ressource -> nomRessource.equals(ressource.getNom()))
                    .findFirst()
                    .orElse(null);

            colon.ajoutPreference(objet);
        }
    }


}
