# Seu cantinho

## Objetivo
O objetivo deste projeto é desenvolver um _protótipo funcional_ que resolva os problemas da solução atual de _Seu Cantinho_, disponíveis em espec/especificacao. Foi desenvolvido como trabalho final da disciplina Design De Software, ofertada na UFPR em 2025/02.

## Estrutura de arquivos
Cada arquivo ou diretório relevante para o sistema está definido abaixo:
- documentacao.pdf: documentação criada com o objetivo de centralizar todas as decisões de design tomadas, assim como detalhar aspectos relevantes sobre o sistema criado.
- espec/especificacao.pdf: especificação do trabalho
- uml/: diagramas uml criados
- /src/backend: código-fonte do backend, dividido nas camadas descritas em documentacao.pdf
- /src/frontend: código-fonte para o frontend
- /src/docker-compose: arquivo principal para implantação via ```docker-compose up``` 

## Guia de execução
Para executar basta, a partir da pasta seu-cantinho no terminal, entrar no diretório src via ```cd src``` e executar ```docker-compose up```. 
Quando for impresso no terminal ``` backend_1 | Servidor Spark rodando na porta 8080 ```, a interface estará disponível em http://localhost.

Além disso, o backend estará disponível em http://localhost:8080/<endpoint de interesse> - acessar diretamente esses endpoints pode ser útil para testes. Além disso, todos os endpoints disponíveis estarão documentados com swagger em http://localhost:8080/docs. 

## Como navegar no protótipo
Com o projeto em execução, a interface será disponibilizada em http://localhost.
Nela, existe um botão no topo da página para cada uma das principais entidades - cliente, administrador, espaço, filial e reserva. Em cada uma dessas abas, é possível cadastrar e remover uma entidade.
Além disso, existem dependências entre elementos. São elas:
- Clientes e Filiais podem ser criados sem nenhuma outra entidade
- Administradores e Espaços só podem ser criados se Filial correspondente já tiver sido criada.
- Reservas só podem ser criadas se Cliente, Filial, Administrador e Espaço correspondente já forem criados.

Se essas dependências não forem atendidas, a criação de entidades retornará erro 500. A interface não lida com esse erro por se tratar de um protótipo funcional, que assume que todas as decisões do usuário são as esperadas. 