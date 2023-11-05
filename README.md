<div align="center" >
  <h1>📖 Leiturabilidade</h1>
  <h4>Biblioteca para avaliação de textos em Java</h4>
</div>

# Sobre

Este projeto é uma biblioteca genérica para extração de estatísticas e análise da qualidade de leiturabilidade de textos.

O projeto oferece a estrutura básica para implementação tanto de *extratores de estatísticas* quanto de *fórmulas de classificação de leiturabilidade* a partir destas estatísticas, além de já implementar tanto um extrator básico e uma fórmula de classificação bastante conhecida.

Ambas as implementações consideram somente textos em português.

## Sobre extratores de estatísticas

Atualmente nossa *interface* suporta a extração das seguintes informações básicas:

- Parágrafos
- Frases
- Palavras
- Sílabas

Como consequência, hoje a *interface* para as fórmulas de classificação de leiturabilidade é limitada em relação a esses parâmetros de entrada.

A implementação do extrator presente na biblioteca é baseada em *expressões regulares* para todas as informações acima, com exceção da contagem silábica. Para extrair a quantidade de sílabas foi utilizado o módulo de separação silábica do Grupo FalaBrasil da Universidade Federal do Pará (UFPA). O módulo pode ser encontrado no projeto [annotator](https://github.com/falabrasil/annotator "annotator on GitHub"), desenvolvido pelo grupo.

É possível que o número de sílabas obtido nem sempre seja o número exato presente no texto, dada a não-trivialidade desse tipo de tarefa, ou seja, a contagem de sílabas falha em algumas palavras.

Outro ponto a ser levado em consideração para uma correta avaliação é a *presença de erros de ortografia*. Tais erros podem provocar a contagem imprecisa de sílabas, palavras, frases e parágrafos, que irão refletir na fórmula de leiturabilidade.

## Sobre fórmulas de leiturabilidade

A *interface* para as fórmulas de cálculo de índice de leiturabilidade atualmente está fortemente acoplada aos dados que vimos na seção anterior.

A biblioteca conta atualmente com uma implementação baseada na [fórmula Flesch-Kincaid](https://www.ufrgs.br/bioetica/ilfk.htm) adaptada para a língua portuguesa em 1996 pelos pesquisadores Teresa B. F. Martins, Claudete M. Ghiraldelo, M. Graças V. Nunes e Osvaldo N. Oliveira Jr., da USP de São Carlos.

Os testes de Flesch-Kincaid utilizam duas variáveis bastante comuns: o comprimento das frases e a complexidade das palavras:

- comprimento das frases: medida a partir do número médio de palavras por sentença;
- complexidade das palavras: medida a partir do número médio de sílabas por palavra.

### Fórmula utilizada

A versão da fórmula implementada por padrão nesta biblioteca é a seguinte:

$$
248,835 - \left(1,015 * \frac {<palavras>} {<frases>} \right) - \left( 84,6 * \frac {<sílabas>} {<palavras>} \right)
$$

onde:

- palavras: quantidade de palavras presentes no texto;
- frases: quantidade de frases presentes no texto;
- sílabas: quantidaded total de sílabas em todas as palavras presentes no texto.

Maiores detalhes sobre a interpretação da fórmula podem ser encontradas em <a href="#ref-goldim">[2]</a>.

# 🖥️ Stack

- Java 1.8

# ⚡ Como utilizar

Dentro do projeto a principal classe é a *interface* de classificação `IClassificadorLeiturabilidade`, responsável por integrar o extrator de estatísticas e o(s) índice(s) (fórmula) de leiturabilidade. 

O pacote conta com classes de testes unitários, uma para cada componente principal da biblioteca: extrator de dados, índice de leiturabilidade (fórmula de leiturabilidade) e classificador. Uma simples análise delas é suficiente para entender como utilizar estes componentes.

Para criar uma instância da implementação padrão do classificado, com a implementação (a única até o momento) de um extrator:

```java
IClassificadorLeiturabilidade classificadorFleschSimples = 
	new ClassificadorLeiturabilidade(new ExtratorEstatisticasRegex());
```

Em seguida, é necessário adicionar uma fórmula de leiturabilidade para geração dos índices de leiturabilidade:

```java
classificadorFleschSimples.addIndiceLegibilidade(
	new FleschBrasileiroSimples());
```

Por fim, executar a análise e extrair o índice de leiturabilidade para leitura dos resultados:

```java
classificador.analisar(EXEMPLO_TEXTO_01);
IIndiceLegibilidade indice = classificador.getIndices().get(0);

System.out.println("Escolaridade sugerida: " + indice.getGrauEscolar());
```

Este projeto utiliza como gerenciador de projeto o [Apache Maven](https://maven.apache.org/). Antes da primeira execução é necessário instalar adequadamente o módulo `annotator` do Grupo FalaBrasil. As instruções encontram-se em uma README dentro da pasta *lib*.

## Extraindo as estatísticas

É possível obter os dados dos componentes de interesse utilizados aqui (frases, palavras, sílabas) através da instância de ```Estatistica``` que pode ser obtida a partir do Extrator de Estatísticas utilizado. Ela possui um método para simples impressão dos dados e verificação das informações extraídas:

```java
// Um classificador
IClassificadorLeiturabilidade classificadorFleschSimples = 
				new ClassificadorLeiturabilidade(new ExtratorEstatisticasRegex());

/*
 * Após a análise...
 */
IExtratorEstatisticas extrator = classificadorFleschSimples.getEstatisticasTexto();

// Nossa instância de estatísticas
Estatisticas estatisticas = extrator.getEstatisticas();

// Impressão das informações para a saída padrão
estatisticas.imprimirPara(System.out);

// Obtendo os componentes individuais
String[] paragrafos = estatisticas.getParagrafos();
String[] frases = estatisticas.getFrases();
String[] palavras = estatisticas.getPalavras();
String[] silabas = estatisticas.getSilabas();

```

A implementação em nosso extrator (```ExtratorEstatisticasRegex```) sempre devolve uma cópia dos *arrays* primários encapsulados em um novo objeto a fim de evitar a manipulação indevida destes dados diretamente.

Depois disso basta jogar os quantitativos nas fórmulas que desejar.

# Referências

1. <a id="ref-falabrasil">GRUPO FALABRASIL</a>. Grupo FalaBrasil on GitHub.com, c2023. falabrasil/annotator. Disponível em: <https://github.com/falabrasil/annotator>. Acesso em: 25 de out. de 2023
2. <a id="ref-goldim">GOLDIM, José Roberto</a>. Índices de Legibilidade de Flesch-Kincaid e de Facilidade de Leitura de Flesch. UFRGS, c2023. Disponível em: <https://www.ufrgs.br/bioetica/ilfk.htm>. Acesso em: 25 de out. de 2023
3. <a id="ref-legibilidade">SOBRE - Análise de Legibilidade Textual</a>. ALT - Análise de Legibilidade Textual, 2023. Disponível em: <https://legibilidade.com/sobre>. Acesso em: 25 de out. de 2023

<div style="display: inline_block" align="center" >
	<img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
</div>