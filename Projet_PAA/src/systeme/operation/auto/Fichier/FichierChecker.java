package systeme.operation.auto.Fichier;


public class FichierChecker{
    private int nbColon;
    private int nbRessource;

    private int currentPosition;
    
    private FichierEtat currentState;

    public FichierChecker(){
        nbColon = 0;
        nbRessource = 0;

        currentPosition = 0;

        currentState = FichierEtat.COLON;
    }

    
    interface CheckSomething {
        void check(String line) throws FichierException;
    }
    
    private CheckSomething[] checkSomething = new CheckSomething[] {

        new CheckSomething() {public void check(String line) throws FichierException {checkSyntax(line, currentState.getRegex());}},
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
                                + "Ligne " + currentPosition + ": " + line);
        }
    }
    
    public void checkColonRessource(String line) throws FichierException{
        if(currentState == FichierEtat.DETESTE && nbColon != nbRessource){
            throw new FichierException("Nombre de Colon et ressource incorrect" + "\n" 
                                + "Ligne " + currentPosition + ": " + line);
        }
    }

    public void checkState(String line) throws FichierException{
        String lineState = line.substring(0 , line.indexOf("("));

        if(!lineState.equals(currentState.toString())){
            throw new FichierException("Ordre d'élément incorrect" + "\n" 
                                + "Ligne " + currentPosition + ": " + line);
        }
    }

    public void changeState(){
        switch(currentState) {
            case FichierEtat.COLON:
                currentState = FichierEtat.RESSOURCE;
                break;
            
            case FichierEtat.RESSOURCE:
                currentState = FichierEtat.DETESTE;
                break;
            
            case FichierEtat.DETESTE:
                currentState = FichierEtat.PREFERENCE;
                break;

            default:
                break;
        }
    }
}
