package Projet_PAA.test;


import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import systeme.entite.Colon;
import systeme.entite.Ressource;




public class ColonTest { 
    @Test
    public void initColonTest() {
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");

        assertEquals("a", colon1.getNom());
        assertEquals("b", colon2.getNom());
        assertEquals("c", colon3.getNom());
        assertEquals("d", colon4.getNom());
    }

    @Test
    public void ajouterRessourceTest() {
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");
        
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");
        Ressource r3 = new Ressource("r3");
        Ressource r4 = new Ressource("r4");

        colon1.ajouterRessource(r1);
        colon2.ajouterRessource(r2);
        colon3.ajouterRessource(r3);
        colon4.ajouterRessource(r4);

        assertEquals(r1, colon1.getRessource());
        assertEquals(r2, colon2.getRessource());
        assertEquals(r3, colon3.getRessource());
        assertEquals(r4, colon4.getRessource());
    }

    @Test
    public void ajouterEnnemiTest(){
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");        
        Colon colon4 = new Colon("d");

        colon1.ajouterEnnemi(colon4);
        colon1.ajouterEnnemi(colon2);

        assertEquals(List.of(colon4, colon2), colon1.getEnnemis());
        assertEquals(List.of(colon1), colon2.getEnnemis());
        assertEquals(List.of(colon1), colon4.getEnnemis());
        assertEquals(List.of(), colon3.getEnnemis());
    }

    @Test
    public void ajouterPreference(){
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");
        
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");
        Ressource r3 = new Ressource("r3");
        Ressource r4 = new Ressource("r4");

        colon1.ajouterPreference(r1);
        colon1.ajouterPreference(r2);
        colon1.ajouterPreference(r3);
        colon1.ajouterPreference(r4);

        colon2.ajouterPreference(r3);
        colon2.ajouterPreference(r1);

        colon3.ajouterPreference(r1);
        colon3.ajouterPreference(r4);

        assertEquals(List.of(r1, r2, r3, r4), colon1.getPreference());
        assertEquals(List.of(r3, r1), colon2.getPreference());
        assertEquals(List.of(r1, r4), colon3.getPreference());
        assertEquals(List.of(), colon4.getPreference());

    }

    @Test
    public void viderPreferenceTest(){
        Colon colon1 = new Colon("a");
        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");
        
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");
        Ressource r3 = new Ressource("r3");
        Ressource r4 = new Ressource("r4");

        colon1.ajouterPreference(r1);
        colon1.ajouterPreference(r2);
        colon1.ajouterPreference(r3);
        colon1.ajouterPreference(r4);

        colon2.ajouterPreference(r3);
        colon2.ajouterPreference(r1);

        colon3.ajouterPreference(r1);
        colon3.ajouterPreference(r4);

        colon1.viderPreference();
        colon2.viderPreference();
        colon3.viderPreference();
        colon4.viderPreference();

        assertEquals(List.of(), colon1.getPreference());
        assertEquals(List.of(), colon2.getPreference());
        assertEquals(List.of(), colon3.getPreference());
        assertEquals(List.of(), colon4.getPreference());

    }
}
