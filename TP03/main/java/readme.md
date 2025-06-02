# PUCFlix - Trabalho Prático 2 (AEDS III)

## 🎯 Objetivo

Implementar um sistema completo de gerenciamento de séries, episódios e atores, utilizando Java, arquivos binários, CRUD genérico, Tabela Hash Extensível e Árvore B+ para garantir eficiência e consistência no acesso e relacionamento dos dados, incluindo agora relacionamento N\:N entre Séries e Atores.

---

## 💻 Tecnologias Utilizadas

* **Java 17** ✅
* **Maven** (build e dependências) ✅
* CRUD Genérico com `RandomAccessFile` ✅
* **Tabela Hash Extensível** para índice direto ✅
* **Árvore B+** para índice indireto e relacionamento N\:N ✅
* Estrutura seguindo o padrão **MVC** ✅

---

## ⚙️ Como Compilar e Executar

1. Clone o repositório:

```bash
git clone https://github.com/seuusuario/pucflix.git
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
├── aeds3/               # CRUD genérico, Hash, B+, ParIntInt
├── controle/            # Lógica de controle da aplicação (Series, Episodios, Atores)
├── modelo/              # Acesso aos arquivos (ArquivoSeries, ArquivoEpisodios, ArquivoAtor)
├── visao/               # Entrada/saída (MenuSeries, MenuEpisodios, MenuAtor)
├── entidades/           # Modelos de dados (Serie, Episodio, Ator)
└── PrincipalFlix.java   # Classe principal
```

---

## 📌 Funcionalidades Implementadas

### 📺 CRUD de Séries

* Incluir, buscar, alterar e excluir séries
* Vincular atores (por ID) durante cadastro ou edição
* Exibe os atores vinculados a uma série
* Exclui automaticamente os vínculos ao remover uma série

### 🎞️ CRUD de Episódios

* Inclusão por série (ID)
* Visualização agrupada por temporada ou completa
* Impede exclusão de série com episódios vinculados

### 🎭 CRUD de Atores

* Incluir, buscar, alterar e excluir
* Visualização de séries em que o ator participa
* Impede exclusão se estiver vinculado
* Busca por nome parcial e case-insensitive

### 🔗 Relacionamento N\:N com Árvore B+

* `serie-atores.db` (idSerie, idAtor)
* `ator-series.db` (idAtor, idSerie)
* Mantido consistente e sincronizado

---

## ✅ Checklist da Atividade

| Requisito                                                 | Status |
| --------------------------------------------------------- | ------ |
| CRUD de Séries com validações e visualização de atores    | ✅ Sim  |
| CRUD de Episódios por série com agrupamento por temporada | ✅ Sim  |
| CRUD de Atores com visualização de séries vinculadas      | ✅ Sim  |
| Relacionamento N\:N entre Atores e Séries com Árvores B+  | ✅ Sim  |
| Exclusão de série remove os vínculos com atores           | ✅ Sim  |
| Exclusão de ator é impedida se estiver vinculado          | ✅ Sim  |
| Cadastro de ator exige que ele já exista                  | ✅ Sim  |
| Busca parcial e flexível de ator por nome                 | ✅ Sim  |
| Estrutura MVC clara e separação de responsabilidades      | ✅ Sim  |
| Trabalho original, funcional e completo                   | ✅ Sim  |

---

## ✍️ Relato da Experiência

Este trabalho foi uma extensão direta do TP1 e introduziu a complexidade do relacionamento N\:N usando estruturas de árvore B+. Implementamos toda a estrutura de CRUD com validações e tratamento de dados persistidos com arquivos binários.

A maior dificuldade foi manter a consistência entre os pares (idSerie, idAtor) e (idAtor, idSerie), garantindo que toda a operação de inclusão ou remoção de vínculo estivesse refletida nas duas estruturas.

A busca flexível por nome e a prevenção de exclusão com dependências foram implementadas com sucesso, elevando o sistema a um nível de robustez e integridade ideal para aplicações reais.

---

## 👥 Participantes

* João Paolinelli e Silva (Matricula: 701540)
* Daniel Lucas Soares Madureira (Matrícula: 796363)
* Ana Luíza de Morais Lemos (Matrícula: 848420)

---

## 🔗 Repositório GitHub

https://github.com/JoaoPaolinelli/TP01-PUCFLIX/tree/main/TP2/src/main/java
