package systeme.operation.fichier;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class FichierChecker{
    private int nbColon;
    private int nbRessource;
    private LinkedHashMap<String, FichierEtat> memoire; // la mémoire permet de tracker les dernières lignes visitées, ça permet de faire des vérifications. 

    private int positionFichier; // position du pointer du BufferedReader
    
    private FichierEtat etat; // état de la ligne (colon, ressource, deteste ou préférence)

    public FichierChecker(){
        nbColon = 0;
        nbRessource = 0;
        memoire = new LinkedHashMap<>();

        positionFichier = 1; // ligne 1 du fichier

        etat = FichierEtat.COLON; 
    }

    /**
     * Permet de stocker des fonctions dans une interface
     */
    private interface CheckLigne {
        void check(String ligne) throws FichierException;
    }
    
    /**
     * Permet de stocker dans un array les interfaces qui contient les méthodes et les appeller plus tard
     */
    private CheckLigne[] checkLigne = new CheckLigne[] {
        new CheckLigne() {public void check(String ligne) throws FichierException {checkSyntaxe(ligne, etat.getRegex());}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkEtat(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkColon(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkRessource(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkDeteste(ligne);}},
        new CheckLigne() {public void check(String ligne) throws FichierException {checkPreference(ligne);}},
    };

    /**
     * Vérifie si la ligne passé en paramètre est valide après les test
     * @param ligne
     */    
    public void check(String ligne) throws FichierException{
        changerEtat(ligne);

        for(CheckLigne checker : checkLigne){
            checker.check(ligne);
        }

        ajouterEnMemoire(ligne);

        positionFichier++;
    }

    /**
     * Vérifie si la syntaxe de la ligne est correcte
     * @param ligne
     */
    public void checkSyntaxe(String ligne, String regex) throws FichierException{
        if(!ligne.matches(regex)){
            throw new FichierException("La syntaxe incorrecte.", positionFichier, ligne);
        }
    }

    /**
     * Vérifie si l'ordre des appels est correcte
     * @param ligne
     */
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

    /**
     * Fonction de checkColon
     * @param ligne
     */
    public void checkColon(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas du type colon(...).
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

    /**
     * Sous fonction de checkColon
     * Vérifie si le colon n'existe pas dans la mémoire
     * @param ligne
     */    
    public void checkColonExiste(String nomColon, String ligne) throws FichierException{
        if(!memoire.containsKey(nomColon)){
            throw new FichierException("Le colon " + nomColon + " n'existe pas.", positionFichier, ligne);
        }
    }

    /**
     * Sous fonction de checkColon
     * Vérifie si le colon existe dans la mémoire (n'est pas un doublon)
     * @param ligne
     */    
    public void checkColonExistePas(String nomColon, String ligne) throws FichierException{
        if(memoire.get(nomColon) == FichierEtat.COLON){
            throw new FichierException("Le colon " + nomColon + " existe déjà.", positionFichier, ligne);
        }
    }

    /**
     * Sous fonction de checkColon
     * Vérifie si le nom du colon n'est pas une ressource
     * @param ligne
     */    
    public void checkColonEstColon(String nomColon, String ligne) throws FichierException{
        if(memoire.get(nomColon) == FichierEtat.RESSOURCE){
            throw new FichierException("Le nom du colon est une ressource.", positionFichier, ligne);
        }
    }

    /**
     * Fonction de checkRessource
     * @param ligne
     */    
    public void checkRessource(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas du type ressource(...).
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
    
    /**
     * Sous fonction de checkRessource
     * Vérifie si la ressource n'existe pas
     * @param ligne
     */    
    public void checkRessourceExiste(String nomRessource, String ligne) throws FichierException{
        if(!memoire.containsKey(nomRessource)){
            throw new FichierException("La ressource " + nomRessource + " n'existe pas.", positionFichier, ligne);
        }
    }

    /**
     * Sous fonction de checkColon
     * Vérifie si la ressource existe dans la mémoire (n'est pas un doublon)
     * @param ligne
     */    
    public void checkRessourceExistePas(String nomRessource, String ligne) throws FichierException{
        if(memoire.get(nomRessource) == FichierEtat.RESSOURCE){
            throw new FichierException("La ressource " + nomRessource + " existe déjà.", positionFichier, ligne);
        }
    }

    /**
     * Sous fonction de checkColon
     * Vérifie si le nom de la ressource n'est pas un colon
     * @param ligne
     */    
    public void checkRessourceEstPasColon(String nomRessource, String ligne) throws FichierException{
        if(memoire.get(nomRessource) == FichierEtat.COLON){
            throw new FichierException("Le nom de la ressource est un colon.", positionFichier, ligne);
        }
    }

    /**
     * Fonction de checkDeteste
     * @param ligne
     */    
    public void checkDeteste(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas du type deteste(...).
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

    /**
     * Fonction de checkPreference
     * Vérifie que le colon saisi est un colon qui existe, vérifie que la ressource saisie est une ressource qui existe, vérifie que la préférence contient des ressources distinctes
     * @param ligne
     */    
    public void checkPreference(String ligne) throws FichierException{
        // ne lance pas la vérification si la ligne n'est pas du type deteste(...).
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
        
        if(st.countTokens() < nbRessource){
            throw new FichierException("Une ressource est manquante.", positionFichier, ligne);
        }
        else if(nbRessource < st.countTokens()){
            throw new FichierException("Une ressource est en trop.", positionFichier, ligne);
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

    /**
     * Sous fonction de checkPreference
     * Vérification si les ressources saisient ne présentes pas de doublons
     * @param ligne
     */    
    public void checkPreferenceRessourceUnique(String [] ressources, String ligne) throws FichierException{
        // ensemble des éléments uniques
        Set<String> ressourceRedondant = new HashSet<>(); 
        Set<String> ressourceSet = new HashSet<>();
        
        for (String ressource : ressources) {
            // si un élément est ré ajouté alors c'est True
            if(!ressourceSet.add(ressource)) {
                ressourceRedondant.add(ressource);
            }
        }

        if(!ressourceRedondant.isEmpty()){
            throw new FichierException("Les ressources " + ressourceRedondant.toString() + " sont redondantes.", positionFichier, ligne);
        }
    }


    /**
     * Permet de stocker des fonctions de check dans une interface
     */
    private interface CheckManquant{
        void check() throws FichierException;
    }

    /**
     * Permet de stocker dans un array les interfaces qui contient les méthodes de check et les appeller plus tard
     */
    private CheckManquant[] checkManquant = new CheckManquant[]{
        new CheckManquant() {public void check() throws FichierException {checkColonRessource();}},
        new CheckManquant() {public void check() throws FichierException {checkColonPreference();}}
    };

    /**
     * Vérifie le fichier est valide après les test
     */    
    public void check() throws FichierException{
        for(CheckManquant checker : checkManquant){
            checker.check();
        }
    }

    /**
     * Vérifie chaque colon a une préférence
     */    
    public void checkColonPreference() throws FichierException{
        Set<String> nomColonSet = new HashSet<>();
        String nomColon;


        for(Map.Entry<String, FichierEtat> entree : memoire.reversed().entrySet()){
            if(entree.getValue() != FichierEtat.PREFERENCES){
                break;
            }
            nomColon = entree.getKey().substring(0, entree.getKey().indexOf(","));
        
            nomColonSet.add(nomColon);
        }

        if(nomColonSet.size() != nbColon){
            throw new FichierException("Les préférences d'un colon sont manquantes.");
        }
    }

    /**
     * Vérifie le nombre de colons est égal au nombre de préférences
     */    
    public void checkColonRessource() throws FichierException{
        if(nbColon != nbRessource){
            throw new FichierException("Le nombre de colon et ressource est inégal.");
        }
    }

    /**
     * Enregistre la ligne dans une LinkedHashMap pour tracker les dernières lignes ajoutées
     * @param ligne
     */    
    public void ajouterEnMemoire(String ligne){
        StringTokenizer st = new StringTokenizer(ligne, "()."); 
        
        // ajouter colon: nomColon ou ressource: nomRessource
        FichierEtat ligneEtat = switch(st.nextToken()){
            case "colon" -> FichierEtat.COLON;
            case "ressource" -> FichierEtat.RESSOURCE;
            case "deteste" -> FichierEtat.DETESTE;
            case "preferences" -> FichierEtat.PREFERENCES;
            default -> null;
        };

        memoire.put(st.nextToken(), ligneEtat);
    }

    /**
     * Change l'état de la ligne (lecteur des lignes colon(), ressource(), deteste() ou preference())
     * @param ligne
     */    
    public void changerEtat(String ligne){
        String ligneEtat = ligne.substring(0, ligne.indexOf("("));

        if(!ligneEtat.equals(etat.getName())){
            switch(etat) {
                case COLON:
                    etat = FichierEtat.RESSOURCE;
                    break;
                
                case RESSOURCE:
                    etat = FichierEtat.DETESTE;
                    break;
                
                case DETESTE:
                    etat = FichierEtat.PREFERENCES;
                    break;
    
                default:
                    etat = FichierEtat.FINFICHIER;
                    break;
            }
        }
    }
}
