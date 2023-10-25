package br.eti.italosiqueira.leiturabilidade;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.eti.italosiqueira.leiturabilidade.extrator.ExtratorEstatisticasRegex;
import br.eti.italosiqueira.leiturabilidade.indice.FleschBrasileiroSimples;
import br.eti.italosiqueira.leiturabilidade.indice.IIndiceLegibilidade;

public class ClassificadorLeiturabilidadeTest {
	
	private static IClassificadorLeiturabilidade classificadorFleschSimples = null;
	
	private final static String EXEMPLO_TEXTO_01 = 
			"Nada mais bondoso do que oferecer aos demais aquilo que possui de sobra, guarda-chuva... Não sejamos vis!\r\n"
			+ "— Mas porque faríamos isso?!\r\n"
			+ "\r\n"
			+ "\r\n"
			+ "— A roda da vida gira.";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ClassificadorLeiturabilidadeTest.classificadorFleschSimples = 
				new ClassificadorLeiturabilidade(new ExtratorEstatisticasRegex());
		ClassificadorLeiturabilidadeTest.classificadorFleschSimples.addIndiceLegibilidade(
				new FleschBrasileiroSimples());
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ClassificadorLeiturabilidadeTest.classificadorFleschSimples = null;
	}

	@Test
	public void testAnalisarFleschSimples() {
		IClassificadorLeiturabilidade classificador = 
				ClassificadorLeiturabilidadeTest.classificadorFleschSimples;
		
		IIndiceLegibilidade indice =
				classificador.analisar(EXEMPLO_TEXTO_01).getIndices().get(0);
		
		assertEquals("Classificação de Grau Escolar diferente!", "1º ao 5º Ano", indice.getGrauEscolar());
	}

}
