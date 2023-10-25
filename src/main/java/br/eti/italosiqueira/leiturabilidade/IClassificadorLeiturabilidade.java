package br.eti.italosiqueira.leiturabilidade;

import java.util.List;

import br.eti.italosiqueira.leiturabilidade.extrator.IExtratorEstatisticas;
import br.eti.italosiqueira.leiturabilidade.indice.IIndiceLegibilidade;


/**
 * Interface principal para o classificador. O classificador é o responsável 
 * por agregar a extração de informações do texto com os índices de leiturabilidade 
 * - classes que emitem as mais variadas avaliações acerca dos resultados.
 * 
 * @author italo
 *
 */
public interface IClassificadorLeiturabilidade {
	
	/**
	 * Recupera a instância efetiva do extrator de estatísticas de texto deste 
	 * classificador.
	 * 
	 * @return instância efetiva do extrator de estatísticas de texto local.
	 */
	IExtratorEstatisticas getEstatisticasTexto();

	/**
	 * Recupera a lista de índices de legibilidade instaladas neste classificador.
	 * 
	 * @return lista de índices de legibilidade local.
	 */
	List<IIndiceLegibilidade> getIndices();

	/**
	 * Adiciona uma implementação de {@link IIndiceLegibilidade} a ser utilizada 
	 * pelo classificador. Geralmente após a extração das informações do texto é
	 * possível solicitar a sua classificação de acordo com os índices instalados.
	 * 
	 * @param indiceLegibilidade implementação de índice de legibilidade.
	 * @return esta instância de {@link IClassificadorLeiturabilidade}.
	 */
	IClassificadorLeiturabilidade addIndiceLegibilidade(IIndiceLegibilidade indiceLegibilidade);

	/**
	 * Adiciona uma lista de implementações de {@link IIndiceLegibilidade} a serem 
	 * utilizadas pelo classificador. Veja mais em <code>addIndiceLegibilidade</code>.
	 * 
	 * @param indicesLegibilidade uma {@link List} de implementações de índices de legibilidade.
	 * @return esta instância de {@link IClassificadorLeiturabilidade}.
	 */
	IClassificadorLeiturabilidade addIndiceLegibilidade(List<IIndiceLegibilidade> indicesLegibilidade);

	
	/**
	 * Inicia a execução da rotina de análise de dados sobre o texto indicado.
	 * 
	 * @param texto {@link String} para ser analisada.
	 * @return esta instância de {@link IClassificadorLeiturabilidade}.
	 */
	IClassificadorLeiturabilidade analisar(String texto);

}