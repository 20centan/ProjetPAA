package systeme.operation.fichier;

public enum FichierEtat{
    COLON("colon", "colon\\([^\\s,]+\\)\\."), 
    RESSOURCE("ressource", "ressource\\([^\\s,]+\\)\\."), 
    DETESTE("deteste", "deteste\\([^\\s,]+,[^\\s,]+\\)."), 
    PREFERENCE("preference", "preference\\(([^\\s,]+)(,[^\\s,]+)+\\)\\.");

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
}
