package com.example.core.factory1;

import com.example.repositories.jpa.ArticleRepositoryJpa;
import com.example.repositories.jpa.ClientRepositoryJpa;
import com.example.repositories.jpa.DetailRepositoryJpa;
import com.example.repositories.jpa.CommandeRepositoryJpa;
// import com.example.repositories.jpa.UserRepositoryJpa;
import com.example.repositories.jpa.impl.ClientRepositoryJpaImpl;

public class RepositoryFactory {

    private static ClientRepositoryJpa clientRepositoryJpa=null;
    // private static UserRepositoryJpa userRepositoryJpa=null;
    private static ArticleRepositoryJpa articleRepositoryJpa=null;
    private static CommandeRepositoryJpa detteRepositoryJpa=null;
    private static DetailRepositoryJpa detailRepositoryJpa=null;
    

    public static ClientRepositoryJpa createClientRepositoryJpa() {
        if(clientRepositoryJpa == null){
            clientRepositoryJpa = new ClientRepositoryJpa();
        }
        return clientRepositoryJpa;
    }
    public static ArticleRepositoryJpa createArticleRepositoryJpa() {
        if(articleRepositoryJpa == null){
            articleRepositoryJpa = new ArticleRepositoryJpa();
        }
        return articleRepositoryJpa;
    }

    // public static UserRepositoryJpa createUserRepositoryJpa() {
    //     if(userRepositoryJpa == null){
    //         userRepositoryJpa = new UserRepositoryJpa();
    //     }
    //     return userRepositoryJpa;
    // }
    public static CommandeRepositoryJpa createDetteRepositoryJpa() {
        if(detteRepositoryJpa == null){
            detteRepositoryJpa = new CommandeRepositoryJpa();
        }
        return detteRepositoryJpa;
    }
    public static DetailRepositoryJpa createDetailRepositoryJpa() {
        if(detailRepositoryJpa == null){
            detailRepositoryJpa = new DetailRepositoryJpa();
        }
        return detailRepositoryJpa;
    }
 




}
