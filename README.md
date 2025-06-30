# TCC-MindTrack: Sistema para acompanhamento do bem estar no ambiente de trabalho

Este repositório corresponde à aplicação Back-End do sistema desenvolvido como Trabalho de Conclusão de Curso no curso de Análise e Desenvolvimento de Sistemas da UFPR. O sistema tem como objetivo auxiliar departamentos de Recursos Humanos no monitoramento do bem-estar emocional dos colaboradores.

## Tecnologias utilizadas:
- JDK: 22.0.1       
- PostgreSQL 16
- Spring Boot: 3.3.5

### Primeiro, é necessário clonar o projeto
1. No GitHub.com, navegue até https://github.com/Mind-Track/MindTrack-Back.
2. Nas opções acima dos arquivos do repositório selecione Code.
3. Copie o código SSH ou HTTPS.
4. Abra o terminal e execute git clone git@github.com:Mind-Track/MindTrack-Back.git (ou HTTPS https://github.com/Mind-Track/MindTrack-Back.git).

### Para executar:
Crie o banco de dados mindtrackDB no postgres
Caso necessario altere o arquivo application.properties com as credencias do seu servidor postgres

  *spring.datasource.username= postgres
  *spring.datasource.password=

Basta executar o arquivo MindTrackBackApplication.java

### 🛠️ Criação rápida de Administrador

Após iniciar o backend, execute o comando abaixo para criar um usuário administrador padrão:
curl -Uri "http://localhost:8080/cadastro/novo" -Method POST -Headers @{ "Content-Type" = "application/json" } -Body '{"cpf":"999.999.999-99","nome":"Administrador","email":"admin@gmail.com","setor":"Comercial","cargo":"Analista","perfil":"Administrador"}'

A senha padrão para login pós curl é "abc123".

Desenvolvido por:
- LEONARDO EUGÊNIO PANCERI DE ARAUJO
- NICOLAS PORTELA BARBOSA
- RICARDO DE PAULA GOMES
