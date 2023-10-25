package br.eti.italosiqueira.leiturabilidade.indice;

/**
 * Impementação do índice Flesch–Kincaid adaptado para o Portugês Brasileiro.
 * 
 * @see <a href="https://medium.com/ladies-that-ux-br/calculadora-de-leiturabilidade-d69c787ee2aa">Calculadora de leiturabilidade</a>
 * @author italo
 *
 */
public class FleschBrasileiroSimples implements IIndiceLegibilidade {

	private static final String INDICE_NOME = "Índice de Facilidade de Leitura de Flesch";

	private static final Double MAXIMO = Double.valueOf(100);
	
	private static final Double MINIMO = Double.valueOf(0);
	
	private static final double[] THRESHOLD = new double[] { 25.0, 50.0, 75.0, 100.0 };
	
	/**
	 * <i>Array</i> dos níveis de leiturabilidade.
	 */
	private static final String[] LEITURABILIDADE = new String[] { "Muito difícil", "Difícil", "Fácil", "Muito fácil" };
	
	/**
	 * <i>Array</i> dos níveis de escolaridade.
	 */
	private static final String[] GRAU_ESCOLAR = new String[] { "Ensino Superior", "Ensino Médio", "6º ao 9º Ano", "1º ao 5º Ano" };
	
	private Double indice = null;
	
	@Override
	public void classificar(int paragrafos, int frases, int palavras, int silabas) {
		double resultado = 248.835 
				- (1.015 * ((double) palavras/ (double) frases)) 
				- (84.6 * ((double) silabas / (double) palavras));
		
		this.indice = Double.valueOf(resultado);	
	}

	@Override
	public String getGrauEscolar() {
		if (indiceCalculado()) {
			double indiceNormalizado = indice.doubleValue();
			if (MAXIMO.compareTo(Double.valueOf(indice)) < 0)
				indiceNormalizado = MAXIMO.floatValue();
			if (MINIMO.compareTo(Double.valueOf(indice)) > 0)
				indiceNormalizado = MINIMO.floatValue();
			for (int i = 0; i < THRESHOLD.length; i++) {
				if (indiceNormalizado <= THRESHOLD[i])
					return GRAU_ESCOLAR[i];
			} 
		}
		return "Falha na consulta";
	}

	@Override
	public Double getIndice() {
		return this.indice == null ? 
				Double.valueOf(-1.0) : this.indice;
	}

	@Override
	public String getLeiturabilidade() {
		
		if (indiceCalculado()) {
			double indiceNormalizado = indice.doubleValue();
			if (MAXIMO.compareTo(Double.valueOf(indice)) < 0)
				indiceNormalizado = MAXIMO.floatValue();
			if (MINIMO.compareTo(Double.valueOf(indice)) > 0)
				indiceNormalizado = MINIMO.floatValue();
			for (int i = 0; i < THRESHOLD.length; i++) {
				if (indiceNormalizado <= THRESHOLD[i])
					return LEITURABILIDADE[i];
			} 
		}
		return "Falha na consulta";
	}
	
	public String getNome() {
		return INDICE_NOME;
	}

	private boolean indiceCalculado() {
		return this.indice != null;
	}
}
