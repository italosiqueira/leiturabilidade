package br.eti.italosiqueira.leiturabilidade.indice;

/**
 * Interface dos indicadores de leiturabilidade.
 * 
 * @author italo
 *
 */
public interface IIndiceLegibilidade {

	/**
	 * Cálcula o índice de leiturabilidade a partir das quantidades indicada.
	 * 
	 * @param paragrafos quantidade de parágrafos.
	 * @param frases quantidade de frases.
	 * @param palavras quantidade de palavras.
	 * @param silabas quantidade de sílabas.
	 */
	public void classificar(int paragrafos, int frases, int palavras, int silabas);
	
	/**
	 * @return o nome desta implementação.
	 */
	public String getNome();

	/**
	 * @return retorna o resultado do índice de leiturabilidade.
	 */
	public String getLeiturabilidade();

	/**
	 * @return retorna o grau de escolaridade recomendado para compreensão do texto.
	 */
	public String getGrauEscolar();
	
	/**
	 * @return retorna o valor quantitativo da análise. Geralmente obtido a partir da 
	 * aplicação de alguma fórmula específica.
	 */
	public Double getIndice();

}