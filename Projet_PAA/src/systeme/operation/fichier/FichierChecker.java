package systeme.operation.fichier;


public class FichierChecker{
    private int nbColon;
    private int nbRessource;

    private int positionFichier;
    
    private FichierEtat etat;

    public FichierChecker(){
        nbColon = 0;
        nbRessource = 0;

        positionFichier = 0;

        etat = FichierEtat.COLON;
    }

    
    interface CheckSomething {
        void check(String line) throws FichierException;
    }
    
    private CheckSomething[] checkSomething = new CheckSomething[] {

        new CheckSomething() {public void check(String line) throws FichierException {checkSyntax(line, etat.getRegex());}},
        new CheckSomething() {public void check(String line) throws FichierException {checkState(line);}},
        new CheckSomething() {public void check(String line) throws FichierException {checkColonRessource(line);}}
    };

    // appel tous les vérifications pour une ligne
    public void check(String line) throws FichierException{
        for(CheckSomething checkMethod : checkSomething){
            checkMethod.check(line);
        }
    }


    public void checkColon(String line) throws FichierException{
        // verifier que le nom est unique

    }

    public void checkRessource(String line) throws FichierException{
        // vérifier que le nom est unique

    }

    public void checkDesteste(String line) throws FichierException{
        // vérifier que les deux noms sont des noms de colon
    }

    public void checkPreference(String line) throws FichierException{
        // vérifier le bon nombre de paramètre
        // vérifier que le premier nom est un colon et que le reste c'est des ressources existantes
    }

    public void checkSyntax(String line, String regex) throws FichierException{
        if(!line.matches(regex)){
            throw new FichierException("Syntaxe incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + line);
        }
    }
    
    public void checkColonRessource(String line) throws FichierException{
        if(etat == FichierEtat.DETESTE && nbColon != nbRessource){
            throw new FichierException("Nombre de Colon et ressource incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + line);
        }
    }

    public void checkState(String line) throws FichierException{
        String lineState = line.substring(0, line.indexOf("("));

        if(!lineState.equals(etat.toString())){
            throw new FichierException("Ordre d'élément incorrect" + "\n" 
                                + "Ligne " + positionFichier + ": " + line);
        }
    }

    public void changeState(){
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
