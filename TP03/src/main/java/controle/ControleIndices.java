package controle;

import java.io.File;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import aeds3.ElementoLista;
import aeds3.ListaInvertida;
import entidades.Ator;
import entidades.Episodio;
import entidades.Serie;

public class ControleIndices {

    private ListaInvertida indiceSeries;
    private ListaInvertida indiceEpisodios;
    private ListaInvertida indiceAtores;

    private static final Set<String> STOPWORDS = new HashSet<>();

    static {
        // Lista de stopwords em português
        String[] palavras = {"a", "o", "as", "os", "um", "uma", "uns", "umas", "de", "do", "da", "dos", "das", "em", "no", "na", "nos", "nas", "por", "para", "com", "sem", "sob", "sobre", "se", "seu", "sua", "seus", "suas", "ser", "tem", "pelo", "pela", "pelos", "pelas", "e", "ou", "mas", "que", "se", "como", "quando", "enquanto", "porque", "pois", "onde", "quem", "qual", "quais"};
        STOPWORDS.addAll(Arrays.asList(palavras));
    }

    public ControleIndices() throws Exception {

        File dir = new File("dados/indices");
        if (!dir.exists()) {
            dir.mkdirs();
        }

        final int TAMANHO_BLOCO = 8;
        indiceSeries = new ListaInvertida(TAMANHO_BLOCO, "dados/indices/series.termos.db", "dados/indices/series.blocos.db");
        indiceEpisodios = new ListaInvertida(TAMANHO_BLOCO, "dados/indices/episodios.termos.db", "dados/indices/episodios.blocos.db");
        indiceAtores = new ListaInvertida(TAMANHO_BLOCO, "dados/indices/atores.termos.db", "dados/indices/atores.blocos.db");
    }

    // Ferramenta para processar texto: minúsculas, sem acentos, sem stopwords
    private List<String> processarTexto(String texto) {
        if (texto == null || texto.isEmpty()) {
            return new ArrayList<>();
        }
        // Normaliza para remover acentos
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("[^\\p{ASCII}]", "")
                .toLowerCase();

        // Separa em palavras, removendo pontuações
        String[] palavras = textoNormalizado.split("[\\p{Punct}\\s]+");

        List<String> termosFiltrados = new ArrayList<>();
        for (String palavra : palavras) {
            if (!palavra.trim().isEmpty() && !STOPWORDS.contains(palavra.trim())) {
                termosFiltrados.add(palavra.trim());
            }
        }
        return termosFiltrados;
    }

    // --- MÉTODOS DE INDEXAÇÃO ---
    private void indexar(int id, String texto, ListaInvertida indice) throws Exception {
        List<String> termos = processarTexto(texto);
        if (termos.isEmpty()) {
            return;
        }

        // Calcula o TF (Term Frequency)
        Map<String, Float> tfMap = new HashMap<>();
        for (String termo : termos) {
            tfMap.put(termo, tfMap.getOrDefault(termo, 0f) + 1);
        }
        for (Map.Entry<String, Float> entry : tfMap.entrySet()) {
            entry.setValue(entry.getValue() / termos.size());
            indice.create(entry.getKey(), new ElementoLista(id, entry.getValue()));
        }
        indice.incrementaEntidades();
    }

    public void indexarSerie(Serie s) throws Exception {
        indexar(s.getID(), s.getNome(), indiceSeries);
    }

    public void indexarEpisodio(Episodio e) throws Exception {
        indexar(e.getID(), e.getNome(), indiceEpisodios);
    }

    public void indexarAtor(Ator a) throws Exception {
        indexar(a.getID(), a.getNome(), indiceAtores);
    }

    // --- MÉTODOS DE DESINDEXAÇÃO ---
    private void desindexar(int id, String texto, ListaInvertida indice) throws Exception {
        List<String> termos = processarTexto(texto);
        Set<String> termosUnicos = new HashSet<>(termos); // Evita chamadas repetidas
        for (String termo : termosUnicos) {
            indice.delete(termo, id);
        }
        indice.decrementaEntidades();
    }

    public void desindexarSerie(Serie s) throws Exception {
        desindexar(s.getID(), s.getNome(), indiceSeries);
    }

    public void desindexarEpisodio(Episodio e) throws Exception {
        desindexar(e.getID(), e.getNome(), indiceEpisodios);
    }

    public void desindexarAtor(Ator a) throws Exception {
        desindexar(a.getID(), a.getNome(), indiceAtores);
    }

    // --- MÉTODOS DE BUSCA COM TF-IDF ---
    private List<ResultadoBusca> buscar(String consulta, ListaInvertida indice) throws Exception {
        List<String> termosBusca = processarTexto(consulta);
        if (termosBusca.isEmpty()) {
            return new ArrayList<>();
        }

        Map<Integer, Float> scoresFinais = new HashMap<>();
        int N = indice.numeroEntidades(); // Total de documentos no índice
        if (N == 0) {
            return new ArrayList<>();
        }

        for (String termo : termosBusca) {
            ElementoLista[] lista = indice.read(termo);
            if (lista.length > 0) {
                int n = lista.length; // Número de docs que contêm o termo
                double idf = Math.log((double) N / n) + 1.0;

                for (ElementoLista el : lista) {
                    float tf = el.getFrequencia();
                    float score = (float) (tf * idf);
                    scoresFinais.merge(el.getId(), score, Float::sum);
                }
            }
        }

        List<ResultadoBusca> resultados = new ArrayList<>();
        for (Map.Entry<Integer, Float> entry : scoresFinais.entrySet()) {
            resultados.add(new ResultadoBusca(entry.getKey(), entry.getValue()));
        }

        Collections.sort(resultados); // Ordena por score (decrescente)
        return resultados;
    }

    public List<ResultadoBusca> buscarSerie(String consulta) throws Exception {
        return buscar(consulta, indiceSeries);
    }

    public List<ResultadoBusca> buscarEpisodio(String consulta) throws Exception {
        return buscar(consulta, indiceEpisodios);
    }

    public List<ResultadoBusca> buscarAtor(String consulta) throws Exception {
        return buscar(consulta, indiceAtores);
    }
}
