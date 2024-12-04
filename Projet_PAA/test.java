package Projet_PAA;

import java.nio.file.Files;
import java.nio.file.Path;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

enum State{
    COLON("colon", "colon\\([^\\s]+\\)\\."), 
    RESSOURCE("ressource", "ressource\\([^\\s]+\\)\\."), 
    DETESTE("deteste", "deteste\\([^\\s]+, [^\\s]+\\)\\."), 
    PREFERENCE("preference", "preference\\(([^\\s,]+)(,[^\\s,]+)+\\)\\.");

    private String name;
    private String regex;

    private State(String name, String regex){
        this.name = name;
        this.regex = regex;
    }

    public String toString(){
        return name;
    }

    public String getRegex(){
        return regex;
    }
}

class FileException extends Exception{
    public FileException(String messageError){
        super(messageError);
    }
}


class FileChecker{
    private int nbColon;
    private int nbRessource;

    private int currentPosition;
    
    private State currentState;

    public FileChecker(){
        nbColon = 0;
        nbRessource = 0;

        currentPosition = 0;

        currentState = State.COLON;
    }

    
    interface CheckSomething {
        void check(String line) throws FileException;
    }
    
    private CheckSomething[] checkSomething = new CheckSomething[] {

        new CheckSomething() {public void check(String line) throws FileException {checkSyntax(line, currentState.getRegex());}},
        new CheckSomething() {public void check(String line) throws FileException {checkState(line);}},
        new CheckSomething() {public void check(String line) throws FileException {checkColonRessource(line);}}
    };

    // appel tous les vérifications pour une ligne
    public void runCheck(String line) throws FileException{
        for(CheckSomething checkMethod : checkSomething){
            checkMethod.check(line);
        }
    }


    public void checkColon(String line) throws FileException{
        // verifier que le nom est unique
    }

    public void checkRessource(String line) throws FileException{
        // vérifier que le nom est unique

    }

    public void checkDesteste(String line) throws FileException{
        // vérifier que les deux noms sont des noms de colon
    }

    public void checkPreference(String line) throws FileException{
        // vérifier le bon nombre de paramètre
        // vérifier que le premier nom est un colon et que le reste c'est des ressources existantes
    }

    public void checkSyntax(String line, String regex) throws FileException{
        if(!line.matches(regex)){
            throw new FileException("Syntaxe incorrect" + "\n" 
                                + "Ligne " + currentPosition + ": " + line);
        }
    }
    
    public void checkColonRessource(String line) throws FileException{
        if(currentState == State.DETESTE && nbColon != nbRessource){
            throw new FileException("Nombre de Colon et ressource incorrect" + "\n" 
                                + "Ligne " + currentPosition + ": " + line);
        }
    }

    public void checkState(String line) throws FileException{
        String lineState = line.substring(0 , line.indexOf("("));

        if(!lineState.equals(currentState.toString())){
            throw new FileException("Ordre d'élément incorrect" + "\n" 
                                + "Ligne " + currentPosition + ": " + line);
        }
    }

    public void changeState(){
        switch(currentState) {
            case State.COLON:
                currentState = State.RESSOURCE;
                break;
            
            case State.RESSOURCE:
                currentState = State.DETESTE;
                break;
            
            case State.DETESTE:
                currentState = State.PREFERENCE;
                break;

            default:
                break;
        }
    }
}

class FileManager {
    private BufferedReader reader;

    private FileChecker fileColonie;

    public FileManager(String path){
        fileColonie = new FileChecker();
    }

    public void openReader(String path){
        try{
            reader = Files.newBufferedReader(Path.of(path));
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public String getNextData(){
        try{
            String line = reader.readLine();
            
            if(!checkLine(line)){
                // fichier corrompu: afficher le problème et sortir du programme 
            }

            return line;
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }

        return "";
    }

    public boolean checkLine(String line){
        try{
            fileColonie.runCheck(line);

            return true;
        }
        catch(FileException e){
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

public class test {
    public static void main(String [] args){
    }    
}
