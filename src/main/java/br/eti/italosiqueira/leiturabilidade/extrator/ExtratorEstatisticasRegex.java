package br.eti.italosiqueira.leiturabilidade.extrator;

import java.text.Normalizer;
import java.util.Objects;
import java.util.regex.Pattern;

import ufpa.falabrasil.Syllabificator;

/**
 * Implementação para extração de dados do texto baseado no uso de <i>regex</i>. A 
 * única exceção é o componente de divisão silábica, que utiliza o módulo de separação 
 * silábica do projeto <a href="https://github.com/falabrasil/annotator">annotator</a> do Grupo FalaBrasil da UFPA.
 * 
 * @see <a href="https://github.com/falabrasil">Grupo FalaBrasil - GitHub</a>
 * @see <a href="https://ufpafalabrasil.gitlab.io/">Grupo FalaBrasil</a>
 * 
 * @author italo
 *
 */
public class ExtratorEstatisticasRegex implements IExtratorEstatisticas {

	/**
	 * Regex para localizar <i>Strings</i> não vazias.
	 */
	private static final String REGEX_NOT_EMPTY_STRING = "\\w+";
	
	/**
	 * Regex para localizar períodos com o auxílio do caractere <a href="https://www.ascii-code.com/character/%C2%B6" target="_top">¶</a>. 
	 */
	private static final String REGEX_PERIODO_WITH_PILCROW = "(?<=[?!\\.]++)[\\u00B6\\u2202\\s]";
	
	/**
	 * Regex para localizar espaços.
	 */
	private static final String REGEX_ESPACAMENTO = "\\s+";
	
	/**
	 * Regex para localizar caracteres que denotem fim de sentenças.
	 */
	private static final String REGEX_SIMBOLOS_FIM_SENTENCA = "[?!,;:\\.]+\\s";
	
	/**
	 * Regex para localizar parágrafos.
	 */
	private static final String REGEX_PARAGRAFO = "(\\r\\n\\s*)+|\\n";
	
	/**
	 * Um caractere <i>pilcrow</i>, para servir como separador de parágrafos na busca por frases.
	 */
	private static final String CHAR_PILCROW = String.format("%c", 182);
	
	/**
	 * Uma instância de <i>Pattern</i> para a <i>regex</i> de <i>Strings</i> 
	 * não vazias.
	 */
	private static final Pattern p = Pattern.compile(REGEX_NOT_EMPTY_STRING);

	/**
	 * <i>String</i> do texto a ser analisado.
	 */
	private String texto;

	/**
	 * <i>Array</i> para armazenar as palavras encontradas.
	 */
	private String[] palavras;
	
	/**
	 * <i>Array</i> para armazenar os parágrafos encontrados.
	 */
	private String[] paragrafos;
	
	/**
	 * <i>Array</i> para armazenar as frases encontradas.
	 */
	private String[] frases;
	
	/**
	 * <i>Array</i> para armazenar as sílabas encontradas.
	 */
	private String[] silabas;

	// Pode ser static?
	/**
	 * Componente para divisão silábica.
	 */
	private Syllabificator s = new Syllabificator();

	/**
	 * Rotina para extração dos parágrafos do texto a partir de <i>regex</i>.
	 */
	private void extrairParagrafos() {
		this.paragrafos = this.texto.split(REGEX_PARAGRAFO);
	}
	
	/**
	 * Rotina para extração das frases do texto a partir de <i>regex</i>.
	 */
	private void extrairFrases() {
		this.frases = this.texto.replaceAll(REGEX_PARAGRAFO, "." + CHAR_PILCROW).split(REGEX_PERIODO_WITH_PILCROW);
	}
	
	/**
	 * Rotina para extração das palavras do texto a partir de <i>regex</i>.
	 */
	private void extrairPalavras() {
		this.palavras = this.texto
				.replaceAll(REGEX_SIMBOLOS_FIM_SENTENCA, " ")
				//.replaceAll(REGEX_SIMBOLOS, "")
				.split(REGEX_ESPACAMENTO);
	}
	
	/**
	 * Rotina para extração das sílabas do texto. Executado sobre o <i>array</i>
	 * de palavras.
	 */
	private void extrairSilabas() {
		if (this.palavras == null || this.palavras.length == 0)
			extrairPalavras();

		StringBuilder builder = new StringBuilder();

		// TODO Usar Java Streams seria uma boa idéia aqui
		for (short b = 0; b < this.palavras.length; b++) {
			String str1 = Normalizer.normalize(this.palavras[b].toLowerCase(), Normalizer.Form.NFD);
			String str2 = "";
			
			try {
				str2 = str2 + this.s.syllabs(str1);
				
				if (str2.length() > 0) {
					builder.append(str2 + "-");
				}
			} catch (IndexOutOfBoundsException e) {
				// Conjunto de caracteres formado somente por símbolos
			}
		}
		builder.deleteCharAt(builder.length() - 1);

		this.silabas = builder.toString().split("-");
	}
	
	/**
	 * Apaga todos os dados extraídos e o texto.
	 */
	private void limpar() {
		this.texto = null;
		this.palavras = null;
		this.paragrafos = null;
		this.frases = null;
		this.silabas = null;
	}
	
	/**
	 * Verifica se a <i>String</i> é diferente de vazia. Uma <code>String</code>
	 * será considerada vazia se:
	 * <ul>
	 * 	<li> for igual a <code>null</code>;
	 *  <li> for composta somente de caracteres considerados espaços em branco.
	 * </ul>
	 * 
	 * @param entrada <i>String</i> a ser analisada.
	 * @return <code>false</code> caso um caractere inválido seja encontrado ou se 
	 * <code>entrada</code> igual a <code>null</code>.
	 */
	private boolean possuiCaracteresValidos(String entrada) {
		return entrada == null ? false : p.matcher(entrada).find();
	}

	@Override
	public void analisar(String texto) throws IllegalArgumentException {
		
		if (!possuiCaracteresValidos(texto))
			throw new IllegalArgumentException("String vazia");
		
		limpar();

		this.texto = texto;

		if (this.texto.length() > 0) {
			extrairParagrafos();
			extrairFrases();
			extrairPalavras();
			extrairSilabas();
		}
	}
	
	public Estatisticas getEstatisticas() throws IllegalStateException {
		
		if (this.texto == null 
				|| this.paragrafos == null 
				|| this.frases == null 
				|| this.palavras == null 
				|| this.silabas == null) {
			throw new IllegalStateException("Nenhuma análise realizada");
		}
		
		return new Estatisticas.Builder()
				.paragrafos(this.paragrafos)
				.frases(this.frases)
				.palavras(this.palavras)
				.silabas(this.silabas)
				.texto(this.texto)
				.build();
	}

	@Override
	public int hashCode() {
		return Objects.hash(texto);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExtratorEstatisticasRegex other = (ExtratorEstatisticasRegex) obj;
		return Objects.equals(texto, other.texto);
	}

}
