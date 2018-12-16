package br.com.franzim.webservice.model.dao;

import java.util.List;

import javax.persistence.EntityManager;

import br.com.franzim.webservice.exceptions.DAOException;
import br.com.franzim.webservice.exceptions.ErrorCode;
import br.com.franzim.webservice.model.domain.Estabelecimento;

public class EstabelecimentoDAO {

	public List<Estabelecimento> getAll() {
		
	      EntityManager em = JPAUtil.getEntityManager();
	      List<Estabelecimento> estabelecimentos = null;
	       
	      try {
	             estabelecimentos = em.createQuery("select e from Estabelecimento e", Estabelecimento.class).getResultList();
	      } catch (RuntimeException ex){
	    	throw new DAOException("Erro ao recuperar os estabelecimentos do banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());  
	      }
	      	finally {
	             em.close();
	      }
	       
	      return estabelecimentos;
	}
	 
	public Estabelecimento getById(long id) {
		
	      EntityManager em = JPAUtil.getEntityManager();
	      Estabelecimento estabelecimento = null;
	        
	      if(id <= 0) {
	    	  throw new DAOException("O id precisa ser maior do que 0", ErrorCode.BAD_REQUEST.getCode());
	      }
	      
	      try {
	             estabelecimento = em.find(Estabelecimento.class, id);
	      } catch (RuntimeException ex){
	    	  throw new DAOException("Erro ao buscar Estabelecimento por id no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());  
	      } finally {
	    	  em.close();
	      }
	      
	      if(estabelecimento == null ) {
	    	  throw new DAOException("Estabelecimento de id " + id + " nao existe.", ErrorCode.NOT_FOUND.getCode());
	      }
	      return estabelecimento;
	}
	 
	public Estabelecimento save(Estabelecimento estabelecimento) {
		
	      EntityManager em = JPAUtil.getEntityManager();
	    
	      if(!estabelecimentoIsValid(estabelecimento)) {
	    	  throw new DAOException("Estabelecimento com dados incompletos", ErrorCode.BAD_REQUEST.getCode());
	      }
	      
	      try {
	             em.getTransaction().begin();
	             em.persist(estabelecimento);
	             em.getTransaction().commit();
	      } catch (RuntimeException ex){
	    	  throw new DAOException("Erro ao salvar Estabelecimento no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());  
	      } finally {
	             em.close();
	      }
	      return estabelecimento;
	}
	 
	public Estabelecimento update(Estabelecimento estabelecimento) {
		
	      EntityManager em = JPAUtil.getEntityManager();
	      
	      Estabelecimento estabelecimentoManaged = null;
	      
	      if(estabelecimento.getId() <= 0) {
	    	  throw new DAOException("O id precisa ser maior do que 0", ErrorCode.BAD_REQUEST.getCode());
	      }
	      
	      if(!estabelecimentoIsValid(estabelecimento)) {
	    	  throw new DAOException("Estabelecimento com dados incompletos", ErrorCode.BAD_REQUEST.getCode());
	      }
	              
	      try {
	             em.getTransaction().begin();
	             estabelecimentoManaged = em.find(Estabelecimento.class, estabelecimento.getId());
	             estabelecimentoManaged.setNome(estabelecimento.getNome());
	             em.getTransaction().commit();
	      } catch (NullPointerException ex) {
	    	  em.getTransaction().rollback();
	    	  throw new DAOException("Estabelecimento informado para atualização não existe: " + ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
	      } catch (RuntimeException ex) {
	    	  em.getTransaction().rollback();
	    	  throw new DAOException("Erro ao atualizar Estabelecimento no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
	      } finally {
	             em.close();
	      }
	       
	      return estabelecimentoManaged;
	}
	 
	public Estabelecimento delete(Long id) {
		
	      EntityManager em = JPAUtil.getEntityManager();
	      
	      Estabelecimento estabelecimento = null;
	      
	      if(id <= 0) {
	    	  throw new DAOException("O id precisa ser maior do que 0", ErrorCode.BAD_REQUEST.getCode());
	      }
	              
	      try {
	             em.getTransaction().begin();
	             estabelecimento = em.find(Estabelecimento.class, id);
	             em.remove(estabelecimento);
	             em.getTransaction().commit();
	      } catch (IllegalArgumentException ex) {
	    	  em.getTransaction().rollback();
	    	  throw new DAOException("Estabelecimento informado para remoção não existe: " + ex.getMessage(), ErrorCode.NOT_FOUND.getCode());
	      } catch (RuntimeException ex) {
	    	  em.getTransaction().rollback();
	    	  throw new DAOException("Erro ao remover Estabelecimento no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());
	      } finally {
              em.close();
	      }
	       
	      return estabelecimento;
	}
	
	private boolean estabelecimentoIsValid(Estabelecimento estabelecimento) {
		  try {
			  if (estabelecimento.getNome().isEmpty())
		        return false;
		  } catch (NullPointerException ex) {
			  throw new DAOException("Estabelecimento com dados incompletos.", ErrorCode.BAD_REQUEST.getCode());
		  }
		   
		   return true;
   }
	 
	public List<Estabelecimento> getByPagination(int firstResult, int maxResults) {
			
	      EntityManager em = JPAUtil.getEntityManager();
	      
	      List<Estabelecimento> estabelecimentos = null;
	       
	      try {
	    	  estabelecimentos = em.createQuery("select e from Estabelecimento e", Estabelecimento.class)
	    			  .setFirstResult(firstResult - 1)
	    			  .setMaxResults(maxResults)
	    			  .getResultList();
	      } catch (RuntimeException ex){
	    	  throw new DAOException("Erro ao buscar Estabelecimentos do banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());  
	      }
	      	finally {
	          em.close();
	      }
	      
	      if(estabelecimentos.isEmpty()) {
	    	  throw new DAOException("Página de produtos vazia.", ErrorCode.NOT_FOUND.getCode()); 
	      }
	       
	      return estabelecimentos;
	}
	
	 public List<Estabelecimento> getByName(String name) {
		 
		 EntityManager em = JPAUtil.getEntityManager();
		 
		 List<Estabelecimento> estabelecimentos = null;
		 
		 try {
			estabelecimentos = em.createQuery("select e from Estabelecimento e where e.nome like :name ", Estabelecimento.class)
					.setParameter("name", "%" + name + "%" )
					.getResultList();
		} catch (RuntimeException ex) {
			throw new DAOException("Erro ao buscar Estabelecimentos por nome no banco de dados: " + ex.getMessage(), ErrorCode.SERVER_ERROR.getCode());  
		} finally {
			em.close();
		}
		 
		if(estabelecimentos.isEmpty()) {
			 throw new DAOException("A consulta não encontrou Estabelecimentos.", ErrorCode.NOT_FOUND.getCode());
		}
		 
		 return estabelecimentos;
	 }
}
