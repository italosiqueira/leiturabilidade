package br.eti.italosiqueira.leiturabilidade.extrator;

import java.io.PrintStream;
import java.io.Serializable;

import org.apache.commons.lang3.SerializationUtils;

/**
 * Uma classe que funciona basicamente como um estrutura de dados 
 * que permite o compartilhamento das estatísticas relevantes da 
 * análise de leiturabilidade sem comprometer os dados originais
 * das instâncias de {@link IExtratorEstatisticas}.
 * 
 * @author italo
 *
 */
public class Estatisticas implements Serializable {
	
	/**
	 * Versão da classe.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Um caractere <i>greek middle dot</i>, para servir como separador na impressão 
	 * das palavras em {@link #imprimirPara(PrintStream)}.
	 */
	private static final String CHAR_GREEK_MIDDLE_DOT = String.format("%c", 183);
	
	/**
	 * Um caractere <i>pilcrow</i>, para servir como separador na impressão das frases
	 * em {@link #imprimirPara(PrintStream)}.
	 */
	private static final String CHAR_PILCROW = String.format("%c", 182);
	
	/**
	 * <i>Array</i> de parágrafos encontrados.
	 */
	private String[] paragrafos;
	
	/**
	 * <i>Array</i> de frases encontradas.
	 */
	private String[] frases;
	
	/**
	 * <i>Array</i> de palavras encontradas.
	 */
	private String[] palavras;
	
	/**
	 * <i>Array</i> de sílabas encontradas.
	 */
	private String[] silabas;
	
	/**
	 * <i>String</i> do texto analisado.
	 */
	private String texto;
	
	public Estatisticas(Builder builder) {
		this.palavras= builder.palavras;

		this.paragrafos= builder.paragrafos;

		this.frases = builder.frases;

		this.silabas= builder.silabas;
		
		this.texto = builder.texto;
	}
	
	public String[] getParagrafos() {
		return paragrafos;
	}

	public String[] getFrases() {
		return frases;
	}

	public String[] getPalavras() {
		return palavras;
	}

	public String[] getSilabas() {
		return silabas;
	}
	
	public String getTexto() {
		return texto;
	}
	
	/**
	 * Imprime os dados para a <i>stream</i> de saída indicada. Os dados são
	 * impressos como um bloco de três <i>Strings</i> - frases, 
	 * palavras e sílabas - onde seus componentes são separados por caracteres 
	 * especiais.
	 * 
	 * @param out <i>stream</i> de saída dos dados.
	 */
	public void imprimirPara(PrintStream out) {
		String[] frases = getFrases();
		String[] palavras = getPalavras();
		String[] silabas = getSilabas();
		
		for (int i = 0; i < frases.length; i++) {
			if (i == frases.length - 1)
				out.print(frases[i]);
			else
				out.print(frases[i] + CHAR_PILCROW);
		}
		out.println();
		
		for (int i = 0; i < palavras.length; i++) {
			if (i == palavras.length - 1)
				out.print(palavras[i]);
			else
				out.print(palavras[i] + CHAR_GREEK_MIDDLE_DOT);
		}
		out.println();
		
		for (int i = 0; i < silabas.length; i++) {
			if (i == silabas.length - 1)
				out.print(silabas[i]);
			else
				out.print(silabas[i] + "-");
		}
		out.println();
	}
	
	/**
	 * Classe para construção de instâncias de {@link Estatisticas} via 
	 * clonagem dos <i>arrays</i> de dados.<br><br>
	 * 
	 * Exemplo de utilização:
	 * 
	 * <pre>
	 * new Estatisticas.Builder()
	 *	 .paragrafos(this.paragrafos)
	 *	 .frases(this.frases)
	 *	 .palavras(this.palavras)
	 *	 .silabas(this.silabas)
	 *	 .texto(this.texto)
	 *	 .build()
	 * </pre>
	 * 
	 * @author italo
	 *
	 */
	public static class Builder {
		
		private String[] paragrafos;

		private String[] frases;

		private String[] palavras;

		private String[] silabas;
		
		private String texto;

		public Builder paragrafos(String[] paragrafos) {
			this.paragrafos = SerializationUtils.clone(paragrafos);
			return this;
		}
		
		public Builder frases(String[] frases) {
			this.frases = SerializationUtils.clone(frases);
			return this;
		}
		
		public Builder palavras(String[] palavras) {
			this.palavras = SerializationUtils.clone(palavras);
			return this;
		}
		
		public Builder silabas(String[] silabas) {
			this.silabas = SerializationUtils.clone(silabas);
			return this;
		}
		
		public Builder texto(String texto) {
			this.texto = SerializationUtils.clone(texto);
			return this;
		}

		public Estatisticas build() {
			return new Estatisticas(this);
		}
		
	}

}
