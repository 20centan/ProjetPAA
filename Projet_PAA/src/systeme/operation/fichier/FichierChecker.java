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
        new CheckLigne() {public void check(String ligne) throws FichierException {checkColon(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkRessource(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkDeteste(ligne);}},
    };

    // appel tous les vérifications pour une ligne
    public void check(String ligne) throws FichierException{
        changerEtat(ligne);

        for(CheckLigne checker : checkLigne){
            checker.check(ligne);
        }

        ajouterEnMemoire(ligne);

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
        // ne lance pas la vérification si la ligne n'est pas un colon(...).
        if(etat != FichierEtat.COLON){
            return;
        }
        
        // st = [colon, valeur]
        StringTokenizer st = new StringTokenizer(ligne, "()."); 

        // pour vider le premier tokken 
        st.nextToken(); 

        checkColonExiste(st.nextToken(), ligne);

        nbColon++;
    }

    public void checkColonExiste(String nomColon, String ligne) throws FichierException{
        if(memoire.get(nomColon) == FichierEtat.COLON){
            throw new FichierException("Le colon existe déjà.", positionFichier, ligne);
        }
    }

    public void checkRessource(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas un ressource(...).
        if(etat != FichierEtat.RESSOURCE){
            return;
        }
        
        // st = [ressource, valeur]
        StringTokenizer st = new StringTokenizer(ligne, "()."); 

        // pour vider le premier tokken 
        st.nextToken();

        String nomRessource = st.nextToken();
        
        checkRessourceExiste(nomRessource, ligne);
        checkRessourceCorrect(nomRessource, ligne);

        nbRessource++;
    }

    public void checkRessourceExiste(String nomRessource, String ligne) throws FichierException{
        if(memoire.get(nomRessource) == FichierEtat.RESSOURCE){
            throw new FichierException("La ressource existe déjà.", positionFichier, ligne);
        }
    }

    public void checkRessourceCorrect(String nomRessource, String ligne) throws FichierException{
        if(memoire.get(nomRessource) == FichierEtat.COLON){
            throw new FichierException("Le nom de la ressource est un colon.", positionFichier, ligne);
        }
    }


    public void checkDeteste(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas un deteste(...).
        if(etat != FichierEtat.DETESTE){
            return;
        }
        
        // st = [deteste, colon1, colon2]
        StringTokenizer st = new StringTokenizer(ligne, "(,)."); 

        // pour vider le premier tokken 
        st.nextToken(); 

        String colon1 = st.nextToken();
        String colon2 = st.nextToken();
        
        if(memoire.get(colon1) != FichierEtat.COLON){
            throw new FichierException(colon1 + " n'est pas un colon.", positionFichier, ligne);
        }

        if(memoire.get(colon2) != FichierEtat.COLON){
            throw new FichierException(colon2 + " n'est pas un colon.", positionFichier, ligne);
        }

        if(colon1.equals(colon2)){
            throw new FichierException("Le nom des deux colons est identique.", positionFichier, ligne);
        }
    }


    public void checkPreference(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas un deteste(...).
        if(etat != FichierEtat.PREFERENCES){
            return;
        }
        
        // st = [preference, colon, ressource1, ressource2, ...]
        StringTokenizer st = new StringTokenizer(ligne, "(,)."); 

        // pour vider le premier tokken 
        st.nextToken(); 

        String colon = st.nextToken();
        
        if(memoire.get(colon) != FichierEtat.COLON){
            throw new FichierException(colon + " n'est pas un colon.", positionFichier, ligne);
        }
        
        if(st.countTokens() != nbRessource){
            throw new FichierException("Une ressource est manquante.", positionFichier, ligne);
        }
        
        while(st.hasMoreTokens()) {
            
        }
        

        if(colon1.equals(colon2)){
            throw new FichierException("Le nom des deux colons est identique.", positionFichier, ligne);
        }
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
            throw new FichierException("Nombre de colon et ressource incorrect", positionFichier);
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
