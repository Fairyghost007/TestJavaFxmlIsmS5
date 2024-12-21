package com.example.repositories.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.entities.Commande;
import com.example.repositories.jpa.impl.CommandeRepositoryJpaImpl;

public class CommandeRepositoryJpa extends RepositoryJpaImpl<Commande> implements CommandeRepositoryJpaImpl {


    public CommandeRepositoryJpa() {
        super(Commande.class);
    }

    // Find all debts where montantRestant == 0 (Settled/Solder)
    @Override
    public List<Commande> findAllDetteSolder() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantRestant = 0 AND d.status = 'VALIDER'";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }
    @Override
    public List<Commande> findAllDetteAnulerOuEnCour() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ENCOURS' OR d.status = 'ANNULER')";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }
    @Override
    public List<Commande> findAllDetteAnuler() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ANNULER')";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }
    @Override
    public List<Commande> findAllDetteEnCour() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantVerser = 0 AND (d.status = 'ENCOURS' )";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }
    @Override
    public List<Commande> findAllDetteValider() {
        String jpql = "SELECT d FROM Dette d WHERE d.status = 'VALIDER' ";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }
    

    // Find all debts where montantRestant != 0 (Unsettled/Non-Solder)
    @Override
    public List<Commande> findAllDetteNonSolder() {
        String jpql = "SELECT d FROM Dette d WHERE d.montantRestant <> 0 AND d.status = 'VALIDER'";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        return query.getResultList();
    }

    // Assuming you want to search Dette by another parameter (if needed)
    public Commande findById(Integer id) {
        try {
            // Query for finding a Dette by its ID
            return em.createQuery("SELECT d FROM Dette d WHERE d.id = :id", Commande.class)
                     .setParameter("id", id)
                     .getSingleResult();
        } catch (NoResultException e) {
            return null; // Return null if no Dette is found
        }
    }

    @Override
    public List<Commande> findAllDetteByClientId(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'VALIDER'";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    @Override
    public double sumMontantByClientId(Integer clientId) {
        List<Commande> dettes = findAllDetteByClientId(clientId);
        return dettes.stream()
                     .mapToDouble(Commande::getMontant) // Assuming getMontant() returns a double
                     .sum();
    }

    @Override
    public List<Commande> findAllDetteAnulerOuEnCourById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND (d.status = 'ENCOURS' OR d.status = 'ANNULER')";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

    @Override
    public List<Commande> findAllDetteAnulerById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'ANNULER'";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }
    @Override
    public List<Commande> findAllDetteEncoursById(Integer clientId) {
        String jpql = "SELECT d FROM Dette d WHERE d.client.id = :clientId AND d.status = 'ENCOURS'";
        TypedQuery<Commande> query = em.createQuery(jpql, Commande.class);
        query.setParameter("clientId", clientId);
        return query.getResultList();
    }

}
