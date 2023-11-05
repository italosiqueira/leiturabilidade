package br.eti.italosiqueira.leiturabilidade.extrator;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ExtratorEstatisticasRegexTest {
	
	private final static String EXEMPLO_TEXTO_01 = 
			"Nada mais bondoso do que oferecer aos demais aquilo que possui de sobra, guarda-chuva... Nao sejamos vis!\r\n"
			+ "— Mas porque fariamos isso?!\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "— A roda da vida gira.";
	
	private static final String EXEMPLO_TEXTO_02 = "85000540-2023.08.000.1253 Mandado de Segurança Cível - Impetrante: João Bosco de Freitas - Impetrado: Governador do Estado do Ceará - Impetrado: Secretário da Segurança Pública e Defesa Social do Estado do Ceará - - Isto posto, CONCEDO PARCIALMENTE A LIMINAR REQUESTADA, para determinar tão somente que as autoridades coatoras se abstenham de impor decesso remuneratório ao impetrante, quando de sua reforma, devendo o requerente continuar a receber seus proventos no mesmo quantitativo percebido na condição de inativo da reserva remunerada, até ulterior deliberação por este Órgão Especial. Empós, determino, com a devida urgência, a notificação da autoridade tida como coatora para prestação de informações no prazo decendial, bem como a intimação do Estado (Pessoa Jurídica) através de sua Procuradoria, de conformidade com o disposto no inciso, II, do art. 7º, da Lei 12.016/2009, tudo sem prejuízo de posterior reexame das questões vertidas. Ato contínuo, ouça-se a douta Procuradoria-Geral de Justiça e, por fim, voltem-me conclusos para julgamento. Expedientes necessários URGENTES. Fortaleza, 2 de maio de 2023. DESEMBARGADOR FRANCISCO DARIVAL BESERRA PRIMO Relator - Advs: Pedro Ferreira Freitas (OAB: 4030/CE)";
	
	private static final String EXEMPLO_TEXTO_03 = "Três pratos de trigo para três tigres tristes.\r\nA batina do padre Pedro é preta.";
	
	private static final String EXEMPLO_TEXTO_04 = "Decisão – o Juiz decide uma questão dentro do processo, mas ainda não julga\r\n"
			+ "\r\n"
			+ "Despacho – o Juiz determina alguma ação para a Vara impulsionar o processo\r\n"
			+ "\r\n"
			+ "Julgamento – o Juiz julga definitivamente o processo\r\n"
			+ "\r\n"
			+ "Arquivo – o processo foi julgado e arquivado\r\n"
			+ "\r\n"
			+ "Conciliação – o processo foi submetido à audiência de conciliação\r\n"
			+ "\r\n"
			+ "Cálculos – o Juiz determinou a atualização de algum valor monetário\r\n"
			+ "\r\n"
			+ "Processo Distribuído – o processo foi encaminhado para um juiz específico\r\n"
			+ "\r\n"
			+ "Processo Baixado – o processo foi julgado e não cabe recurso\r\n"
			+ "\r\n"
			+ "Processo Redistribuído – o órgão julgador (Vara) foi mudado\r\n"
			+ "\r\n"
			+ "Audiência – foi agendada uma audiência com as partes e o juiz\r\n"
			+ "\r\n"
			+ "Citação – a parte ré foi comunicada sobre o início do processo\r\n"
			+ "\r\n"
			+ "Conclusão – o processo siau da Secretaria e foi encaminhado ao juiz\r\n"
			+ "\r\n"
			+ "Intimação – as partes foram comunicadas sobre alguma decisão do juiz\r\n"
			+ "\r\n"
			+ "Juntada – alguma petição ou documento foi inserido no processo\r\n"
			+ "\r\n"
			+ "Perícia – o Juiz determinou a investigação de algum fato por um profissional específico (perito)\r\n"
			+ "\r\n"
			+ "Devolução de Mandado – o Oficial de Justiça devolveu o mandado de intimação ou citação";

	private IExtratorEstatisticas extrator = null;
	
	@Before
	public void setUp() {
		this.extrator = new ExtratorEstatisticasRegex();
	}
	
	@Test
	public void testClassificadorLeiturabilidade() {
		
		extrator.analisar(EXEMPLO_TEXTO_01);
		
		Estatisticas estatisticas = extrator.getEstatisticas();
		
		assertEquals("Falha! Quantidade de PARÁGRAFOS é diferente!", 3, estatisticas.getParagrafos().length);
		
		assertEquals("Falha! Quantidade de FRASES é diferente!", 4, estatisticas.getFrases().length);
		
		assertEquals("Falha! Quantidade de PALAVRAS é diferente!", 28, estatisticas.getPalavras().length);
		
		assertEquals("Falha! Quantidade de SÍLABAS é diferente!", 52, estatisticas.getSilabas().length);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testClassificadorLeiturabilidadeNullString() {		
		extrator.analisar(null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testClassificadorLeiturabilidadeEmptyString() {		
		extrator.analisar("     "
				+ "       \r\n");
	}
	
	@Test
	public void testClassificadorLeiturabilidadeJudicial() {
		
		extrator.analisar(EXEMPLO_TEXTO_02);
		
		Estatisticas estatisticas = extrator.getEstatisticas();
		
		estatisticas.imprimirPara(System.out);
		
		assertEquals("Falha! Quantidade de PARÁGRAFOS é diferente!", 1, estatisticas.getParagrafos().length);
		
		assertEquals("Falha! Quantidade de FRASES é diferente!", 7, estatisticas.getFrases().length);
		
		assertEquals("Falha! Quantidade de PALAVRAS é diferente!", 178, estatisticas.getPalavras().length);
		
		assertEquals("Falha! Quantidade de SÍLABAS é diferente!", 434, estatisticas.getSilabas().length);
	}
	
	@Test
	public void testClassificadorLeiturabilidadeJudicialGlossario() {
		
		extrator.analisar(EXEMPLO_TEXTO_04);
		
		Estatisticas estatisticas = extrator.getEstatisticas();
		
		estatisticas.imprimirPara(System.out);
		
		assertEquals("Falha! Quantidade de PARÁGRAFOS é diferente!", 16, estatisticas.getParagrafos().length);
		
		assertEquals("Falha! Quantidade de FRASES é diferente!", 16, estatisticas.getFrases().length);
		
		assertEquals("Falha! Quantidade de PALAVRAS é diferente!", 182, estatisticas.getPalavras().length);
		
		assertEquals("Falha! Quantidade de SÍLABAS é diferente!", 414, estatisticas.getSilabas().length);
	}
	
	@Test
	public void testClassificadorLeiturabilidadeCache() {
		
		// Primeira execução
		extrator.analisar(EXEMPLO_TEXTO_01);
		
		// Segunda execução - observar limpeza dos dados
		extrator.analisar(EXEMPLO_TEXTO_03);
		
		Estatisticas estatisticas = extrator.getEstatisticas();
		
		estatisticas.imprimirPara(System.out);
		
		assertEquals("Falha! Quantidade de PARÁGRAFOS é diferente!", 2, estatisticas.getParagrafos().length);
		
		assertEquals("Falha! Quantidade de FRASES é diferente!", 2, estatisticas.getFrases().length);
		
		assertEquals("Falha! Quantidade de PALAVRAS é diferente!", 15, estatisticas.getPalavras().length);
		
		assertEquals("Falha! Quantidade de SÍLABAS é diferente!", 25, estatisticas.getSilabas().length);
	}

}
