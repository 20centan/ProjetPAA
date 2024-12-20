package Projet_PAA.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import systeme.entite.Colon;
import systeme.entite.Ressource;
import systeme.entite.Colonie;


public class ColonieTest {
    @Test
    public void ajouterColonTest(){
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");

        Colonie colonie1 = new Colonie();
        Colonie colonie2 = new Colonie();
        Colonie colonie3 = new Colonie();

        colonie1.ajouterColon(colon1);
        colonie1.ajouterColon(colon2);
        colonie1.ajouterColon(colon3);
        colonie1.ajouterColon(colon4);

        colonie2.ajouterColon(colon3);
        colonie2.ajouterColon(colon2);
        colonie2.ajouterColon(colon1);

        colonie3.ajouterColon(colon4);
        colonie3.ajouterColon(colon1);

        assertEquals(List.of(colon1, colon2, colon3, colon4), colonie1.getColons());
        assertEquals(List.of(colon3, colon2, colon1), colonie2.getColons());
        assertEquals(List.of(colon4, colon1), colonie3.getColons());
        
    }

    @Test
    public void ajouterRessourcesTest(){
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");
        Ressource r3 = new Ressource("r3");
        Ressource r4 = new Ressource("r4");

        Colonie colonie1 = new Colonie();
        Colonie colonie2 = new Colonie();
        Colonie colonie3 = new Colonie();

        colonie1.ajouterRessources(r1);
        colonie1.ajouterRessources(r2);
        colonie1.ajouterRessources(r3);
        colonie1.ajouterRessources(r4);

        colonie2.ajouterRessources(r4);
        colonie2.ajouterRessources(r1);
        colonie2.ajouterRessources(r2);

        colonie3.ajouterRessources(r2);
        colonie3.ajouterRessources(r1);

        assertEquals(List.of(r1, r2, r3, r4), colonie1.getRessources());
        assertEquals(List.of(r4, r1, r2), colonie2.getRessources());
        assertEquals(List.of(r2, r1), colonie3.getRessources());
    }
}
