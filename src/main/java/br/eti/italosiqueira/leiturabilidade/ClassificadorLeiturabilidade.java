package br.eti.italosiqueira.leiturabilidade;

import java.util.ArrayList;
import java.util.List;

import br.eti.italosiqueira.leiturabilidade.extrator.Estatisticas;
import br.eti.italosiqueira.leiturabilidade.extrator.IExtratorEstatisticas;
import br.eti.italosiqueira.leiturabilidade.indice.IIndiceLegibilidade;

/**
 * Uma implementação simples do {@link IClassificadorLeiturabilidade}.
 * 
 * Esta implementação analisa o texto fornecido e submete as estatísticas 
 * a todos os índices cadastrados na instância.
 * 
 * @author italo
 *
 */
public class ClassificadorLeiturabilidade implements IClassificadorLeiturabilidade {
	
	/**
	 * O extrator de estatísticas de texto utilizado por este classificador.
	 */
	private IExtratorEstatisticas estatisticasTexto = null;
	
	/**
	 * Uma {@link List} dos índices de legibilidades que serão usados sobre os 
	 * resultados 
	 */
	private List<IIndiceLegibilidade> indices = new ArrayList<IIndiceLegibilidade>();
	
	/**
	 * Construtor padrão.
	 * 
	 * @param extrator extrator de estatísticas de texto utilizado por este classificador.
	 */
	public ClassificadorLeiturabilidade(IExtratorEstatisticas extrator) {
		this.estatisticasTexto = extrator;
	}
	
	@Override
	public IExtratorEstatisticas getEstatisticasTexto() {
		return estatisticasTexto;
	}

	@Override
	public List<IIndiceLegibilidade> getIndices() {
		return indices;
	}

	@Override
	public IClassificadorLeiturabilidade addIndiceLegibilidade(IIndiceLegibilidade indiceLegibilidade) {
		this.indices.add(indiceLegibilidade);
		return this;
	}
	
	@Override
	public IClassificadorLeiturabilidade addIndiceLegibilidade(List<IIndiceLegibilidade> indicesLegibilidade) {
		this.indices.addAll(indicesLegibilidade);
		return this;
	}
	
	@Override
	public IClassificadorLeiturabilidade analisar(String texto) {
		
		this.estatisticasTexto.analisar(texto);
		
		Estatisticas estatisticas = this.estatisticasTexto.getEstatisticas();
		
		for (IIndiceLegibilidade indice : indices) {
			indice.classificar(
					estatisticas.getParagrafos().length, 
					estatisticas.getFrases().length, 
					estatisticas.getPalavras().length, 
					estatisticas.getSilabas().length);
		}
		
		return this;
	}
	
}
