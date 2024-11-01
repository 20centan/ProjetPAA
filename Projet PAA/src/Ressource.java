public class Ressource {
    private static int id = 1;

    public Ressource(){
        id++;
    }

    public static int getId() {
        return id;
    }   
}
