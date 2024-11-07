package system.operation;

import java.util.Iterator;
import java.util.List;

import graphic.Menu;
import graphic.MenuInput;

import system.entity.Colon;
import system.entity.Colonie;
import system.entity.Ressource;

public abstract class DistributionColonie {
    public static void run(Colonie colonie, Menu menu){
        distribution(colonie, menu.getMi());
    }

    public static void echangeRessource(Colonie colonie, MenuInput mi) {
        System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
	
        Colon colon1 = mi.saisirColon(colonie);
        Colon colon2 = mi.saisirColon(colonie);
        
        Ressource tmp = colon1.getRessource();
        colon1.ajoutRessource(colon2.getRessource());
        colon2.ajoutRessource(tmp);

	}

    public static void distribuer(Colonie colonie, MenuInput mi) {
        System.out.println("Début de la distribution des ressources dans la colonie...");

        for (Colon colon : colonie.getColons()) {
            boolean attribuer = false;
            int i = 0;

            while (!(attribuer)) {
                if (colonie.getRessources().contains(colon.getPreference().get(i))) {
                    Iterator<Ressource> it = colonie.getRessources().iterator();
                    boolean trouver = false;
                    
                    while(it.hasNext() && !(trouver)){
                        Ressource ressource = it.next();
                        if (ressource.equals(colon.getPreference().get(i))) {
                            colon.setRessource(ressource);
                            colonie.getRessources().remove(ressource);
                            
                            attribuer = true;
                            trouver = true;
                        }
                    }
                }
                i++;
            }
        }

        System.out.println();
        System.out.println("Fin de la distribution des ressources dans la colonie");
    }

    public static int calculJaloux(Colonie colonie){
        int nbJaloux = 0;

        for(Colon c : colonie.getColons()){
            Ressource ressource = c.getRessource();
            List<Ressource> preference = c.getPreference();
            List<Colon> ennemis = c.getEnnemis();
            int rangRessource = preference.indexOf(ressource);

            //Si le colon recois sa premiere ressource on ne rentre pas dans le if car il ne sera jamais jaloux
            if(ressource != preference.get(0)){
                Boolean stop = false;
                int i = 0;

                while(!(stop) && i<ennemis.size()){
                    Colon ennemi = ennemis.get(i);
                    Ressource ressourceEnnemi = ennemi.getRessource();

                    int j = 0;
                    while(!(stop) && j <= rangRessource){
                        if(ressourceEnnemi.equals(preference.get(j))){
                            nbJaloux++;
                            stop = true;
                        }
                        j++;
                    }
                    i++;
                }        
            }
        }
        return nbJaloux;
    }

    public static void afficheJaloux(Colonie colonie){
        System.out.println("Le nombre actuel de colons jaloux dans votre colonie est de " + calculJaloux(colonie) +"\n");
    }

    public static void distribution(Colonie colonie, MenuInput mi){
        distribuer(colonie, mi);

        boolean lancer = true;

        while(lancer) {
            switch(mi.saisirInt("Choisir une option: \n" +
                    "[1] Echanger les ressources de deux colons \n" +
                    "[2] Afficher le nombre de colons jaloux \n" +
                    "[3] fin")) {
                case 1:
                    colonie.afficheColonie();
                    System.out.println();
                    echangeRessource(colonie,mi);
                    break;

                case 2:
                    System.out.println();
                    afficheJaloux(colonie);
                    break;

                case 3:
                    lancer = false;
                    break;
                default:
                    System.out.println("Commande invalide, veuillez ressayer.");
            }

        }
    } 
}
