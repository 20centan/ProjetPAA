package systeme.operation.fichier;


/**
 * Classe exception qui permet de renvoyer un message d'erreur si le fichier est invalide
 */
public class FichierException extends Exception{
    public FichierException(String messageErreur){
        super("Erreur fichier: " + messageErreur);
    }
    
    public FichierException(String messageErreur, int ligne){
        super("Erreur fichier: " + messageErreur + "\n"
        + "Ligne " + ligne);
    }

    public FichierException(String messageErreur, int ligne, String ligneErreur){
        super("Erreur fichier: " + messageErreur + "\n"
        + "Ligne " + ligne + ": " + ligneErreur);
    }

}