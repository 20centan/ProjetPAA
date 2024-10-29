public class Ressource {
    private static int id = 0;

    public Ressource(){
        id++;
    }

    public static int getId() {
        return id;
    }   
}
