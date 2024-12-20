package Projet_PAA.test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import systeme.entite.Colon;
import systeme.entite.Ressource;




public class ColonTest {
    Colon colon1;
    Colon colon2;
    Colon colon3;
    Colon colon4;

    Ressource r1;
    Ressource r2;
    Ressource r3;
    Ressource r4;

    @Before
    public void setUp() throws Exception {
        colon1 = new Colon("a");
        colon2 = new Colon("b");
        colon3 = new Colon("c");
        colon4 = new Colon("d");

        r1 = new Ressource("r1");
        r2 = new Ressource("r2");
        r3 = new Ressource("r3");
        r4 = new Ressource("r4");

        colon1.ajouterRessource(r1);
        colon2.ajouterRessource(r2);
        colon3.ajouterRessource(r3);
        colon4.ajouterRessource(r4);

        colon1.ajouterPreference(r1);
        colon1.ajouterPreference(r2);
        colon1.ajouterPreference(r3);
        colon1.ajouterPreference(r4);

        colon1.ajouterEnnemi(colon2);
        colon1.ajouterEnnemi(colon3);
    }
    
    @Test
    public void getNomTest() {
        assertEquals("a", colon1.getNom());
        assertEquals("b", colon2.getNom());
        assertEquals("c", colon3.getNom());
        assertEquals("d", colon4.getNom());
    }

    @Test
    public void getRessourceTest() {
        List<Ressource> preferences = new ArrayList<>();
        preferences.add(r1);
        preferences.add(r2);
        preferences.add(r3);
        preferences.add(r4);

        assertEquals(preferences, colon1.getPreference());
    }

    @Test
    public void getEnnemis(){
        List<Colon> listeEnemis = new ArrayList<>();
        listeEnemis.add(colon2);
        listeEnemis.add(colon3);

        assertEquals(listeEnemis, colon1.getEnnemis());
    }
}
