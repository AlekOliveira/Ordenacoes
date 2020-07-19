package trabalhopo2020;

import java.util.Random;

public class Lista {

    private int qtElem;
    private No Inicio;
    private No Fim;
    private int permutacoes;

    public Lista() {
        permutacoes = 0;
        this.qtElem = 0;
        Inicio = Fim = null;
    }

    public int getPermutacoes() {
        return permutacoes;
    }

    public No getLista() {
        return Inicio;
    }

    public No getInicio() {
        return Inicio;
    }

    public void setInicio(No inicio) {
        this.Inicio = inicio;
    }

    public No getFim() {
        return Fim;
    }

    public void setFim(No fim) {
        this.Fim = fim;
    }

    public void insereInicio(No NC) {
        if (Inicio == null) {
            Inicio = Fim = NC;
        } else {
            Inicio.setAnt(NC);
            NC.setProx(Inicio);
            Inicio = NC;
        }
        qtElem++;
    }

    public void insereFim(No NC) {
        if (Fim == null) {
            Fim = Inicio = NC;
        } else {
            Fim.setProx(NC);
            NC.setAnt(Fim);
            Fim = NC;
        }
        qtElem++;
    }

    public int len() {
        return qtElem;
    }

    public int distancia(No ini, No fim) {

        int i = 1;
        if (ini == fim) {
            return 0;
        }

        while (ini.getProx() != fim) {
            ini = ini.getProx();
            i++;
        }
        return i;

    }

    private int calculaMeio(No ini, No fim) {
        return (getIndex(ini) + getIndex(fim)) / 2;
    }

    public int getIndex(No chave) {
        int i = 0;
        No aux = Inicio;

        while (aux != null && chave != aux) {
            aux = aux.getProx();
            i++;
        }

        return i;
    }

    private No getNo(int pos) {
        No aux = Inicio;
        for (int i = 0; i < pos; i++) {
            aux = aux.getProx();
        }
        return aux;
    }

    public No getNo(int pos, Lista L) {
        No aux = L.getInicio();

        for (int i = 0; i < pos; i++) {
            aux = aux.getProx();
        }
        return aux;
    }

    public void exibe() {
        No aux = Inicio;

        while (aux != null) {
            System.out.print(aux.getInfo() + " ");
            aux = aux.getProx();
        }
    }

    private void permuta(No um, No dois) {
        int temp = um.getInfo();
        um.setInfo(dois.getInfo());
        dois.setInfo(temp);
    }

    private No getNo(No inicio, int desloc) {
        No aux = inicio;

        while (aux != null && desloc != 0) {
            if (desloc > 0) {
                aux = aux.getProx();
                desloc--;
            } else {
                aux = aux.getAnt();
                desloc++;
            }

        }
        return aux;
    }

    public void listaRandom(int k) {
        Random r = new Random();
        for (int i = 0; i < k; i++) {
            insereFim(new No(null, null, r.nextInt(100)));
        }
    }

    public int getMaior() {
        int i = 0, m = 0;
        while (i < qtElem) {
            if (getNo(i).getInfo() > m) {
                m = getNo(i).getInfo();
            }
            i++;
        }
        return m;
    }

    public int vetToInt(int[] vet, int cont) {
        int result = 0;
        int Grr;

        for (int i = 0; i < cont; i++) {
            Grr = (int) Math.pow(10, i);
            result = result + (vet[i] * (Grr));
        }
        return result;
    }

///////////////////////////Ordenações e buscas/////////////////////////////////   
    public void InsertionSort() {
        //Permuta um elemento menor do par até sua posicao ideal
        int aux;
        No i, pos;
        i = Inicio.getProx();
        while (i != null) {
            aux = i.getInfo();
            pos = i;
            while (pos != Inicio && aux < pos.getAnt().getInfo()) {
                pos.setInfo(pos.getAnt().getInfo());
                pos = pos.getAnt();
            }
            pos.setInfo(aux);
            i = i.getProx();
        }
    }

    public void SelecaoDireta() {
        //Lado Nao ordenado e ordenado
        No auxi = Inicio;
        No auxj;
        No posmenor;

        while (auxi.getProx() != null) {
            posmenor = auxi;
            auxj = auxi.getProx();

            while (auxj != null) {
                if (auxj.getInfo() < posmenor.getInfo()) {
                    posmenor = auxj;
                }
                auxj = auxj.getProx();
            }

            permuta(auxi, posmenor);
            auxi = auxi.getProx();
        }
    }

    public void bubbleSort() {
        No auxi = Inicio;
        No auxf = Fim;
        boolean flag = false;

        while (!flag) {
            flag = true;
            while (auxi != auxf) {
                if (auxi.getInfo() > auxi.getProx().getInfo()) {
                    permuta(auxi, auxi.getProx());
                    flag = false;
                }
                auxi = auxi.getProx();
            }
            auxf = auxf.getAnt();
            auxi = Inicio;
        }
    }

    public void shakeSort() {
        No fim = Fim, ini = Inicio, i;

        while (ini.getProx() != fim) {
            for (i = ini; i != null && i != fim; i = i.getProx()) {
                if (i.getInfo() > i.getProx().getInfo()) {
                    permuta(i.getProx(), i);
                }
            }

            if (ini.getProx() != fim.getAnt()) {
                fim = fim.getAnt();
            }

            for (i = fim; i != ini; i = i.getAnt()) {
                if (i.getInfo() < i.getAnt().getInfo()) {
                    permuta(i.getAnt(), i);
                }
            }
            ini = ini.getProx();
        }
    }

    public void heapSort() {
        int FE, FD, pai;
        int TL2 = qtElem;
        No nFE;
        No nFD;
        No npai;
        No maiorFilho;
        No auxFim = Fim;

        while (TL2 > 1) {
            for (pai = TL2 / 2 - 1; pai >= 0; pai--) {
                FE = 2 * pai + 1;
                FD = 2 * pai + 2;

                nFE = getNo(FE);
                nFD = getNo(FD);
                npai = getNo(pai);

                if (FD < TL2 && nFD.getInfo() > nFE.getInfo()) {
                    maiorFilho = nFD;
                } else {
                    maiorFilho = nFE;
                }

                if (npai.getInfo() < maiorFilho.getInfo()) {
                    permuta(npai, maiorFilho);
                }
            }

            permuta(Inicio, auxFim);
            auxFim = auxFim.getAnt();

            TL2--;
        }
    }

    public void quickSemPivo() {
        quickSP(Inicio, Fim);
    }

    private void quickSP(No ini, No fim) {
        No i = ini, j = fim, aux;
        boolean flag = true;
        int count = 0;

        while (i != j) {
            if (flag) {
                while (i != j && i.getInfo() <= j.getInfo()) {
                    i = i.getProx();
                }
            } else {
                while (i != j && i.getInfo() <= j.getInfo()) {
                    j = j.getAnt();
                }
            }
            if (i != j) {
                permuta(i, j);
            }
        }
        if (ini != i) {
            quickSP(ini, i.getAnt());
        }
        if (j != fim) {
            quickSP(j.getProx(), fim);
        }
    }
    

    public void mergeSort() {
        int vet1[] = new int[qtElem / 2];
        int vet2[] = new int[qtElem / 2];
        int seq = 1;

        while (seq < qtElem) {
            particao(vet1, vet2);
            fusao(vet1, vet2, seq);
            seq *= 2;
        }
    }

    private void particao(int[] vet1, int[] vet2) {

        for (int i = 0; i < qtElem / 2; i++) {
            vet1[i] = getNo(i).getInfo();
            vet2[i] = getNo(Inicio, i + (qtElem / 2)).getInfo();
        }
    }

    private void fusao(int[] vet1, int[] vet2, int seq) {
        int i, j, k;
        i = j = k = 0;
        int auxseq = seq;

        while (k < qtElem) {
            while (i < seq && j < seq) {
                if (vet1[i] < vet2[j]) {
                    getNo(k++).setInfo(vet1[i++]);
                } else {
                    getNo(k++).setInfo(vet2[j++]);
                }
            }
            while (i < seq) {
                getNo(k++).setInfo(vet1[i++]);
            }
            while (j < seq) {
                getNo(k++).setInfo(vet2[j++]);
            }

            seq += auxseq;
        }
    }

    public void MergeSort2() {
        Lista L = new Lista();

        for (int i = 0; i < qtElem; i++) {
            L.insereFim(new No(getNo(i).getInfo()));
        }
        particao2(L, 0, qtElem - 1);
    }

    private void particao2(Lista L, int esq, int dir) {
        int meio;

        if (esq < dir) {
            meio = (esq + dir) / 2;
            particao2(L, esq, meio);
            particao2(L, meio + 1, dir);
            fusao2(L, esq, meio, meio + 1, dir);
        }
    }

    private void fusao2(Lista L, int ini1, int fim1, int ini2, int fim2) {
        int i = ini1, j = ini2, k = 0;

        while (i <= fim1 && j <= fim2) {
            if (getNo(i).getInfo() < getNo(j).getInfo()) {
                getNo(k++, L).setInfo(getNo(i++).getInfo());
            } else {
                getNo(k++, L).setInfo(getNo(j++).getInfo());
            }
        }
        while (i <= fim1) {
            getNo(k++, L).setInfo(getNo(i++).getInfo());
        }
        while (j <= fim2) {
            getNo(k++, L).setInfo(getNo(j++).getInfo());
        }
        for (i = 0; i < k; i++) {
            getNo(ini1 + i).setInfo(getNo(i, L).getInfo());
        }
    }

    public void countingSort() {
        Lista L1 = new Lista();
        Lista LCop = new Lista();

        for (int i = 0; i < qtElem; i++) {
            LCop.insereFim(new No(getNo(i).getInfo()));
        }

        int i, j, k, w;
        i = j = k = w = 0;

        while (i < getMaior()) {
            L1.insereFim(new No(0));
            i++;
        }

        while (j < qtElem) {
            getNo(getNo(j).getInfo() - 1, L1).setInfo((getNo(getNo(j).getInfo() - 1, L1).getInfo()) + 1);
            j++;
        }

        while (k < getMaior() - 1) {
            getNo(k + 1, L1).setInfo(getNo(k, L1).getInfo() + getNo(k + 1, L1).getInfo());
            k++;
        }

        j = 0;
        while (j < getMaior())//todos -1 pois o "vetor" é indexado a partir de 0
        {
            getNo(j, L1).setInfo(getNo(j, L1).getInfo() - 1);
            j++;
        }
        while (w < qtElem) {
            getNo(getNo(getNo(w, LCop).getInfo() - 1, L1).getInfo()).setInfo(getNo(w, LCop).getInfo());
            getNo(getNo(w, LCop).getInfo() - 1, L1).setInfo(getNo(getNo(w, LCop).getInfo() - 1, L1).getInfo() - 1);
            w++;
        }
    }
    
    public void bucketSort() {
        int[] vet = new int[qtElem];
        int maior_val = 0;
        No aux = Inicio;
        int tam = qtElem;

        for (int i = 0; i < tam; i++, aux = aux.getProx()) {
            vet[i] = aux.getInfo();
            if (aux.getInfo() > maior_val) {
                maior_val = aux.getInfo();
            }
        }

        int[][] buckets = new int[4][tam];
        int[] tls = new int[4];

        for (int i = 0; i < 4; i++) {
            tls[i] = 0;
        }

        for (int i = 0; i < tam; i++) {
            if (vet[i] < maior_val * 0.25) {
                buckets[0][tls[0]++] = vet[i];
            } else if (vet[i] < maior_val * 0.5) {
                buckets[1][tls[1]++] = vet[i];
            } else if (vet[i] < maior_val * 0.75) {
                buckets[2][tls[2]++] = vet[i];
            } else {
                buckets[3][tls[3]++] = vet[i];
            }
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tls[i] - 1; j++) {
                if (buckets[i][j] > buckets[i][j + 1]) {
                    int a = buckets[i][j];
                    buckets[i][j] = buckets[i][j + 1];
                    buckets[i][j + 1] = a;
                }
            }
        }

        int tl = 0;

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < tls[i]; j++) {
                vet[tl++] = buckets[i][j];
            }
        }

        aux = Inicio;

        for (int i = 0; i < tam; i++, aux = aux.getProx()) {
            aux.setInfo(vet[i]);
        }
    }

    public void radixSort() {

        Lista Laux = new Lista();
        int[][] VV = new int[qtElem][100];
        for (int i = 0; i < qtElem; i++) {
            Laux.insereFim(new No(getNo(i).getInfo()));
        }

        int[] vet = new int[100];
        int f = 0;
        int a;
        int cont = 0;
        int maxCont = 0;
        for (int w = 0; w < qtElem; w++) {
            //insere os vetores no Vetor
            a = getNo(w, Laux).getInfo();
            cont = 0;
            f = 0;
            while (a > 0) {
                //coloca os int no vetor
                vet[f] = a % 10;
                a = a / 10;
                f++;
                cont++;
            }
            for (int j = 0; j < cont; j++) {
                VV[w][j] = vet[j];
            }
            if (cont > maxCont) {
                maxCont = cont;
            }
        }
        int t = 0;
        for (int h = 0; h < maxCont; h++) {
            t = 0;

            for (int p = 0; p <= 9; p++) {
                for (int s = 0; s < qtElem; s++) {
                    if (VV[s][h] == p) {
                        getNo(t).setInfo(vetToInt(VV[s], maxCont));
                        t++;
                    }
                }
            }
            Laux = new Lista();
            VV = new int[qtElem][100];
            vet = new int[100];

            for (int i = 0; i < qtElem; i++) {
                Laux.insereFim(new No(getNo(i).getInfo()));
            }
            f = 0;
            cont = 0;
            for (int w = 0; w < qtElem; w++) {
                //insere os vetores no Vetor
                a = getNo(w, Laux).getInfo();
                cont = 0;
                f = 0;
                while (a > 0) {
                    //coloca os int no vetor
                    vet[f] = a % 10;
                    a = a / 10;
                    f++;
                    cont++;
                }
                for (int j = 0; j < cont; j++) {
                    VV[w][j] = vet[j];
                }
            }
        }
    }

    public void combSort() {
        int gap = qtElem, aux;
        while (gap >= 1) {
            for (int i = 0; i < qtElem; i++) {
                if (i + gap < qtElem) {
                    if (getNo(i).getInfo() > getNo(i + gap).getInfo()) {
                        aux = getNo(i).getInfo();
                        getNo(i).setInfo(getNo(i + gap).getInfo());
                        getNo(i + gap).setInfo(aux);
                    }
                }
            }
            gap = (int) (gap / 1.3);
        }
    }

    public void gnomeSort() {
        int aux;
        for (int i = 0; i < qtElem - 1; i++) {
            if (getNo(i).getInfo() > getNo(i).getProx().getInfo()) {
                aux = getNo(i).getInfo();
                getNo(i).setInfo(getNo(i).getProx().getInfo());
                getNo(i).getProx().setInfo(aux);
                i = -1;
            }
        }
    }

    private void timInsertionSort(int esq, int dir) {
        int temp, j;
        for (int i = esq + 1; i <= dir; i++) {
            temp = getNo(i).getInfo();
            j = i - 1;
            while (getNo(j).getInfo() > temp && j >= esq) {

                getNo(j + 1).setInfo(getNo(j).getInfo());
                j--;
            }
            getNo(j + 1).setInfo(temp);
        }
    }

    private void timMerge(int e, int m, int d) {

        int tam1 = m - e + 1, len2 = d - m;
        int[] esq = new int[tam1];
        int[] dir = new int[len2];
        for (int x = 0; x < tam1; x++) {
            esq[x] = getNo(e + x).getInfo();
        }

        for (int x = 0; x < len2; x++) {
            dir[x] = getNo(m + 1 + x).getInfo();
        }

        int i = 0;
        int j = 0;
        int k = e;

        while (i < tam1 && j < len2) {
            if (esq[i] <= dir[j]) {
                getNo(k).setInfo(esq[i]);
                i++;
            } else {
                getNo(k).setInfo(dir[j]);
                j++;
            }
            k++;
        }

        while (i < tam1) {
            getNo(k).setInfo(esq[i]);
            k++;
            i++;
        }

        while (j < len2) {
            getNo(k).setInfo(dir[j]);
            k++;
            j++;
        }
    }

    public void timSort() {
        int n = qtElem, RUN = 32;
        int menor;

        for (int i = 0; i < n; i = i + RUN) {
            if (i + 31 < n - 1) {
                menor = i + 31;
            } else {
                menor = n - 1;
            }
            timInsertionSort(i, menor);
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int esq = 0; esq < n; esq += 2 * size) {

                int meio = esq + size - 1;
                int dir = Math.min((esq + 2 * size - 1), (n - 1));
                timMerge(esq, meio, dir);
            }
        }
    }

    public void shellSort() {
        int TL = qtElem;
        No aux;
        int temp;
        for (int h = TL / 2; h > 0; h /= 2) {
            for (int i = h; i < TL; i++) {
                temp = getNo(i).getInfo();
                int j;
                for (j = i; j >= h && getNo(j - h).getInfo() > temp; j -= h) {
                    getNo(j).setInfo(getNo(j - h).getInfo());
                }
                aux = getNo(j);
                aux.setInfo(temp);
            }
        }
    }

    public void insercaoBinaria() {
        int aux, pos;
        for (int i = 1; i < qtElem; i++) {
            aux = getNo(i).getInfo();
            pos = buscaBinaria(aux, i);

            for (int j = i; j > pos; j--) {
                getNo(j).setInfo(getNo(j - 1).getInfo());
            }

            getNo(pos).setInfo(aux);
        }
    }

    public int buscaBinaria(int chave, int tl) {
        int inicio = 0, fim = tl - 1, meio = tl / 2;

        No aux = getNo(meio);
        while (inicio < fim && aux.getInfo() != chave) {
            if (aux.getInfo() == chave) {
                return meio;
            } else if (chave > aux.getInfo()) {
                inicio = meio + 1;
            } else {
                fim = meio - 1;
            }

            meio = (inicio + fim) / 2;
            aux = getNo(meio);
        }
        if (chave > aux.getInfo()) {
            //qnd n existe
            return meio + 1;
        }
        return meio;
    }

    public void quickComPivo() {
        QuickCP(Inicio, Fim);
    }

    private void QuickCP(No inicio, No finaal) {
        No ini = inicio, fim = finaal, pivo = getNo((getIndex(inicio) + getIndex(finaal)) / 2);
        int aux;

        while (getIndex(ini) <= getIndex(fim)) {
            while (ini.getInfo() < pivo.getInfo()) {
                ini = ini.getProx();
            }

            while (fim.getInfo() > pivo.getInfo()) {
                fim = fim.getAnt();
            }

            if (getIndex(ini) <= getIndex(fim)) {
                aux = ini.getInfo();
                ini.setInfo(fim.getInfo());
                fim.setInfo(aux);
                ini = ini.getProx();
                fim = fim.getAnt();

            }

        }

        if (getIndex(inicio) < getIndex(fim)) {
            QuickCP(inicio, ini.getAnt());
        }
        if (getIndex(ini) < getIndex(finaal)) {
            QuickCP(fim.getProx(), finaal);
        }
    }

}
