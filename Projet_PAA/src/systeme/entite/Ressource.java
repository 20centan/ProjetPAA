package systeme.entite;


public class Ressource {
    private String nom;

    public Ressource(String nom){
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom){
        this.nom = nom;
    }

    public boolean equals(Ressource ressource){
        return nom.equals(ressource.getNom());
    }

    public boolean equals(String nomRessource){
        return nom.equals(nomRessource);
    }
}
