package systeme.operation.fichier;

public class FichierException extends Exception{
    public FichierException(String messageErreur){
        super(messageErreur);
    }
    
    public FichierException(String messageErreur, int ligne){
        super(messageErreur + "\n"
        + "Ligne " + ligne);
    }

    public FichierException(String messageErreur, int ligne, String ligneErreur){
        super(messageErreur + "\n"
        + "Ligne " + ligne + ": " + ligneErreur);
    }

}