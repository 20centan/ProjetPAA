import java.util.ArrayList;


public class Colon {
    private char nom;
    private ArrayList<Colon> ennemis;
    private ArrayList<Ressource> preference;
    private Ressource ressource;

    public Colon(char nom, ArrayList<Ressource> preference){
        this.nom = nom;
        this.preference = preference;

        this.ennemis = new ArrayList<Colon>();
    }

    public void ajoutEnnemi(Colon c){
        if(!ennemis.contains(c)){
            ennemis.add(c);
            
            c.ajoutEnnemi(this);
        }
    }

    public void ajoutRessource(Ressource ressource){
        this.ressource = ressource;
    }

    public char getNom() {
        return nom;
    }

    public void setNom(char nom) {
        this.nom = nom;
    }

    public static void main(String [] args){
        Colon colon1 = new Colon('A', null);
        Colon colon2 = new Colon('B', null);
        Colon colon3 = new Colon('C', null);

        colon1.ajoutEnnemi(colon2);
        System.out.println(colon1.ennemis.get(0).nom);
        System.out.println(colon2.ennemis.get(0).nom);
    }
}
