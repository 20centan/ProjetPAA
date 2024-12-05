package systeme.operation.fichier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.IOException;


public class FichierManager {
    private Path fichier;

    private BufferedReader reader;

    private FichierChecker fileColonie;

    public FichierManager(String chemin){
        fichier = Path.of(chemin);

        fileColonie = new FichierChecker();
    }

    public void openReader(){
        try{
            reader = Files.newBufferedReader(fichier);
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String getNextData(){
        try{
            String line = reader.readLine();

            if(line == null){return null;}
            
            

            return line;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    public boolean runCheck(String line){
        try{
            fileColonie.check(line);

            return true;
        }
        catch(FichierException e){
            System.out.println(e.getMessage());
        }
        
        return false;
    }
    

    public void save(String data, String dataPath){
        try{
            BufferedWriter writer = Files.newBufferedWriter(Path.of(dataPath));
            
            writer.write(data);

            writer.close();

        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public void closeReader(){
        try{
            reader.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    
}
