package trabalhopo2020;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Arquivo {

    private String nomearquivo;
    public void setNomearquivo(String nomearquivo) {
        this.nomearquivo = nomearquivo;
    }
    private RandomAccessFile arquivo;
    private int comp, mov, TL;

    public Arquivo(String nomearquivo) {
        try {
            this.nomearquivo=nomearquivo;
            this.arquivo = new RandomAccessFile(nomearquivo, "rw");
            this.TL = filesize();
        } catch (IOException ex) {
        }
    }
    
    public Arquivo() 
    { }

    
      public String getNomearquivo() {
        return nomearquivo;
    }
    
    public void copiaArquivo(Arquivo arquivoOrigem)
    {
        try
        {
            Registro reg = new Registro();
            int i = 0, tam = (int) arquivoOrigem.getFile().length() / Registro.length();
            try{
            arquivo.close();}catch (Exception eee){}
            this.arquivo = new RandomAccessFile(arquivoOrigem.getNomearquivo()+"temp.dat", "rw");
            //this.truncate(0);
            arquivo.setLength(0);
            arquivoOrigem.getFile().seek(0);
            while(i < tam)
            {
                reg.leDoArq(arquivoOrigem.getFile());
                reg.gravaNoArq(arquivo);
                i++;
            }
        }
        catch(IOException e)
        {

        }
    }

    public RandomAccessFile getFile() {
        return arquivo;
    }

    public void truncate(long pos)
    {
        try
        {
            arquivo.setLength(pos * Registro.length());
        }
        catch (IOException exc) { }
    }

    public boolean eof() {
        boolean flag = false;
        try {
            if (arquivo.getFilePointer() == arquivo.length()) {
                flag = true;
            }
        } catch (IOException ex) {
        }
        return flag;
    }

    public void seekArq(int pos) {
        try {
            arquivo.seek(pos * Registro.length());
        } catch (IOException ex) {
        }
    }

    public void initComp() {
        this.comp = 0;
    }

    public void initMov() {
        this.mov = 0;
    }

    public int getComp() {
        return this.comp;
    }

    public int getMov() {
        return this.mov;
    }

    public int filesize() {
        try {
            return (int) this.arquivo.length() / Registro.length();
        } catch (IOException ex) {
            return -1;
        }
    }

    public int getPosRegistro(Registro chave) {
        //retorna a posicao de 0 a N ou -1 caso a chave não exista.

        int i = 0;
        seekArq(0);
        while (!eof() && chave.getNumero() != getRegistro(i).getNumero()) {
            i++;
        }

        if (i < filesize()) {
            return i;
        }
        return -1;
    }

    public Registro getRegistro(int k) {
        Registro r = new Registro();
        seekArq(k);
        r.leDoArq(this.arquivo);

        return r;
    }

    public Registro getRegistro(Registro regIni, int desloc) {
        //Retorna o registro conforme o deslocamento dado a partir do nó

        Registro r = new Registro();
        int pos = getPosRegistro(regIni) + desloc;

        if (pos < filesize() && pos > -1) {
            return getRegistro(pos);
        }
        return null;
    }

    public void geraArquivoRandomico() {
        Registro reg = new Registro(1);
        Random r = new Random();

        for (int i = 0; i < Tabela.n; i++) {
            reg.setNumero(r.nextInt(100));
            reg.gravaNoArq(this.arquivo);
        }
    }
    
    public void geraArquivoReverso( ) {
        for (int i = Tabela.n; i > 0; i--) {
            Registro r = new Registro(i);
            r.gravaNoArq(arquivo);
        }
    }
    
    public void geraArquivoOrdenado( ) {

        for (int i = 0; i < Tabela.n; i++) {
            Registro r = new Registro(i);
            r.gravaNoArq(arquivo);
        }
    }

    public void exibe() {
        Registro r = new Registro();
        seekArq(0);
        while (!eof()) {
            r.leDoArq(this.arquivo);
            System.out.print(r.getNumero() + "|");
        }
    }

    
       
    

    

    //METODOS DE ORDENACAO    
    public void bubbleSort() {
        boolean flag = false;
        int TL = filesize() - 1;
        int pos = 0;

        Registro regFim = new Registro();
        Registro reg1 = new Registro();
        Registro reg2 = new Registro();

        seekArq(0);
        reg1.leDoArq(this.arquivo);
        reg2.leDoArq(this.arquivo);

        while (!flag) {
            flag = true;
            while (pos < TL) {
                comp++;
                if (reg1.getNumero() > reg2.getNumero()) {
                    seekArq(pos);
                    reg2.gravaNoArq(this.arquivo);
                    reg1.gravaNoArq(this.arquivo);
                    flag = false;
                    mov+=2;
                }
                pos++;
                seekArq(pos);
                reg1.leDoArq(this.arquivo);
                reg2.leDoArq(this.arquivo);
            }
            TL--;
            pos = 0;

            seekArq(0);
            reg1.leDoArq(this.arquivo);
            reg2.leDoArq(this.arquivo);
        }
    } //ok

    public void heapSort() {
        int TL = filesize();
        int posFim = TL - 1;
        int posFE, posFD, posPai, posMF;
        Registro fe, fd, pai, maiorFilho;
        Registro regTeste = new Registro();

        while (TL > 1) {
            for (posPai = TL / 2 - 1; posPai >= 0; posPai--) {
                posFE = 2 * posPai + 1;
                posFD = 2 * posPai + 2;

                fe = getRegistro(posFE);
                fd = getRegistro(posFD);
                pai = getRegistro(posPai);

                comp++;
                if (posFD < TL && fd.getNumero() > fe.getNumero()) {
                    maiorFilho = fd;
                    posMF = posFD;
                } else {
                    maiorFilho = fe;
                    posMF = posFE;
                }
                
                comp++;
                if (pai.getNumero() < maiorFilho.getNumero()) {
                    mov+=2;
                    seekArq(posPai);
                    maiorFilho.gravaNoArq(this.arquivo);

                    seekArq(posMF);
                    pai.gravaNoArq(this.arquivo);
                }
            }
            fe = getRegistro(0);
            fd = getRegistro(posFim);
            
            mov+=2;
            seekArq(0);
            fd.gravaNoArq(this.arquivo);
            seekArq(posFim);
            fe.gravaNoArq(this.arquivo);
            TL--;
            posFim--;
        }
    } //ok 

    public void selectionSort() //ok
    {
        int tl = filesize(), i = 0, menorpos = 0;
        Registro r1 = new Registro(), r2 = new Registro(), menorReg = new Registro();
        while (i < tl) {
            seekArq(i);
            menorReg.leDoArq(arquivo);
            r2.setNumero(menorReg.getNumero());
            for (int j = i + 1; j < tl; j++) {
                seekArq(j);
                r1.leDoArq(arquivo);
                
                comp++;
                if (r1.getNumero() < menorReg.getNumero()) {
                    menorpos = j;
                    menorReg.setNumero(r1.getNumero());
                }
            }
            mov+=2;
            seekArq(i);
            menorReg.gravaNoArq(arquivo);
            seekArq(menorpos);
            r2.gravaNoArq(arquivo);
            //exibe();
            i++;
        }
    }

    public void inserctionSort() //ok
    {
        int tl = 0, posCorreta;
        Registro r1 = new Registro();
        Registro r2 = new Registro();
        while (tl < filesize() - 1) {
            int j = tl + 1;
            seekArq(tl);
            r1.leDoArq(arquivo);
            seekArq(j);
            r2.leDoArq(arquivo);
            
            comp++;
            while (j > 0 && r2.getNumero() < r1.getNumero()) {
                comp++;
                mov+=2;
                seekArq(j - 1);
                r2.gravaNoArq(arquivo);
                seekArq(j);
                r1.gravaNoArq(arquivo);
                //System.out.println("\n");
                //exibe();
                j--;
                if (j > 0) {
                    seekArq(j - 1);
                    r1.leDoArq(arquivo);
                    seekArq(j);
                    r2.leDoArq(arquivo);
                }
            }
            tl++;
        }
    }

    /*public void quickSPcall() //ok
    {
        quickSP(0, filesize() - 1);
    }

    public void quickSP(int ini, int fim) //ok
    {
        Registro rI = new Registro(), rJ = new Registro();
        int i = ini, j = fim;
        while (i < j) {
            seekArq(i);
            rI.leDoArq(arquivo);
            seekArq(j);
            rJ.leDoArq(arquivo);
            while (i < j && rI.getNumero() <= rJ.getNumero()) {
                i++;
                seekArq(i);
                rI.leDoArq(arquivo);
            }
            if (i <= j) {
                seekArq(i);
                rJ.gravaNoArq(arquivo);
                seekArq(j);
                rI.gravaNoArq(arquivo);
                // System.out.println("\n");
                // exibe();
            }
            while (i < j && rI.getNumero() <= rJ.getNumero()) {
                j--;
                seekArq(j);
                rJ.leDoArq(arquivo);
            }
            if (i <= j) {
                seekArq(i);
                rJ.gravaNoArq(arquivo);
                seekArq(j);
                rI.gravaNoArq(arquivo);
                // System.out.println("to fazendo permuta"+i);
                // System.out.println("\n");
                // exibe();
            }
            if (ini < i - 1) {
                quickSP(ini, i - 1);
            }
            if (j + 1 < fim) {
                quickSP(j + 1, fim);
            }
        }
    }*/

    /*public void quickCPcall() {
        quickCP(0, filesize() - 1);
    }

    public void quickCP(int ini, int fim) //ok
    {
        Registro rI = new Registro(), rJ = new Registro();
        int i = ini, j = fim;
        Registro pivo = new Registro();
        seekArq((i + j) / 2);
        pivo.leDoArq(arquivo);
        while (i < j) {
            seekArq(i);
            rI.leDoArq(arquivo);
            seekArq(j);
            rJ.leDoArq(arquivo);
            while (rI.getNumero() <= pivo.getNumero()) {
                i++;
                seekArq(i);
                rI.leDoArq(arquivo);
            }
            while (rJ.getNumero() >= pivo.getNumero()) {
                j--;
                seekArq(j);
                rJ.leDoArq(arquivo);
            }
            if (i <= j) {
                seekArq(i);
                rJ.gravaNoArq(arquivo);
                seekArq(j);
                rI.gravaNoArq(arquivo);
                // System.out.println("to fazendo permuta"+i);
                // System.out.println("\n");
                // exibe();
            }            
        }
        if (ini < j) {
            quickSP(ini, j);
        }
        if (i < fim) {
            quickSP(i, fim);
        }
    }*/
    
     public void QuickSemPivo() {
        QuickSP(0, filesize()-1);
    }

    public void QuickSP(int ini, int fim) {
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        int i = ini;
        int j = fim;

        while (i < j) {
            seekArq(i);
            reg.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);

            comp++;
            while (i < j && reg.getNumero() <= reg2.getNumero()) {
                comp++;
                i++;
                seekArq(i);
                reg.leDoArq(arquivo);
            }
            comp++;
            if (i != j) {
                mov+=2;
                seekArq(i);
                reg2.gravaNoArq(arquivo);
                seekArq(j);
                reg.gravaNoArq(arquivo);
            }
            
            comp++;
            while (i < j && reg2.getNumero() >= reg.getNumero()) {
                comp++;
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }
            comp++;
            if (i != j) {
                mov+=2;
                seekArq(j);
                reg.gravaNoArq(arquivo);
                seekArq(i);
                reg2.gravaNoArq(arquivo);
            }

        }

        if (ini < i - 1) {
            QuickSP(ini, i - 1);
        }
        if (j + 1 < fim) {
            QuickSP(j + 1, fim);
        }
    }
    
        public void QuickComPivo() {        
        QuickCP(0, filesize()-1);        
    }
        
    public void QuickCP(int ini, int fim) {
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        Registro pivo = new Registro();
        int i = ini;
        int j = fim;
        int valor = (ini + fim) / 2;
        seekArq(valor);
        pivo.leDoArq(arquivo);
       
        while (i < j) {
            seekArq(i);
            reg.leDoArq(arquivo);
            
            comp++;
            while (reg.getNumero() < pivo.getNumero()) {
                comp++;
                i++;
                seekArq(i);
                reg.leDoArq(arquivo);
            }
            seekArq(j);
            reg2.leDoArq(arquivo);
            comp++;
            while (reg2.getNumero() > pivo.getNumero()) {
                comp++;
                j--;
                seekArq(j);
                reg2.leDoArq(arquivo);
            }
            mov+=2;
            seekArq(i);
            reg2.gravaNoArq(arquivo);
            seekArq(j);
            reg.gravaNoArq(arquivo);
            i++;
            j--;
            mov += 2;
        }
        if (ini < j) {
            QuickCP(ini, j);
        }
        if (i < fim) {
            QuickCP(i, fim);
        }
    }
    
    
    private void split(int[] vet1, int[] vet2) //talvez mudar pra Registro[] vet1 ...
    {
        int j=filesize()/2;
        for(int i = 0; i < filesize()/2; i++)
        {
            vet1[i] = getRegistro(i).getNumero();                   
            vet2[i] = getRegistro(j++).getNumero();    
        }
    }
    
    private void fusao(int[] vet1, int[] vet2, int seq)
    {
        int i, j, k;
        i=j=k=0;
        int auxseq=seq;
        Registro r = new Registro();
        while(k < filesize())
        {
            comp++;
            while(i < seq && j < seq)
            {
                comp++;
                if(vet1[i] < vet2[j])
                {
                    mov+=2;
                    seekArq(k++);
                    r.setNumero(vet1[i++]);
                    r.gravaNoArq(arquivo);
                    
                }
                else
                {
                    mov+=2;
                    seekArq(k++);
                    r.setNumero(vet2[j++]);
                    r.gravaNoArq(arquivo);
                }
            } 
            
            comp++;
            while(i<seq)
                 {
                     comp++;
                     mov+=2;
                    r.setNumero(vet1[i++]);
                    seekArq(k++);
                    r.gravaNoArq(arquivo);
                    
                }
            comp++;
            while(j<seq)
                { 
                    comp++;
                    mov+=2;
                    r.setNumero(vet2[j++]);
                    seekArq(k++);
                    r.gravaNoArq(arquivo);
                } 
              
            seq+=auxseq;
        } 
    }
    
    public void mergeSort() //ok
    {
        int vet1[] = new int [filesize()/2];
        int vet2[] = new int [filesize()/2];
        int seq = 1;
        
                
        while(seq < filesize())
        {
            split(vet1, vet2);
            fusao(vet1, vet2, seq);
            seq *=2;
        }
    }
    public void merge() {
        TL = filesize();
        Registro aux[] = new Registro[TL];
        for (int i = 0; i < TL; i++) {
            aux[i] = new Registro();
        }
        Registro reg = new Registro();
        Registro reg2 = new Registro();
        mergesort(aux, 0, TL - 1, reg, reg2);
    }

    public void mergesort(Registro aux[], int esq, int dir, Registro reg, Registro reg2) {
        int meio;
        comp++;
        if (esq < dir) {
            meio = (esq + dir) / 2;
            mergesort(aux, esq, meio, reg, reg2);
            mergesort(aux, meio + 1, dir, reg, reg2);
            fusao(aux, esq, meio, meio + 1, dir, reg, reg2);
        }
    }

    public void fusao(Registro aux[], int ini1, int fim1, int ini2, int fim2, Registro reg, Registro reg2) {
        int i = ini1, j = ini2, k = 0;

        while (i <= fim1 && j <= fim2) {
            seekArq(i);
            reg.leDoArq(arquivo);
            seekArq(j);
            reg2.leDoArq(arquivo);
            if (reg.getNumero() < reg2.getNumero()) {
                aux[k++].setReg(reg);
                i++;
            } else {
                aux[k++].setReg(reg2);
                j++;
            }
        }
        while (i <= fim1) {
            seekArq(i);
            reg.leDoArq(arquivo);
            aux[k++].setReg(reg);
            i++;
        }
        while (j <= fim2) {
            seekArq(j);
            reg2.leDoArq(arquivo);
            aux[k++].setReg(reg2);
            j++;
        }        
        for (i = 0; i < k; i++) {
            seekArq(ini1 + i);
            aux[i].gravaNoArq(arquivo);
            mov++;
        }
    }

    
    public void shellShort() // ok
    {
        int total = filesize();
        int temp;
        Registro r = new Registro();
        for (int h = total / 2; h > 0; h /= 2) {
            for (int i = h; i < total; i++) {
                temp = getRegistro(i).getNumero();
                int j;
                comp++;
                for (j = i; j >= h && getRegistro(j - h).getNumero() > temp; j -= h) {
                    comp++;
                    r.setNumero(getRegistro(j - h).getNumero());
                    seekArq(j);
                    r.gravaNoArq(arquivo);
                    mov++;
                }
                seekArq(j);
                r.setNumero(temp);
                r.gravaNoArq(arquivo);
                mov++;
            }
        }
    }

    public int getMaior() throws IOException {
        int i = 1, m;
        long savedpos;
        savedpos = arquivo.getFilePointer();
        Registro r = new Registro();
        seekArq(0);
        r.leDoArq(arquivo);
        m = r.getNumero();
        while (i < filesize()) {
            r.leDoArq(arquivo);
            
            if (m < r.getNumero()) {
                m = r.getNumero();
            }
            i++;
        }
        arquivo.seek(savedpos);
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

    public void radixSort() //ok
    {
        int[] laux = new int[filesize()];
        int[] vet = new int[100];
        int[][] VV = new int[filesize()][100];
        Registro r = new Registro();
        seekArq(0);
        int i = 0;
        int f = 0;
        int a;
        int cont = 0;
        int maxCont = 0;
        while (!eof()) {
            r.leDoArq(arquivo);
            laux[i++] = r.getNumero();
        }
        for (int w = 0; w < filesize(); w++) {
            //insere os vetores no Vetor
            a = laux[w];
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
                for (int s = 0; s < filesize(); s++) {
                    comp++;
                    if (VV[s][h] == p) {
                        mov++;
                        seekArq(t);
                        r.setNumero(vetToInt(VV[s], maxCont));
                        r.gravaNoArq(arquivo);
                        t++;
                    }
                }
            }
            laux = new int[filesize()];
            VV = new int[filesize()][100];
            vet = new int[100];

            for (int x = 0; i < filesize(); i++) {
                seekArq(x);
                r.leDoArq(arquivo);
                laux[x] = r.getNumero();
            }
            f = 0;
            cont = 0;
            for (int w = 0; w < filesize(); w++) {
                //insere os vetores no Vetor
                seekArq(w);
                r.leDoArq(arquivo);
                a = r.getNumero();
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

    public void combSort() //ok
    {
        int gap = filesize(), aux;
        int qtElem = filesize();
        Registro r = new Registro();
        Registro r2 = new Registro();
        Registro r3 = new Registro();
        Registro r4 = new Registro();
        seekArq(0);
        while (gap >= 1) {
            for (int i = 0; i < qtElem; i++) {
                if (i + gap < qtElem) {
                    seekArq(i);
                    r2.leDoArq(arquivo);
                    seekArq(i + gap);
                    r3.leDoArq(arquivo);
                    comp++;
                    if (r2.getNumero() > r3.getNumero()) {
                        seekArq(i);
                        r.leDoArq(arquivo);
                        aux = r.getNumero();
                        seekArq(i + gap);
                        r4.leDoArq(arquivo);
                        r.setNumero(r4.getNumero());
                        seekArq(i);
                        r.gravaNoArq(arquivo);
                        seekArq(i + gap);
                        r.setNumero(aux);
                        r.gravaNoArq(arquivo);
                        mov+=2;
                    }
                }
            }
            gap = (int) (gap / 1.3);
        }
    }

    public void gnomeSort() //ok
    {
        int aux;
        seekArq(0);
        int qtElem = filesize();
        Registro r1 = new Registro();
        Registro r2 = new Registro();
        for (int i = 0; i < qtElem - 1; i++) {
            seekArq(i);
            r1.leDoArq(arquivo);
            seekArq(i + 1);
            r2.leDoArq(arquivo);
            comp++;
            if (r1.getNumero() > r2.getNumero()) {
                seekArq(i);
                r2.gravaNoArq(arquivo);
                seekArq(i + 1);
                r1.gravaNoArq(arquivo);
                i = -1;
                mov+=2;
            }
        }
    }

    public int buscaBinaria(int chave, int TL) {
        int meio = TL / 2;
        int i = 0;
        int j = TL - 1;
        Registro reg = new Registro();
        seekArq(meio);
        reg.leDoArq(arquivo);
        
        while (i < j && chave != reg.getNumero()) {
            
            if (chave > reg.getNumero()) {
                i = meio + 1;
            } else {
                j = meio - 1;
            }
            meio = (i + j) / 2;
            seekArq(meio);
            reg.leDoArq(arquivo);
        }
        seekArq(meio);
        reg.leDoArq(arquivo);
       
        if (chave > reg.getNumero()) {
            return meio + 1;
        }
        return meio;
    }

    public void insercaoBinaria() {
        Registro aux = new Registro();
        Registro reg = new Registro();

        int pos;
        for (int i = 1; i < TL; i++) {
            seekArq(i);
            aux.leDoArq(arquivo);
            pos = buscaBinaria(aux.getNumero(), i);
            
            for (int j = i; j > pos; j--) {
                seekArq(j - 1);
                reg.leDoArq(arquivo);
                reg.gravaNoArq(arquivo);
                mov++;
                comp++;
            }
            seekArq(pos);
            aux.gravaNoArq(arquivo);
            mov++;
        }
    }

    public void shake() {
        TL = filesize();
        int fim = TL - 1;
        int inicio = 0;
        Registro regi = new Registro();
        Registro regi2 = new Registro();
        while (fim > inicio) {
            for (int i = inicio; i < fim; i++) {
                seekArq(i);
                regi.leDoArq(arquivo);
                regi2.leDoArq(arquivo);

                comp++;
                if (regi.getNumero() > regi2.getNumero()) {
                    seekArq(i);
                    regi2.gravaNoArq(arquivo);
                    regi.gravaNoArq(arquivo);
                    mov+=2;
                }
            }
            fim--;
            for (int i = fim; i > inicio; i--) {
                seekArq(i - 1);
                regi.leDoArq(arquivo);
                regi2.leDoArq(arquivo);

                comp++;
                if (regi2.getNumero() < regi.getNumero()) {
                    seekArq(i - 1);
                    regi2.gravaNoArq(arquivo);
                    regi.gravaNoArq(arquivo);
                    mov+=2;
                }
            }
            inicio++;
        }
    }

    public void counting() {
        TL = filesize();
        int maior;
        int menor;
        int i;
        int pos;
        Registro reg = new Registro();
        seekArq(0);

        reg.leDoArq(arquivo);
        maior = reg.getNumero();
        menor = reg.getNumero();
        for (i = 1; i < TL; i++) {
            reg.leDoArq(arquivo);
            comp++;
            if (maior < reg.getNumero()) {
                maior = reg.getNumero();
            }
            comp++;
            if (menor > reg.getNumero()) {
                menor = reg.getNumero();
            }
        }
        int aux[] = new int[maior + 1];

        for (i = 0; i < maior + 1; i++) {
            aux[i] = 0;
        }

        for (i = 0; i < TL; i++) {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = reg.getNumero();
            aux[pos - menor] += 1;
        }

        for (i = 1; i < maior + 1; i++) {
            aux[i] += aux[i - 1];
        }
        int vet[] = new int[TL];

        for (i = 0; i < TL; i++) {
            seekArq(i);
            reg.leDoArq(arquivo);
            pos = reg.getNumero();
            vet[aux[pos] - 1] = pos;
        }

        seekArq(0);
        for (i = 0; i < TL; i++) {
            reg.setNumero(vet[i]);
            reg.gravaNoArq(arquivo);
            mov++;
        }
    }

    public void bucket() {
        Registro reg1 = new Registro();
        ArrayList<ArrayList<Integer>> lista = new ArrayList<>();
        int divisor;
        int j;
        int vet[] = new int[TL];
        int maior, menor;

        for (int i = 0; i < 10; i++) {
            lista.add(new ArrayList<>());
        }

        seekArq(0);
        reg1.leDoArq(arquivo);
        maior = reg1.getNumero();
        menor = reg1.getNumero();
        for (int i = 1; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            comp++;
            if (maior < reg1.getNumero()) {
                maior = reg1.getNumero();
            }
            comp++;
            if (menor > reg1.getNumero()) {
                menor = reg1.getNumero();
            }
        }
        divisor = (int) ((maior + 1) * 100) / 10;

        for (int i = 0; i < TL; i++) {
            seekArq(i);
            reg1.leDoArq(arquivo);
            j = reg1.getNumero() / divisor;
            lista.get(j).add(reg1.getNumero());
        }
        for (int i = 0; i < 10; i++) {
            Collections.sort(lista.get(i));
        }
        int pos = 0;
        for (int i = 0; i < 10; i++) {
            for (int x = 0; x < lista.get(i).size(); x++) {
                vet[pos] = lista.get(i).get(x);
                pos++;
            }
        }
        for (int i = 0; i < TL; i++) {
            seekArq(i);
            reg1.setNumero(vet[i]);
            reg1.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    
    public void split2 (int esq, int dir,Registro r1, Registro r2, int[] vet)
    {
        int meio;

        if (esq < dir) {
            meio = (esq + dir) / 2;
            split2(esq, meio,r1,r2,vet);
            split2(meio + 1, dir,r1,r2,vet);
            fusao2(esq, meio, meio + 1, dir,r1,r2,vet);
        }
    }
         
    
    public void merge2()
    {
        Registro r1 = new Registro();
        Registro r2 = new Registro();
        int i=0;
        int[] vet = new int[filesize()];
        seekArq(0);
        while(!eof())
        {
            seekArq(i);
            r1.leDoArq(arquivo);
            vet[i++]=r1.getNumero();
        }
        seekArq(0);
        split2(0, filesize()-1,r1,r2,vet);
    }
    
    
    public void fusao2(int ini1, int fim1, int ini2, int fim2,Registro r1, Registro r2, int[] vet)
    {
        
        int i = ini1, j = ini2, k = 0;

        while (i <= fim1 && j <= fim2)
        {
            seekArq(i);
            r1.leDoArq(arquivo);
            seekArq(j);
            r2.leDoArq(arquivo);
            
            comp++;
            if (r1.getNumero() < r2.getNumero()) 
            {
                seekArq(i++);
                r1.leDoArq(arquivo);
                vet[k++]=r1.getNumero();
                
                //getNo(k++, L).setInfo(getNo(i++).getInfo());
            } 
            else 
            {
                seekArq(j++);
                r2.leDoArq(arquivo);
                vet[k++]=r2.getNumero();
                
                //getNo(k++, L).setInfo(getNo(j++).getInfo());
            }
        }
        
        while (i <= fim1)
        {
            seekArq(i++);
            r1.leDoArq(arquivo);
            vet[k++]=r1.getNumero();
            //getNo(k++, L).setInfo(getNo(i++).getInfo());            
        }
        while (j <= fim2)
        {
           seekArq(j++);
            r2.leDoArq(arquivo);
            vet[k++]=r2.getNumero();
            //getNo(k++, L).setInfo(getNo(j++).getInfo());           
            
        }
        for (i = 0; i < k; i++)
        {
            seekArq(ini1+i);
            r1.setNumero(vet[i]);
            r1.gravaNoArq(arquivo);
            //getNo(ini1 + i).setInfo(getNo(i, L).getInfo());
            mov++;
            
        }
    }
    
    private void timInsertionSort(int esq, int dir,Registro r1, Registro r2)
    {
        int temp, j;
        
        comp++;
        for (int i = esq + 1; i <= dir; i++)
        {
            comp++;
            seekArq(i);
            r1.leDoArq(arquivo);
            temp = r1.getNumero();
            j = i - 1;
            seekArq(j);
            r1.leDoArq(arquivo);
            comp++;
            while (r1.getNumero() > temp && j >= esq)
            {
                comp++;
                seekArq(j+1);
                r1.gravaNoArq(arquivo);
                mov++;
                //getNo(j + 1).setInfo(getNo(j).getInfo());
                //j--;
                seekArq(--j);
                r1.leDoArq(arquivo);
            }
            r2.setNumero(temp);
            seekArq(j+1);
            r2.gravaNoArq(arquivo);
            mov++;
        }
    }
    
    private void timMerge(int e, int m, int d,Registro r1, Registro r2) {

        int tam1 = m - e + 1, len2 = d - m;
        int[] esq = new int[tam1];
        int[] dir = new int[len2];
        for (int x = 0; x < tam1; x++)
        {
            seekArq(e+x);
            r1.leDoArq(arquivo);
            esq[x] = r1.getNumero();
            mov+=2;
        }

        for (int x = 0; x < len2; x++)
        {
            seekArq(m + 1 + x);
            r2.leDoArq(arquivo);
            dir[x] = r2.getNumero();
            mov+=2;
        }

        int i = 0;
        int j = 0;
        int k = e;

        while (i < tam1 && j < len2) {
            comp++;
            if (esq[i] <= dir[j])
            {
                seekArq(k);
                r1.setNumero(esq[i++]);
                r1.gravaNoArq(arquivo);

            } else
            {
                seekArq(k);
                r2.setNumero(dir[j++]);
                r2.gravaNoArq(arquivo);
                
            }
            mov++;
            k++;
        }

        while (i < tam1) {
            seekArq(k);
            r1.setNumero(esq[i]);
            r1.gravaNoArq(arquivo);
            k++;
            i++;
            mov++;
        }

        while (j < len2) {
            seekArq(k);
            r2.setNumero(dir[j]);
            r2.gravaNoArq(arquivo);
            //getNo(k).setInfo(dir[j]);
            k++;
            j++;
            mov++;
        }
    }
    
    
    public void timSort()
    {
        int n = filesize(), RUN = 32;
        int menor;
        Registro r1 = new Registro();
        Registro r2 = new Registro();
        for (int i = 0; i < n; i = i + RUN) {
            if (i + 31 < n - 1) {
                menor = i + 31;
            } else {
                menor = n - 1;
            }
            timInsertionSort(i, menor,r1,r2);
        }

        for (int size = RUN; size < n; size = 2 * size) {
            for (int esq = 0; esq < n; esq += 2 * size) {

                int meio = esq + size - 1;
                int dir = Math.min((esq + 2 * size - 1), (n - 1));
                timMerge(esq, meio, dir,r1,r2);
            }
        }
    }

}
