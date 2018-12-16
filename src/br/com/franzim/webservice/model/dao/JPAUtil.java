package br.com.franzim.webservice.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {
	  
    private static EntityManagerFactory emf;
 
    public static EntityManager getEntityManager() {
          if (emf == null) {
               emf = Persistence.createEntityManagerFactory("estabelecimento");
          }
          return emf.createEntityManager();
    }
}