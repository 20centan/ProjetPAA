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

        // position de départ: ligne 1 du fichier
        positionFichier = 1;

        etat = FichierEtat.COLON;
    }

    
    private interface CheckSomething {
        void check(String ligne) throws FichierException;
    }
    
    private CheckSomething[] checkSomething = new CheckSomething[] {

        new CheckSomething() {public void check(String ligne) throws FichierException {checkSyntaxe(ligne, etat.getRegex());}},
        new CheckSomething() {public void check(String ligne) throws FichierException {checkEtat(ligne);}},
        new CheckSomething() {public void check(String ligne) throws FichierException {checkColonRessource(ligne);}}
    };

    // appel tous les vérifications pour une ligne
    public void check(String ligne) throws FichierException{
        for(CheckSomething checkMethod : checkSomething){
            changerEtat(ligne);

            checkMethod.check(ligne);

            ajouterEnMemoire(ligne);
        }
    }


    public void checkColon(String ligne) throws FichierException{
        // verifier que le nom est unique

    }

    public void checkRessource(String ligne) throws FichierException{
        // vérifier que le nom est unique

    }

    public void checkDesteste(String ligne) throws FichierException{
        // vérifier que les deux noms sont des noms de colon
    }

    public void checkPreference(String ligne) throws FichierException{
        // vérifier le bon nombre de paramètre
        // vérifier que le premier nom est un colon et que le reste c'est des ressources existantes
    }

    public void checkSyntaxe(String ligne, String regex) throws FichierException{
        if(!ligne.matches(regex)){
            throw new FichierException("Syntaxe incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + ligne);
        }
    }
    
    public void checkColonRessource(String ligne) throws FichierException{
        if(etat == FichierEtat.DETESTE && nbColon != nbRessource){
            throw new FichierException("Nombre de Colon et ressource incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + ligne);
        }
    }

    public void checkEtat(String ligne) throws FichierException{
        String ligneEtat = ligne.substring(0, ligne.indexOf("("));

        // verifier si la 1ère ligne est un colon
        if(positionFichier == 1 && !ligneEtat.equals("colon")){
            throw new FichierException("Ordre d'élément incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + ligne);
        }

        if(!ligneEtat.equals(etat.toString())){
            throw new FichierException("Ordre d'élément incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + ligne);
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
                    etat = FichierEtat.PREFERENCE;
                    break;
    
                default:
                    break;
            }
        }
    }
}
