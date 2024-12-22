package systeme.operation.fichier;

/**
 * Enum qui permet de tracker l'état de la lecture du fichier (colon(), ressource(), deteste() ou preference())
 */
public enum FichierEtat{
    COLON("colon", "colon\\([^\\s,]+\\)\\."), 
    RESSOURCE("ressource", "ressource\\([^\\s,]+\\)\\."), 
    DETESTE("deteste", "deteste\\([^\\s,]+,[^\\s,]+\\)."), 
    PREFERENCES("preferences", "preferences\\(([^\\s,]+)(,[^\\s,]+)+\\)\\."),

    // (exceptionnel) pour marquer la fin du fichier
    FINFICHIER("fin", "");

    private String name;
    private String regex; //Respect du pattern

    private FichierEtat(String name, String regex){
        this.name = name;
        this.regex = regex;
    }

    public String toString(){
        return name;
    }

    public String getRegex(){
        return regex;
    }

    public String getName() {
        return name;
    }
}
