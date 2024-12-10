package systeme.operation;

import graphique.Menu;
import graphique.MenuSaisir;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import systeme.entite.Colon;
import systeme.entite.Colonie;
import systeme.entite.Ressource;
import systeme.operation.fichier.FichierManager;

public abstract class DistributionColonie {
    public static void run(Colonie colonie, Menu menu){
        distribution(colonie, menu);
    }

    public static void run(Colonie colonie, Menu menu, String fichier){
        distribution(colonie, menu, fichier);
    }

    public static void echangeRessource(Colonie colonie, MenuSaisir ms) {
        Colon colon1 = ms.saisirColon(colonie);
        Colon colon2 = ms.saisirColon(colonie);
        
        Ressource tmp = colon1.getRessource();
        colon1.ajouterRessource(colon2.getRessource());
        colon2.ajouterRessource(tmp);

	}

    public static void echangeRessource(Colon colon1,Colon colon2) {
        Ressource tmp = colon1.getRessource();
        colon1.ajouterRessource(colon2.getRessource());
        colon2.ajouterRessource(tmp);

	}

    public static void distribuer(Colonie colonie) { //solutionNaive
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
        System.out.println("\nLe nombre actuel de colons jaloux dans votre colonie est de " + calculJaloux(colonie) + ".");
    }

    public static void distribution(Colonie colonie, Menu menu){
        MenuSaisir ms = menu.getMs();
        
        System.out.println("\nDébut de la distribution des ressources dans la colonie...");        
        distribuer(colonie);
        System.out.println("Fin de la distribution des ressources dans la colonie.");

        int tour = 0;
        boolean lancer = true;
        while(lancer) {
            menu.afficherSeparateur(tour);

            switch(ms.saisirInt("Choisir une option: \n" +
            "[1] Echanger les ressources de deux colons \n" +
            "[2] Afficher le nombre de colons jaloux \n" +
            "[3] fin", 
            "Erreur - Commande invalide.")) {
                case 1:
                    menu.afficherRessource(colonie);
                    System.out.println();
                    
                    System.out.println("Veuillez specifiez les deux colons échangeant leurs ressources:");
                    echangeRessource(colonie, ms);

                    menu.afficherRessource(colonie);
                    break;

                case 2:
                    afficheJaloux(colonie);
                    break;

                case 3:
                    lancer = false;
                    break;

                default:
                    break;
            }

            tour++;
        }
    } 

    public static void distribution(Colonie colonie, Menu menu, String fichier){
        MenuSaisir ms = menu.getMs();

        System.out.println("\nDébut de la distribution des ressources dans la colonie...");
        distribuer(colonie);
        System.out.println("Fin de la distribution des ressources dans la colonie.");

        System.out.println("Voici les options qui vous sont maintenant proposés: \n");

        boolean lancer = true;
        while(lancer){
            switch(ms.saisirInt("Choisir une option: \n" +
                    "[1] Résolution automatique de la distribution de la colonie\n"+
                    "[2] Sauvegarder la solution actuelle \n" +
                    "[3] Fin","Erreur - Commande invalide")){
                case 1:
                    menu.afficherRessource(colonie);
                    resolutionAutomatique(colonie);
                    break;

                case 2:
                    String nomFichier = ms.saisirString("Rentrer le nom du fichier:","Nom de fichier invalide");
                    sauvegardeSolution(colonie,nomFichier);
                    break;

                case 3:
                    lancer = false;
                    break;

                default:
                    break;

            }
            lancer = false;
        }
    }

    private static void resolutionAutomatique(Colonie colonie){
        int max = 5; //Nombre de tours
        Colon colon1,colon2 = null;
        int solution1 = calculJaloux(colonie);
        boolean memeColon = true;

        for(int i = 0; i<max;i++){
            //Choix aléatoire d'un colon
            colon1 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(0,colonie.getNbColons()));

            //Vérification qu'on ne prends pas 2 fois le meme colon
            while(memeColon){
                colon2 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(0,colonie.getNbColons()));

                if(!(colon1.equals(colon2))){
                    memeColon = false;
                }
            }

            //Echange des ressources puis de nouveau on calcul nbJaloux
            echangeRessource(colon1,colon2);
            int solution2 = calculJaloux(colonie);

            //si inf on laisse alors la colonie comme ca sinon on réechange pour remettre comme avant
            if(solution2<solution1){
                solution1=solution2;
            }else{
                echangeRessource(colon1,colon2);
            }

        }
        System.out.println("Le nombre de jaloux est de "+solution1);
    }

    private static void sauvegardeSolution(Colonie colonie,String fichier){
        FichierManager fichierManager = new FichierManager(fichier);

        fichierManager.openWriter();

        for(Colon c : colonie.getColons()){
            String data = c.toString() + ":" + c.getRessource() + "\n";
            fichierManager.save(data);
        }

        fichierManager.closeWriter();
    }

}
