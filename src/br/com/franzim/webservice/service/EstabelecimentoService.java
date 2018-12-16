package br.com.franzim.webservice.service;

import java.util.List;

import br.com.franzim.webservice.model.dao.EstabelecimentoDAO;
import br.com.franzim.webservice.model.domain.Estabelecimento;

public class EstabelecimentoService {

	private EstabelecimentoDAO dao = new EstabelecimentoDAO();

	public List<Estabelecimento> getEstabelecimentos() {
		return dao.getAll();
	}

	public Estabelecimento getEstabelecimento(Long id) {
		return dao.getById(id);
	}

	public Estabelecimento saveEstabelecimento(Estabelecimento estabelecimento) {
		return dao.save(estabelecimento);
	}

	public Estabelecimento updateEstabelecimento(Estabelecimento estabelecimento) {
		return dao.update(estabelecimento);
	}

	public Estabelecimento deleteEstabelecimento(Long id) {
		return dao.delete(id);
	}

	public List<Estabelecimento> getEstabelecimentosByPagination(int firstResult, int maxResults) {
		return dao.getByPagination(firstResult, maxResults);
	}

	public List<Estabelecimento> getEstabelecimentoByName(String name) {
		return dao.getByName(name);
	}
}
