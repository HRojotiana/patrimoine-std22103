package school.hei.patrimoine.modele;

import org.junit.jupiter.api.Test;
import school.hei.patrimoine.cas.exam.BakoCas;
import school.hei.patrimoine.modele.possession.Compte;
import school.hei.patrimoine.modele.possession.FluxArgent;
import school.hei.patrimoine.modele.possession.Materiel;

import java.time.LocalDate;
import java.time.Month;
import java.util.Set;

import static java.time.Month.MAY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static school.hei.patrimoine.modele.Argent.ariary;
import static school.hei.patrimoine.modele.Devise.MGA;

public class PatrimoineBakoTest {
    @Test
    void valeur_patrimoine_de_bako() {
        LocalDate ce_jour = LocalDate.of(2025, Month.APRIL, 8);
        LocalDate fin = LocalDate.of(2025, Month.DECEMBER, 31);

        var compteCourant = new Compte("Compte courant BNI", LocalDate.of(2025, Month.APRIL, 8), Argent.ariary( 2_000_000 ) );
        var compteEpargne = new Compte("Compte épargne BMOI", LocalDate.of(2025, Month.APRIL, 8), Argent.ariary( 625_000) );
        var coffreMaison = new Compte("Coffre fort", LocalDate.of(2025, Month.APRIL, 8), Argent.ariary(1_750_000));

        var ordinateur = new Materiel("Ordinateur Portable", ce_jour, fin,Argent.ariary(3_000_000) , 0.12);

        new FluxArgent("Salaire", compteCourant, ce_jour, fin, 2, Argent.ariary( 2_125_000));
        new FluxArgent("Epargne", compteEpargne, ce_jour, fin, 3, Argent.ariary(200_000));
        new FluxArgent("Epargne", compteCourant, ce_jour, fin, 3, Argent.ariary(-200_000));
        new FluxArgent("Loyer", compteCourant, ce_jour, fin, 26, Argent.ariary(-600_000));
        new FluxArgent("Dépense mensuelle", compteCourant, ce_jour, fin, 1, Argent.ariary(-700_000));

        var bako = new Personne("Bako");

        var patrimoineBakoAu31Dec2025 =
                Patrimoine.of(
                        "patrimoineBakoAu31Dec2025",
                        MGA,
                        fin,
                        bako,
                        Set.of(compteCourant, compteEpargne, coffreMaison, ordinateur));

        assertEquals(ariary(7_375_000), patrimoineBakoAu31Dec2025.getValeurComptable());
    }
}
