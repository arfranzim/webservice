package br.com.franzim.webservice.resources;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.com.franzim.webservice.model.domain.Estabelecimento;
import br.com.franzim.webservice.service.EstabelecimentoService;

@Path("/estabelecimentos")
public class EstabelecimentoResource {

	private EstabelecimentoService service = new EstabelecimentoService();

	@GET
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public List<Estabelecimento> getEstabelecimentos() {
		return service.getEstabelecimentos();
	}
	
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Estabelecimento getEstabelecimento(@PathParam("id") long id) {
		return service.getEstabelecimento(id);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Estabelecimento save(Estabelecimento estabelecimento) {
		return service.saveEstabelecimento(estabelecimento);
	}
	
	@PUT
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON + ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Estabelecimento update(@PathParam("id") long id, Estabelecimento estabelecimento) {
		estabelecimento.setId(id);
		return service.updateEstabelecimento(estabelecimento);
	}
	
	@DELETE
	@Path("{id}")
	public Estabelecimento delete(@PathParam("id") long id) {
		return service.deleteEstabelecimento(id);
	}
}
