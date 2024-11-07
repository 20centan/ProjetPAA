public class Ressource {
    private int id;
    private static int compteur = 0;

    public Ressource(){
        id = ++compteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    @Override
    public String toString(){
        return String.valueOf(id);
    }

    public boolean equals(Ressource r){
        return this.id == r.getId();
    }

}
