Na implementação foram criadas as classes da maneira especificada na orientação, a classe componente sendo abstrata para abrangir possiveis novos componentes
e seu atributo caverna sendo estatico para conectá-los ao jogo em questão. Na Salas, cada uma de suas intâncias possuem atributo Componentes que são definidos
como um vetor ordenado na ordem especificada do jogo, qualquer possivel alteração nesta ordem é facilmente realizada. O App realiza uma instancia de controle e 
procura arquivo de movimento, escolhendo o teclado como uma exception no caso de não existir este. Os componentes briza e fedor interagem com o heroi de maneira
simples, ao entrar em uma sala com estes, ele tem uma reação.
A classe Montador também tem ituito de imprimir a matriz de caracteres baseadas nas condicões especificadas, qualquer alteração do mapa ou do tipo de representação
pode ser tambem modificada de maneira mais simples.
