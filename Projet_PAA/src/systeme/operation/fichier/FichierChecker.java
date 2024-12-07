package systeme.operation.fichier;

import java.util.HashMap;
import java.util.StringTokenizer;

public class FichierChecker{
    private int nbColon;
    private int nbRessource;
    private HashMap<String, FichierEtat> memoire; // permet de stocker les colons et les ressources pour les vérifications

    private int positionFichier;
    
    private FichierEtat etat;

    public FichierChecker(){
        nbColon = 0;
        nbRessource = 0;
        memoire = new HashMap<>(); // la mémoire permet de tracker les dernières lignes visitées, ça permet de vérifier que chaque ligne est unique. 

        // position de départ: ligne 1 du fichier
        positionFichier = 1;

        etat = FichierEtat.COLON;
    }

    
    private interface CheckLigne {
        void check(String ligne) throws FichierException;
    }
    
    private CheckLigne[] checkLigne = new CheckLigne[] {

        new CheckLigne() {public void check(String ligne) throws FichierException {checkEtat(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkSyntaxe(ligne, etat.getRegex());}},
    };

    // appel tous les vérifications pour une ligne
    public void check(String ligne) throws FichierException{
        for(CheckLigne checker : checkLigne){
            changerEtat(ligne);

            checker.check(ligne);

            ajouterEnMemoire(ligne);

        }
        positionFichier++;
    }

    private interface CheckManquant{
        void check() throws FichierException;
    }

    private CheckManquant[] checkManquant = new CheckManquant[]{
        new CheckManquant() {public void check() throws FichierException {checkColonRessource();}}
    };


    public void check() throws FichierException{
        for(CheckManquant checker : checkManquant){
            checker.check();
        }
    }


    public void checkColon(String ligne) throws FichierException{
        // verifier que le nom est unique

    }

    public void checkRessource(String ligne) throws FichierException{
        if(etat != FichierEtat.RESSOURCE){
            return;
        }
        
        StringTokenizer st = new StringTokenizer(ligne, "()."); 
        // st = [ressource, valeur]

        st.nextToken(); // pour vider le premier tokken 
        String valeur = st.nextToken();
        
        if(memoire.get(valeur) == FichierEtat.RESSOURCE){
            throw new FichierException("La ressource existe déjà.", positionFichier, ligne);
        }

        if(memoire.get(valeur) == FichierEtat.COLON){
            throw new FichierException("Le nom de la ressource est un Colon.", positionFichier, ligne);
        }
    }

    public void checkDesteste(String ligne) throws FichierException{
        // vérifier que les deux noms sont des noms de colon
    }

    public void checkPreference(String ligne) throws FichierException{
        // vérifier le bon nombre de paramètre
        // vérifier que le premier nom est un colon et que le reste c'est des ressources existantes
    }

    public void checkColonPreference(){
        // vérifier le bon nombre de préférence/ tous les colons ont une préférence

    }

    public void checkSyntaxe(String ligne, String regex) throws FichierException{
        if(!ligne.matches(regex)){
            throw new FichierException("La syntaxe incorrecte.", positionFichier, ligne);
        }
    }
    
    public void checkColonRessource() throws FichierException{
        if(etat == FichierEtat.DETESTE && nbColon != nbRessource){
            throw new FichierException("Nombre de Colon et ressource incorrect", positionFichier);
        }
    }

    public void checkEtat(String ligne) throws FichierException{
        String ligneEtat = ligne.substring(0, ligne.indexOf("("));

        // verifier si la 1ère ligne est un colon
        if(positionFichier == 1 && !ligneEtat.equals("colon")){
            throw new FichierException("L'ordre d'élément est incorrect.", positionFichier, ligne);
        }

        if(!ligneEtat.equals(etat.toString())){
            throw new FichierException("L'ordre d'élément est incorrect.", positionFichier, ligne);
        }
    }

    public void ajouterEnMemoire(String ligne){
        StringTokenizer st = new StringTokenizer(ligne, "()."); 
        
        //cherche [colon, nomColon] ou [ressource, nomRessource]
        FichierEtat ligneEtat = switch(st.nextToken()){
            case "colon" -> FichierEtat.COLON;
            case "ressource" -> FichierEtat.RESSOURCE;
            default -> null;
        };

        if(ligneEtat == FichierEtat.COLON || ligneEtat == FichierEtat.RESSOURCE){
            memoire.put(st.nextToken(), ligneEtat);
        }
    }


    public void changerEtat(String ligne){
        String ligneEtat = ligne.substring(0, ligne.indexOf("("));

        if(!ligneEtat.equals(etat.getName())){
            switch(etat) {
                case FichierEtat.COLON:
                    etat = FichierEtat.RESSOURCE;
                    break;
                
                case FichierEtat.RESSOURCE:
                    etat = FichierEtat.DETESTE;
                    break;
                
                case FichierEtat.DETESTE:
                    etat = FichierEtat.PREFERENCES;
                    break;
    
                default:
                    etat = FichierEtat.FINFICHIER;
                    break;
            }
        }
    }
}
