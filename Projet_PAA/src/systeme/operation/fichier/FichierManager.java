package systeme.operation.fichier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;


public class FichierManager {
    private Path fichier;

    private BufferedReader reader;

    private BufferedWriter writer;

    private FichierChecker fileColonie;

    public FichierManager(String chemin){
        fichier = Path.of(chemin);

        fileColonie = new FichierChecker();
    }

    /**
     * Ouvre un Buffer pour la lecture
     */    
    public void openReader(){
        try{
            reader = Files.newBufferedReader(fichier);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Ouvre un Buffer pour l'écriture
     */    
    public void openWriter(){
        try{
            writer = Files.newBufferedWriter(fichier);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    /**
     * Retourne la prochaine ligne du fichier en lecture après avoir tester 
     */    
    public String getNextData(){
        try{
            String line = reader.readLine();

            if(line == null){
                runCheck();

                return null;
            }
            
            runCheck(line);
            

            return line;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    /**
     * Appelle les méthodes de check
     */    
    public void runCheck(String line){
        try{
            fileColonie.check(line);
        }
        catch(FichierException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }

    /**
     * Appelle les méthodes de check
     */    
    public void runCheck(){
        try{
            fileColonie.check();
        }
        catch(FichierException e){
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
    

    /**
     * Ecrit les données dans le fichier sauvegarde
     */    
    public void save(String data){
        try{
            writer.write(data);
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Ferme le Buffer pour la lecture
     */    
    public void closeReader(){
        try{
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Ferme le Buffer pour l'écriture
     */    
    public void closeWriter(){
        try{
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
