# Educa100

## Visão Geral

O Educa100 é uma iniciativa que visa criar uma API REST completa para a gestão de alunos, notas, cursos, turmas, matérias e docentes. O objetivo dessa API é fornecer uma solução eficiente para a administração educacional, integrando-se a soluções web de gestão em escolas e creches da rede pública.

### Tecnologias Utilizadas

- **Java** (IDE: IntelliJ)
- **PostgreSQL** (pgAdmin4)
- **Spring Boot**
- **Maven**
- **GitHub** (com metodologia GitFlow)
- **Trello** (com metodologia Kanban)
- **Protocolo HTTP** (usado Postman para testes)
- **JSON**

## Descrição de Execução

**Utilize um aplicativo de testes de APIs Web**: Recomendo o Postman para testar os métodos HTTP, conforme os controllers indicam.

*(Login do Administrador: Login: "adm", Senha: "12345")*

3. **Rotas disponíveis**:
    - `/login`
    - `/cadastro`-cria usuários
    - `/alunos`
        - `POST` - Cria um novo aluno
        - `GET` - Lista todos os alunos
        - `GET(/{id})` - Retorna informações de um aluno específico
        - `PUT(/{id})` - Atualiza informações de um aluno específico
        - `DELETE(/{id})` - Deleta um aluno específico
        - `GET(/{id}/pontuacao)` - Retorna a pontuação de um aluno específico
        - `GET(/{id_aluno}/notas)` - Retorna as notas de um aluno específico
    - `/cursos`
        - `POST` - Cria um novo curso
        - `GET` - Lista todos os cursos
        - `GET(/{id})` - Retorna informações de um curso específico
        - `PUT(/{id})` - Atualiza informações de um curso específico
        - `DELETE(/{id})` - Deleta um curso específico
        - `GET(/{id_curso}/materias)` - Retorna as matérias de um curso específico
    - `/docentes`
        - `POST` - Cria um novo docente
        - `GET` - Lista todos os docentes
        - `GET(/{id})` - Retorna informações de um docente específico
        - `PUT(/{id})` - Atualiza informações de um docente específico
        - `DELETE(/{id})` - Deleta um docente específico
    - `/materias`
        - `POST` - Cria uma nova matéria
        - `GET` - Lista todas as matérias
        - `GET(/{id})` - Retorna informações de uma matéria específica
        - `PUT(/{id})` - Atualiza informações de uma matéria específica
        - `DELETE(/{id})` - Deleta uma matéria específica
    - `/notas`
        - `POST` - Cria uma nova nota
        - `GET` - Lista todas as notas
        - `GET(/{id})` - Retorna informações de uma nota específica
        - `PUT(/{id})` - Atualiza informações de uma nota específica
        - `DELETE(/{id})` - Deleta uma nota específica
    - `/turma`
        - `POST` - Cria uma nova turma
        - `GET` - Lista todas as turmas
        - `GET(/{id})` - Retorna informações de uma turma específica
        - `PUT(/{id})` - Atualiza informações de uma turma específica
        - `DELETE(/{id})` - Deleta uma turma específica

 **Coleção Postman**: Use uma coleção Postman disponível para facilitar o teste das rotas.

## Melhorias Sugeridas

- **Interface Visual**: Desenvolver uma interface gráfica para tornar a interação com a API mais amigável e acessível.
- **Melhor lógica de segurança**: Mudar a lógica de segurança usada para uma lógica mais profissional de concisa. 
- **Correção de erros**: Identificar e corrigir possíveis bugs para melhorar a estabilidade e confiabilidade do sistema.
- **Mais funcionalidades para cada tipo de docente**: Definir permissões e funções mais específicas para cada tipo de docente para uma gestão mais eficiente.