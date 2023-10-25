# Módulo de separação silábica

Este projeto utiliza o módulo de separação silábica do Grupo FalaBrasil da Universidade Federal do Pará (UFPA). O módulo pode ser encontrado no projeto [annotator](https://github.com/falabrasil/annotator "annotator on GitHub"), desenvolvido pelo grupo.

## Instalação

### Obter a biblioteca fb_nlplib.jar

Faça o download da biblioteca `fb_nlplib.jar` diretamente do repositório do projeto [annotator](https://github.com/falabrasil/annotator). Mova o arquivo *jar* para um local apropriado dentro da estrutura do seu projeto.

### Configurar o Maven

Próximo passo é instalar a biblioteca de modo que o Maven possa encontrá-la. Para isso, vamos configurar um *plugin* do Maven.

Dentro o seu *pom.xml*, na seção **plugins**, acrescente:

```
<plugin>
  <artifactId>maven-install-plugin</artifactId>
  <version>2.5.2</version>
  <executions>
    <execution>
      <id>install-external</id>
      <phase>clean</phase>
      <configuration>
        <!-- Use o diretório que você escolheu para guardar o arquivo -->
        <file>${basedir}/lib/fb_nlplib.jar</file>
        <repositoryLayout>default</repositoryLayout>
        <groupId>ufpa.falabrasil</groupId>
        <artifactId>falabrasilnlp</artifactId>
        <version>1.0.0</version>
        <packaging>jar</packaging>
        <generatePom>true</generatePom>
      </configuration>
      <goals>
          <goal>install-file</goal>
      </goals>
    </execution>
  </executions>
</plugin>
```

Basicamente estamos orientando a *engine* do Maven para instalar localmente a biblioteca em nosso repositório local durante um *clean*.

Em seguida, podemos fazer uso da biblioteca normalmente como qualquer outra, bastando identificá-la como uma dependência normal conforme os dados apresentados acima. No nosso *pom*, em `dependencies`:

```
    <dependency>
	  <groupId>ufpa.falabrasil</groupId>
	  <artifactId>falabrasilnlp</artifactId>
	  <version>1.0.0</version>
	</dependency>
```

#### Instalação alternativa

Também é possível instalar a dependência em nosso repositório local via `mvn install`:

```
$ mvn install:install-file -Dfile=c:\fb_nlplib.jar -DgroupId=ufpa.falabrasil 
	-DartifactId=falabrasilnlp -Dversion=1.0.0 -Dpackaging=jar
```

Em seguida, basta adicionar a dependência normalmente no `pom.xml`, conforme visto na seção anterior.

### Build do projeto

Para finalizar, antes de realizar o primeiro *build* do seu projeto, realize um `mvn clean`.