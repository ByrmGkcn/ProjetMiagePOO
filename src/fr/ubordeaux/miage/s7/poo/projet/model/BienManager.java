package fr.ubordeaux.miage.s7.poo.projet.model;

import fr.ubordeaux.miage.s7.poo.projet.model.BienImmobilier;
import fr.ubordeaux.miage.s7.poo.projet.model.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BienManager {
    private final ObservableList<BienImmobilier> biens;

    public BienManager() {
        this.biens = FXCollections.observableArrayList();
    }

    /**
     * Retourne la liste observable des biens.
     * 
     * @return ObservableList<BienImmobilier>
     */
    public ObservableList<BienImmobilier> getBiens() {
        return biens;
    }

    /**
     * Ajoute un bien avec les détails spécifiés.
     * 
     * @param adresse    L'adresse du bien.
     * @param codePostal Le code postal du bien.
     * @param ville      La ville du bien.
     * @param valeur     La valeur estimée du bien.
     */
    public void ajouterBien(String adresse, String codePostal, String ville, double valeur) {
        Model model = new Model(); // Création d'un nouveau modèle pour le bien
        BienImmobilier bien = new BienImmobilier(adresse, codePostal, ville, valeur, model);
        biens.add(bien);
    }

    /**
     * Supprime un bien spécifique.
     * 
     * @param bien Le bien à supprimer.
     */
    public void supprimerBien(BienImmobilier bien) {
        biens.remove(bien);
    }

    /**
     * Recherche un bien par son ID.
     * 
     * @param id L'identifiant du bien.
     * @return BienImmobilier, ou null si aucun bien ne correspond.
     */
    public BienImmobilier rechercherParId(String id) {
        for (BienImmobilier bien : biens) {
            if (bien.getId().equals(id)) {
                return bien;
            }
        }
        return null;
    }
}
