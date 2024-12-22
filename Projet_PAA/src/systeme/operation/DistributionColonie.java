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
    /**
     * Lance la distribution de la colonie à partir de la construction manuelle
     * @param colonie
     * @param menu
     */
    public static void run(Colonie colonie, Menu menu){
        distribution(colonie, menu);
    }

    /**
     * Lance la distribution de la colonie à partir de sa construction automatique depuis un fichier
     * @param colonie
     * @param menu
     * @param fichier
     */
    public static void run(Colonie colonie, Menu menu, String fichier){
        colonie.afficherColonie();
        distribution(colonie, menu, fichier);
    }

    /**
     * Méthode qui échange les ressources entre 2 colons depuis l'interface textuelle
     * @param colonie
     * @param ms
     */
    public static void echangeRessource(Colonie colonie, MenuSaisir ms) {
        Colon colon1 = ms.saisirColon(colonie);
        Colon colon2 = ms.saisirColon(colonie);
        
        Ressource tmp = colon1.getRessource();
        colon1.ajouterRessource(colon2.getRessource());
        colon2.ajouterRessource(tmp);

	}

    /**
     * Echange les ressources entre 2 colons passés en paramètre
     * @param colon1
     * @param colon2
     */
    public static void echangeRessource(Colon colon1,Colon colon2) {
        Ressource tmp = colon1.getRessource();
        colon1.ajouterRessource(colon2.getRessource());
        colon2.ajouterRessource(tmp);

	}

    /**
     * Distribution à partir de la solution naïve ( partie 1 )
     * @param colonie
     */
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

    /**
     * Calcule le nombre de colons jaloux dans la colonie
     * @param colonie
     * @return int
     */
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

    /**
     * Affiche le nombre de jaloux dans la colonie
     * @param colonie
     */
    public static void afficheJaloux(Colonie colonie){
        System.out.println("\nLe nombre actuel de colons jaloux dans votre colonie est de " + calculJaloux(colonie) + ".");
    }

    /**
     * Menu des options après la distribution des ressources ( à partir de la construction manuelle )
     * @param colonie
     * @param menu
     */
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

    /**
     * Menu des options après la construction automatique depuis un fichier
     * @param colonie
     * @param menu
     * @param fichier
     */
    public static void distribution(Colonie colonie, Menu menu, String fichier){
        MenuSaisir ms = menu.getMs();

        System.out.println("\nDébut de la distribution des ressources dans la colonie...");
        distribuer(colonie);
        System.out.println("Fin de la distribution des ressources dans la colonie.");

        System.out.println("Voici les options qui vous sont maintenant proposés: \n");

        colonie.setNbJaloux(calculJaloux(colonie));

        boolean lancer = true;
        while(lancer){
            switch(ms.saisirInt("Choisir une option: \n" +
                    "[1] Résolution automatique de la distribution de la colonie\n"+
                    "[2] Sauvegarder la solution actuelle \n" +
                    "[3] Fin","Erreur - Commande invalide")){
                case 1:
                    menu.afficherRessource(colonie);
                    System.out.println("Nombre de jaloux avant résolution : "+colonie.getNbJaloux());
                    resolutionAutoRecuitSimule(colonie, colonie.getNbJaloux());
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
        }
    }


    /**
     * Sauvegarde la solution actuelle dans un fichier texte
     * Automatisation de la recherche de solution par recuit simulé
     * @param colonie
     * @param nbJaloux
     */
    private  static void resolutionAutoRecuitSimule(Colonie colonie, int nbJaloux){
        Colon colon1,colon2 = null;
        boolean memeColon = true;

        int solutionOpti = nbJaloux;
        int solutionLocal = solutionOpti;

        if(solutionOpti==0){
            System.out.println("Aucun colons n'est jaloux, annulation de la résolution automatique...");
        }else{
            
            double temp = 100.0; //Initialisation de la température
            double cool = 0.99; //Facteur de refroidissement
            
            //tant que la temperature n'a pas atteint le seuil 0.001
            while(0.0001<temp){
                //10 est un choix arbitraire pour la boucle for
                for(int i = 0; i<10 ; i++){
                    //Choix aléatoire d'un colon
                    colon1 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(colonie.getNbColons()));

                    //Vérification qu'on ne prends pas 2 fois le meme colon
                    while(memeColon){
                        colon2 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(colonie.getNbColons()));

                        if(!(colon1.equals(colon2))){
                            memeColon = false;
                        }
                    }

                    //Echange des ressources puis de nouveau on calcul le nombre de jaloux
                    echangeRessource(colon1,colon2);
                    int solution2 = calculJaloux(colonie);

                    double delta = solutionLocal - solution2; //différence de coût entre les deux solutions
                    
                    //Si solution2<solution1 la solution2 devient solution1
                    if(0<=delta){
                        solutionLocal = solution2; //On recupère un minimum local

                        if(solutionLocal<=solutionOpti){
                            solutionOpti=solutionLocal;
                        }

                    }else{
                        double r = (double)(ThreadLocalRandom.current().nextLong(100))/100; //Variable aléatoire (compris entre 0 et 1 (exclus) qui servira de facteur d'influence pour la suite)

                        double facBoltz = Math.exp((delta)/temp); //Facteur de Boltzmann utilisant la règle de metropolis
                        
                        /**
                         * Si le chiffre aléatoire r est inférieur au facteur de Boltzmann
                         * bien qu'il y est plus de jaloux dans la solution2,
                         * la solution2 devient solutionLocal
                         * Cela permet d'explorer plus de possibilité et de ne pas
                         * s'enfermer dans un optimum local
                         */
                        if(r<facBoltz){
                            solutionLocal = solution2;
                        }else{
                            echangeRessource(colon1,colon2);
                        }
                    }
                }
                temp*=cool;
            }
            colonie.setNbJaloux(solutionOpti);
            System.out.println("Le nombre de jaloux est maintenant de "+solutionOpti);

        }
    }

    /**
     * Sauvegarde la solution actuelle dans un fichier texte
     * @param colonie
     * @param fichier
     */
    private static void sauvegardeSolution(Colonie colonie,String fichier){
        FichierManager fichierManager = new FichierManager(fichier);

        fichierManager.openWriter();

        for(Colon c : colonie.getColons()){
            String data = c.toString() + ":" + c.getRessource() + "\n";
            fichierManager.save(data);
        }

        fichierManager.closeWriter();
    }

    /**
     * Cette algorithme est l'implémentation du pseudocode donné dans le sujet
     *Je le mets en commentaire pour garder une archive
     * Automatisation de la recherche de solution
     * @param colonie
     */
    /**
    private static void resolutionAutomatique(Colonie colonie){
        int max = 5; //Nombre de tours
        Colon colon1,colon2 = null;
        int solution1 = calculJaloux(colonie);
        boolean memeColon = true;

        System.out.println("Nombre de jaloux avant résolution : "+solution1);

        for(int i = 0; i<max;i++){
            //Choix aléatoire d'un colon
            colon1 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(colonie.getNbColons()));

            //Vérification qu'on ne prends pas 2 fois le meme colon
            while(memeColon){
                colon2 = colonie.getColons().get(ThreadLocalRandom.current().nextInt(colonie.getNbColons()));

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
        System.out.println("Le nombre de jaloux est maintenant de "+solution1);
    }
    */
}
