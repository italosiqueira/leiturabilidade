package br.eti.italosiqueira.leiturabilidade.extrator;


/**
 * Interface principal do extrator de estatísticas do texto. As estatísticas a 
 * serem extraídas são definidas na classe {@link Estatisticas}.
 * 
 * @author italo
 *
 */
public interface IExtratorEstatisticas {

	/**
	 * Analisa a <code>String</code> a fim de obter as suas estatísticas.
	 * 
	 * @param texto {@link String} para analisar.
	 * @throws IllegalArgumentException caso uma {@link String} vazia seja especificada.
	 */
	void analisar(String texto) throws IllegalArgumentException;

	/**
	 * Deve retornar uma cópia das estatísticas calculadas pela última vez. Uma cópia
	 * é recomendada para preservar as informações internas.
	 * 
	 * @return uma instância de {@link Estatisticas} com as últimas informações extraídas.
	 * @throws IllegalStateException caso nenhuma análise tenha sido realizada.
	 */
	Estatisticas getEstatisticas() throws IllegalStateException;

}