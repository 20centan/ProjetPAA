import java.util.ArrayList;


public class Colon {
    private char nom;
    private ArrayList<Colon> ennemis;
    private ArrayList<Ressource> preference;
    private Ressource ressource;

    public Colon(char nom){
        this.nom = nom;

        this.ennemis = new ArrayList<Colon>();
        this.preference = new ArrayList<Ressource>();
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
    
    
    public void ajoutPreference(Ressource ressource) {
    	preference.add(ressource);
    }


    public char getNom() {
        return nom;
    }

    public static void main(String [] args){
        Colon colon1 = new Colon('A');
        Colon colon2 = new Colon('B');
        Colon colon3 = new Colon('C');

        colon1.ajoutEnnemi(colon2);
        System.out.println(colon1.ennemis.get(0).nom);
        System.out.println(colon2.ennemis.get(0).nom);
    }
}
