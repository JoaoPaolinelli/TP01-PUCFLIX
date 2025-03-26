# PUCFlix - Trabalho Prático 1 (AEDS III)

## 🎯 Objetivo
Implementar um sistema de gerenciamento de séries de streaming com seus respectivos episódios, utilizando um CRUD genérico, Tabela Hash Extensível e Árvore B+ para gerenciamento de dados e índices.

---

## 💻 Tecnologias Utilizadas

- **Java 17** ✅
- **Maven** (gerenciador de dependências e build) ✅
- CRUD Genérico com manipulação de arquivos binários ✅
- **Tabela Hash Extensível** para índice direto ✅
- **Árvore B+** para índice indireto com relacionamento 1:N ✅
- Estrutura de projeto seguindo o padrão **MVC** ✅

---

## ⚙️ Como compilar e executar

1. Clone o repositório:
```bash
git clone https://github.com/JoaoPaolinelli/TP01-PUCFLIX.git
cd pucflix
```

2. Compile o projeto com Maven:
```bash
mvn clean compile
```

3. Execute a aplicação:
```bash
mvn exec:java
```

---

## 📂 Estrutura de Diretórios (resumo)

```
src/main/java/
├── aeds3/               # CRUD genérico, EntidadeArquivo, Hash, B+
├── controle/            # Lógica de controle da aplicação (ControleSeries, ControleEpisodios)
├── modelo/              # Acesso aos arquivos físicos (ArquivoSeries, ArquivoEpisodios)
├── visao/               # Entrada/saída do usuário (MenuSeries, MenuEpisodios)
├── entidades/           # Classes de dados (Serie, Episodio)
└── PrincipalFlix.java   # Classe principal com o menu
```

---

## 📌 Funcionalidades Implementadas

### 📺 Classe `Serie`
- Atributos: id, nome, ano de lançamento, sinopse, plataforma de streaming
- Serialização para arquivo com `toByteArray()` / `fromByteArray()`

### 🎞️ Classe `Episodio`
- Atributos: id, idSerie, nome, temporada, data de lançamento, duração, sinopse
- Relacionamento com série via `idSerie`

### 📁 `ArquivoSeries` / `ArquivoEpisodios`
- Extendem o CRUD genérico baseado em arquivos binários
- Utilizam Tabela Hash Extensível para índice direto (ID → endereço)
- `ArquivoEpisodios` usa Árvore B+ com chaves do tipo `(idSerie, idEpisodio)`
- Possui método `buscarEpisodiosPorSerie(idSerie)` para recuperar episódios via índice B+

### 📊 `ParIdId` (chave para Árvore B+)
- Representa pares `(idSerie, idEpisodio)`
- Implementa a interface `RegistroArvoreBMais`

### 🧠 Controle e Visão (`MVC`)

#### `ControleSeries`:
- Menu de opções para incluir, buscar, alterar e excluir séries
- Exibe episódios por temporada
- Impede a exclusão de uma série se houver episódios associados (checagem por árvore B+)

#### `ControleEpisodios`:
- Permite o gerenciamento de episódios **por série**
- Garante que um episódio só é inserido se a série existir

#### `MenuSeries` / `MenuEpisodios`
- Realizam a entrada de dados e exibem os dados formatados ao usuário

### 🧩 `PrincipalFlix`
- Classe principal que exibe o menu inicial
- Encaminha para os controles de séries e episódios

---

## ✅ Checklist da Atividade

| Requisito                                                                                   | Status |
|---------------------------------------------------------------------------------------------|--------|
| As operações de inclusão, busca, alteração e exclusão de séries estão implementadas?        | ✅ Sim |
| As operações de inclusão, busca, alteração e exclusão de episódios por série estão ok?      | ✅ Sim |
| As operações usam CRUD genérico, Tabela Hash Extensível e Árvore B+?                        | ✅ Sim |
| O atributo `idSerie` foi implementado como chave estrangeira na entidade `Episodio`?        | ✅ Sim |
| Há uma Árvore B+ para registrar o relacionamento 1:N entre episódios e séries?              | ✅ Sim |
| A visualização dos episódios por temporada está implementada?                              | ✅ Sim |
| A exclusão da série verifica se há episódios vinculados?                                    | ✅ Sim |
| A inclusão de episódio se limita às séries existentes?                                      | ✅ Sim |
| O trabalho está funcionando corretamente?                                                    | ✅ Sim |
| O trabalho está completo?                                                                   | ✅ Sim |
| O trabalho é original?                                                                      | ✅ Sim |

---

## ✍️ Relato da Experiência

O desenvolvimento deste trabalho foi desafiador e enriquecedor. Tivemos que compreender a estrutura do CRUD genérico com RandomAccessFile, além de integrar dois tipos de índices: Tabela Hash Extensível (direto) e Árvore B+ (indireto) para implementar o relacionamento 1:N.

A maior dificuldade foi adaptar a Árvore B+ e garantir que os episódios estivessem sempre corretamente associados à sua respectiva série, tanto na inclusão quanto na exclusão.

No fim, conseguimos construir um sistema robusto, com controle completo das entidades, boa separação de responsabilidades (padrão MVC), e que atende todos os critérios exigidos pela disciplina.

---

## 👥 Participantes
- João Paolinelli e Silva (Matricula: 701540)
- Daniel Lucas Soares Madureira (Matrícula: 796363)
- Ana Luíza de Morais Lemos (Matrícula: 848420)

