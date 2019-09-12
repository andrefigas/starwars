# Projeto Star Wars

Esta aplicação se proõe a demonstrar um pouco sobre os personagens do filme Star Wars

## Instalação
Clone este projeto e importe no **Android Studio**
```bash
git clone git@github.com:andrefigas/starwars.git
```

## Arquitetura
MVP

## Pricipais Tecnologias Envolvidas
- Kotlin
- Dagger2
- Rx
- Retrofit
- Picasso
- Palette

## Funcionalidades

### Lista

Aqui são exibidos dados resumidos sobre cada personagem (nome, espécie, quantidade de carros). Para carregar estas informações é necessário fazer duas requisições por cada item da lista: uma para carregar as informações do personagem, outro para carregar as informações da espécie.

### Carregamento sob demanda

Por definição, em cada carregamento busca-se por 10 personagens.
As requisições são disparadas a medida que é feito scroll, precisamente quando faltam apenas 5 itens para chegar ao fim da lista.

### Detalhes

Nesta tela são exibidas informações adicionais sobre o personagem escolhido (gênero, cor da pele, lista de veículos) e o mural de fotos (obtidas pelas consulta ao google). Um detalhe relevante é que o background do mural tem a cor condizente com a cor de pele do personagem.
Para compor as informações são feitas as seguintes requisições:
- Uma requição para cada veículo
- Uma requisição para o planeta natal
- Uma requisição ao serviço de busca do google para carregar urls de imagens encontradas ao buscar pelo nome do personagem
- Uma requisição (feita pelo Picasso) para carregar cada imagem

Para compor a imagem de background:
- Uma requisição ao serviço de busca do google procurando por "color <cor de pele>"
- Uma requisição (feita pelo Picasso) para carregar o primeiro resultado
- Uma requisição ao serviço do Pallete para encontrar cor predominante na imagem. **Nota:** *O Pallete apresentou problemas ao reconhecer imagens completamente brancas, então a cor *white* (branca) foi pré-carregada via código*

## Agradecimentos

Agradeço à equipe responsável pela [API](https://swapi.co/api/)
