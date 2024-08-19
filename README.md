# Santander e Claro Bootcamp 2024

Java RESTful API criado de um gerenciador de motel utilizando Login e JJWT

## Principais Tecnologias
 - **Java 17**: Utilizaremos a versão LTS mais recente do Java para tirar vantagem das últimas inovações que essa linguagem robusta e amplamente utilizada oferece;
 - **Spring Boot 3**: Trabalharemos com a mais nova versão do Spring Boot, que maximiza a produtividade do desenvolvedor por meio de sua poderosa premissa de autoconfiguração;
 - **Spring Data JPA**: Exploraremos como essa ferramenta pode simplificar nossa camada de acesso aos dados, facilitando a integração com bancos de dados SQL;
 - **OpenAPI (Swagger)**: Vamos criar uma documentação de API eficaz e fácil de entender usando a OpenAPI (Swagger), perfeitamente alinhada com a alta produtividade que o Spring Boot oferece;
 - **Spring Security**: adiciona login de sergurança a aplicação
 - **Token JJWT**: utiliza um token para validar pagina a pagina se o usuario esta logado


```mermaid
classDiagram
  class Usuario {
	
	Long id;	
	String nome;	
	String login;	
	String email;	
	String senha;	
	TipoSituacaoUsuario situacao;

  class Quarto {
        
    Long id;    
    Integer numero;    
    String descricao;    
    Double valorHora;    
    Double valorPernoite;    
    StatusQuarto status;    
    TipoQuarto tipoQuarto;    
    List<Hospedagem> hospedagemAtual = new ArrayList<>();

  class TipoQuarto {
    
    Long id;    
    String descricao;

  class Produto {
    
    Long id;   
    String descricao;    
    Double preco;    
    String unidade;    
    String status;    
    List<ItemPedido> itensPedidos = new ArrayList<>();

  class ItemPedido {
    
    Long id;
    Integer quantidade;    
    Double precoTotal;    
    Hospedagem hospedagem;   
    Produto produto;

  class Hospedagem {

    Long id;    
    String placa;
    String descricao;    
    Date checkin;
    Date checkout;
    Double valor_hospedagem;
    Double valor_total;
    String obs;    
    StatusHospedagem status;    
    Quarto quarto;
    List<ItemPedido> itensPedidos = new ArrayList<>();  

  Quarto "N" *-- "1" TipoQuarto 
  Quarto "1" *-- "N" Hospedagem

  TipoQuarto "1" *-- "N" Quarto

  Produto "1" *-- "N" ItemPedido

  ItemPedido "N" *-- "1" Produto
  ItemPedido "N" *-- "1" Hospedagem

  Hospedagem "N" *-- "1" Quarto
  Hospedagem "1" *-- "N" ItemPedido
  
```

