package com.example.services;

import java.util.List;

import com.example.core.config.repository.impl.jpa.RepositoryJpaImpl;
import com.example.core.config.service.impl.ServiceImpl;
import com.example.entities.Commande;
import com.example.repositories.jpa.CommandeRepositoryJpa;


public class CommandeServiceImpl    extends ServiceImpl<Commande>{

    private RepositoryJpaImpl<Commande> repositoryJpaImpl;
    private CommandeRepositoryJpa detteRepositoryJpa;

    public CommandeServiceImpl(CommandeRepositoryJpa detteRepositoryJpa) {
        this.repositoryJpaImpl= new RepositoryJpaImpl<>(Commande.class);
        this.detteRepositoryJpa = new CommandeRepositoryJpa();
    }

    public void create(Commande dette) {
        repositoryJpaImpl.insert(dette);
    }

    public List<Commande> findAll() {
        return repositoryJpaImpl.selectAll();
    }
    public void update(Commande dette) {
        repositoryJpaImpl.update(dette);
    }   
    public Commande getById(Integer id) {
        return repositoryJpaImpl.selectById(id);
    }

    public List<Commande> findAllDetteSolder() {
        return detteRepositoryJpa.findAllDetteSolder();
    }

    public List<Commande> findAllDetteNonSolder() {
        return detteRepositoryJpa.findAllDetteNonSolder();
    }

    public List<Commande> findAllDetteAnulerOuEnCour() {
        return detteRepositoryJpa.findAllDetteAnulerOuEnCour();
    }
    public List<Commande> findAllDetteAnuler() {
        return detteRepositoryJpa.findAllDetteAnuler();
    }
    public List<Commande> findAllDetteEnCour() {
        return detteRepositoryJpa.findAllDetteEnCour();
    }

     public List<Commande> findAllDetteByClientId(Integer clientId){
        return detteRepositoryJpa.findAllDetteByClientId(clientId);
     }
     public double sumMontantByClientId(Integer clientId){
         return detteRepositoryJpa.sumMontantByClientId(clientId);
     }
     public List<Commande> findAllDetteAnulerOuEnCourById(Integer clientId){
          return detteRepositoryJpa.findAllDetteAnulerOuEnCourById(clientId);
     }
     public List<Commande> findAllDetteValider(){
         return detteRepositoryJpa.findAllDetteValider();
     }
     public List<Commande> findAllDetteAnulerById(Integer clientId){
         return detteRepositoryJpa.findAllDetteAnulerById(clientId);
     }

     public List<Commande> findAllDetteEncoursById(Integer clientId){
         return detteRepositoryJpa.findAllDetteEncoursById(clientId);
     }
        
    public boolean delete(int id) {
        repositoryJpaImpl.delete(   id);
        return true;
    }

    
}
