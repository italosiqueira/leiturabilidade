package br.eti.italosiqueira.leiturabilidade.indice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FleschBrasileiroSimplesTest {
	
	private IIndiceLegibilidade indiceLegibilidade = new FleschBrasileiroSimples();

	@Test
	public void testGetGrauEscolar() {
		this.indiceLegibilidade.classificar(3, 4, 26, 51);
		
		assertEquals("Classificação de Grau Escolar diferente!", "1º ao 5º Ano", indiceLegibilidade.getGrauEscolar());
		
		this.indiceLegibilidade.classificar(1, 6, 172, 426);
		
		assertEquals("Classificação de Grau Escolar diferente!", "Ensino Superior", indiceLegibilidade.getGrauEscolar());
	}

	@Test
	public void testGetIndice() {
		this.indiceLegibilidade.classificar(3, 4, 26, 51);
		
		assertEquals("Índices diferentes!", 76.29, indiceLegibilidade.getIndice().doubleValue(), 0.01);
		
		this.indiceLegibilidade.classificar(1, 6, 172, 426);
		
		assertEquals("Índices diferentes!", 10.20, indiceLegibilidade.getIndice().doubleValue(), 0.01);
	}

	@Test
	public void testGetLeiturabilidade() {
		this.indiceLegibilidade.classificar(3, 4, 26, 51);
		
		assertEquals("Classificação de Legibilidade diferente!", "Muito fácil", indiceLegibilidade.getLeiturabilidade());
		
		this.indiceLegibilidade.classificar(1, 6, 172, 426);
		
		assertEquals("Classificação de Legibilidade diferente!", "Muito difícil", indiceLegibilidade.getLeiturabilidade());
	}

}
