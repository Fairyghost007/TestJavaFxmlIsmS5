package com.example.repositories.jpa.impl;

import java.util.List;

import com.example.entities.Commande;

public interface CommandeRepositoryJpaImpl {

    List<Commande> findAllDetteSolder();
    List<Commande> findAllDetteNonSolder();
    List <Commande> findAllDetteAnulerOuEnCour();
    List <Commande> findAllDetteAnulerOuEnCourById(Integer clientId);
    List <Commande> findAllDetteAnulerById(Integer clientId);
    List <Commande> findAllDetteByClientId(Integer clientId);
    List <Commande> findAllDetteValider();
    double sumMontantByClientId(Integer clientId);
    List<Commande> findAllDetteAnuler();
    List<Commande> findAllDetteEnCour();
    List<Commande> findAllDetteEncoursById(Integer clientId);

}
