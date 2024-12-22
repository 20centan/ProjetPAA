package systeme.entite;


public class Ressource {
    private String nom;

    public Ressource(String nom){
        this.nom = nom;
    }

    /**
     * Vérifie si cette ressource est égale à la ressource passée en paramètre
     * @param ressource
     * @return boolean
     */
    public boolean equals(Ressource ressource){
        return nom.equals(ressource.getNom());
    }

    /**
     * Vérifie si le nom de cette ressource est égale au nom passée en paramètre
     * @param nomRessource
     * @return
     */
    public boolean equals(String nomRessource){
        return nom.equals(nomRessource);
    }

    /**
     * Getter du nom de la ressource
     * @return String
     */
    public String getNom() {
        return nom;
    }

    public String toString(){
        return nom;
    }
}
