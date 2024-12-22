package Projet_PAA.test;

import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void appartientColonieTest(){
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

        assertTrue(colonie1.appartientColonie(colon1));
        assertTrue(colonie1.appartientColonie(colon2));
        assertTrue(colonie1.appartientColonie(colon3));

        assertTrue(colonie2.appartientColonie(colon3));
        assertTrue(colonie2.appartientColonie(colon1));
        assertTrue(colonie3.appartientColonie(colon4));
        assertTrue(colonie3.appartientColonie(colon1));

        assertFalse(colonie2.appartientColonie(colon4));
        assertFalse(colonie3.appartientColonie(colon3));
        assertFalse(colonie3.appartientColonie(colon2));
    }

    @Test
    public void preferenceVideTest(){
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");

        Colon colon1 = new Colon("a");
        colon1.ajouterPreference(r1);
        colon1.ajouterPreference(r2);

        Colon colon2 = new Colon("b");
        Colon colon3 = new Colon("c");
        Colon colon4 = new Colon("d");
        colon4.ajouterPreference(r2);
        colon4.ajouterPreference(r1);

        Colonie colonie1 = new Colonie();
        Colonie colonie2 = new Colonie();
        Colonie colonie3 = new Colonie();

        colonie1.ajouterColon(colon1);
        colonie1.ajouterColon(colon2);
        colonie1.ajouterColon(colon3);
        colonie1.ajouterColon(colon4);

        colonie2.ajouterColon(colon3);
        colonie2.ajouterColon(colon2);

        colonie3.ajouterColon(colon4);
        colonie3.ajouterColon(colon1);


        assertTrue(colonie2.preferenceVide());
        assertTrue(colonie1.preferenceVide());
        
        assertFalse(colonie3.preferenceVide());
    }

    @Test
    public void preferenceValideTest(){
        Ressource r1 = new Ressource("r1");
        Ressource r2 = new Ressource("r2");
        Ressource r3 = new Ressource("r3");
        Ressource r4 = new Ressource("r4");

        Colon colon1 = new Colon("a");
        colon1.ajouterPreference(r1);
        colon1.ajouterPreference(r2);
        colon1.ajouterPreference(r3);
        colon1.ajouterPreference(r4);

        Colon colon2 = new Colon("b");
        colon2.ajouterPreference(r4);
        colon2.ajouterPreference(r1);
        colon2.ajouterPreference(r2);
        colon2.ajouterPreference(r3);

        Colon colon3 = new Colon("c");
        colon3.ajouterPreference(r4);
        colon3.ajouterPreference(r3);
        colon3.ajouterPreference(r2);
        colon3.ajouterPreference(r1);

        Colon colon4 = new Colon("d");
        colon4.ajouterPreference(r4);
        colon4.ajouterPreference(r3);
        colon4.ajouterPreference(r2);

        Colonie colonie1 = new Colonie();
        colonie1.ajouterRessources(r1);
        colonie1.ajouterRessources(r2);
        colonie1.ajouterRessources(r3);
        colonie1.ajouterRessources(r4);

        assertTrue(colonie1.preferenceValide(colon1.getPreference()));
        assertTrue(colonie1.preferenceValide(colon2.getPreference()));
        assertTrue(colonie1.preferenceValide(colon3.getPreference()));
        
        assertFalse(colonie1.preferenceValide(colon4.getPreference()));
    }
}
