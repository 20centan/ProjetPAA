public class Ressource {
    private int id;
    private static int compteur = 0;

    public Ressource(){
        id = ++compteur;
    }

    public int getId() {
        return id;
    }   
}
