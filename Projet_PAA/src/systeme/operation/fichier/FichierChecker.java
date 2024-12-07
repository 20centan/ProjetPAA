package systeme.operation.fichier;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
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
        new CheckLigne() {public void check(String ligne) throws FichierException {checkPreference(ligne);}},
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

        // vider le premier tokken 
        st.nextToken(); 

        checkColonExistePas(st.nextToken(), ligne);

        nbColon++;
    }

    public void checkColonExiste(String nomColon, String ligne) throws FichierException{
        if(!memoire.containsKey(nomColon)){
            throw new FichierException("Le colon n'existe pas.", positionFichier, ligne);
        }
    }

    public void checkColonExistePas(String nomColon, String ligne) throws FichierException{
        if(memoire.get(nomColon) != FichierEtat.COLON){
            throw new FichierException(nomColon + " existe déjà.", positionFichier, ligne);
        }
    }

    public void checkColonEstColon(String nomColon, String ligne) throws FichierException{
        if(memoire.get(nomColon) == FichierEtat.RESSOURCE){
            throw new FichierException("Le nom du colon est une ressource.", positionFichier, ligne);
        }
    }


    public void checkRessource(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas un ressource(...).
        if(etat != FichierEtat.RESSOURCE){
            return;
        }
        
        // st = [ressource, valeur]
        StringTokenizer st = new StringTokenizer(ligne, "()."); 

        // vider le premier tokken 
        st.nextToken();

        String nomRessource = st.nextToken();
        
        checkRessourceExistePas(nomRessource, ligne);
        checkRessourceEstPasColon(nomRessource, ligne);

        nbRessource++;
    }
    
    public void checkRessourceExiste(String nomRessource, String ligne) throws FichierException{
        if(!memoire.containsKey(nomRessource)){
            throw new FichierException("La ressource n'existe pas.", positionFichier, ligne);
        }
    }
    
    public void checkRessourceExistePas(String nomRessource, String ligne) throws FichierException{
        if(memoire.get(nomRessource) == FichierEtat.RESSOURCE){
            throw new FichierException("La ressource existe déjà.", positionFichier, ligne);
        }
    }
    
    public void checkRessourceEstPasColon(String nomRessource, String ligne) throws FichierException{
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

        // vider le premier tokken 
        st.nextToken(); 

        String colon1 = st.nextToken();
        String colon2 = st.nextToken();

        checkColonExiste(colon1, ligne);
        checkColonExiste(colon2, ligne);
       
        checkColonEstColon(colon1, ligne);
        checkColonEstColon(colon2, ligne);

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

        // vider le premier tokken 
        st.nextToken(); 

        String nomColon = st.nextToken();
        checkColonExiste(nomColon, ligne);
        checkColonEstColon(nomColon, ligne);
        
        if(st.countTokens() != nbRessource){
            throw new FichierException("Une ressource est manquante.", positionFichier, ligne);
        }
        
        String nomRessource;
        String [] ressources = new String[st.countTokens()];
        int i = 0;

        while(st.hasMoreTokens()) {
            nomRessource = st.nextToken();

            checkRessourceExiste(nomRessource, ligne);
            checkRessourceEstPasColon(nomRessource, ligne);

            ressources[i++] = nomRessource;
        }

        checkPreferenceRessourceUnique(ressources, ligne);
    }

    public void checkPreferenceRessourceUnique(String [] ressources, String ligne) throws FichierException{
        Set<String> ressourceRedondant = new HashSet<>(); 
        Set<String> ressourceSet = new HashSet<>();
            
        for (String ressource : ressources) {
            if(!ressourceSet.add(ressource)) {
                ressourceRedondant.add(ressource);
            }
        }

        if(!ressourceSet.isEmpty()){
            throw new FichierException("Les ressources " + ressourceRedondant.toString() + " sont redondantes.", positionFichier, ligne);
        }
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
