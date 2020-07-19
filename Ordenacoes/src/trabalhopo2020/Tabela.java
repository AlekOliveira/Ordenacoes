/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package trabalhopo2020;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author Alexandre
 */
public class Tabela {
    static final int n = 1024;
    private Arquivo ordenado, reverso, randomico, auxreverso, auxrandomico;
    private FileWriter txt;
    private PrintWriter escritor;
    private int tini, tfim, mov, com;   
    
    
    
    public Tabela(){}
    
    
    private void initArquivos()
    {
        this.ordenado = new Arquivo("Odenado.dat");
        this.reverso = new Arquivo("Reverso.dat");       
        this.randomico = new Arquivo("Randomico.dat");
        ordenado.truncate(n);
        reverso.truncate(n);
        randomico.truncate(n);
        this.auxrandomico = new Arquivo();
        this.auxreverso = new Arquivo();
        try
        {
           this.txt = new FileWriter("tabela.txt");
           this.escritor = new PrintWriter(txt);
        }
        catch(Exception e)
        {
            System.out.println("Erro ao criar arquivo TXT");
            System.exit(-1);
        }
    }
    
    private void escreveCabecalho()
    {
        escritor.println("|MÉTODOS DE ORDENAÇÃO|ARQUIVO ORDENADO\t\t\t\t|ARQUIVO EM ORDEM REVERSA\t\t     "
                + "   |ARQUIVO RANDÔMICO");
        escritor.println("|\t\t     |Comp. 1\t|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|Comp. 1|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|"
                + "Comp. 1|Comp. 2|Mov. 1\t|Mov. 2\t|Tempo\t|");
    }
    
    private void escreveTabela(String nomeMetodo, int cp, double ce, int mp, double me, double tempo)
    {
        escritor.printf("%s %d\t| %.0f\t| %d\t| %.0f\t| %.0f\t|", nomeMetodo, cp, ce, mp, me, tempo);
    }
    
    private void insertionSort()
    {
        //INSERÇÃO DIRETA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.inserctionSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Insertion Sort\t     |", com, n - 1, mov, 3 * (n - 1) , tfim - tini);
       // System.out.println("ordenado done");
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        reverso.inserctionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2) + n - 4) / 4, mov, (Math.pow(n, 2) + 3 * n - 4) / 2 , tfim - tini);
       // System.out.println("revers done");
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.inserctionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2) + n - 2) / 4, mov, (Math.pow(n, 2) + 9 * n - 10) / 4, tfim - tini);
        escritor.println("\n");
       // System.out.println("rand done");
    }
    
    private void binaryInsertion()
    {
        //INSERÇÃO BINÁRIA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.insercaoBinaria();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Binary Insertion    |", com, 0, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.insercaoBinaria();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, (Math.pow(n, 2) + 3 * n - 4) / 2 , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.insercaoBinaria();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, n * (Math.log(n)), mov, (Math.pow(n, 2) + 9 * n - 10) / 2 , tfim - tini);
        escritor.println("\n");
    }
    
    private void selectionSort()
    {
        //SELEÇÃO DIRETA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.selectionSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Select Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.selectionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / 4 + (3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.selectionSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f), tfim - tini);
        escritor.println("\n");
    }
    
    private void bubbleSort()
    {
        //BOLHA
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.bubbleSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Bubble Sort\t     |", com, 0, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.bubbleSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.bubbleSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }
    
    private void shakeSort()
    {
        //SHELL
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.shake();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Shake Sort\t     |", com, (Math.pow(n, 2) - n) / 2, mov, 0 , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.shake();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2) - n) / 2, mov, 3 * (Math.pow(n, 2) - n) / 4, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.shake();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2) - n) / 2, mov, 3 * (Math.pow(n, 2) - n) / 2, tfim - tini);
        escritor.println("\n");
    }
    
    private void shellSort()
    {
        //SHELL
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.shellShort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Shell Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.shellShort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.shellShort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }
    
    private void heapSort()
    {
        //HEAP
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.heapSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Heap Sort\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.heapSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.heapSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }
        
    private void quickSortI()
    {
        //QUICK SEM PIVO
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.QuickSemPivo();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Quick Sort 1\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.QuickSemPivo();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.QuickSemPivo();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n");
    }
    
    private void quickSortII()
    {
        //QUICK COM PIVO
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.QuickComPivo();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Quick Sort 2\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.QuickComPivo();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.QuickComPivo();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n"); 
    }
    
    private void mergeI()
    {
        //MERGE 1ª Implementação
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.merge();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Merge 1\t     |", com, (Math.pow(n, 2)- n) / 2, mov, 3 * (n - 1) , tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.merge();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, Math.pow(n, 2) / (4 + 3 * (n - 1)) , tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.merge();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, (Math.pow(n, 2)- n) / 2, mov, n * (Math.log((double) n) + 0.577216f) , tfim - tini);
        escritor.println("\n"); 

    }
    
    private void mergeII()
    {
        //MERGE 2ª Implementação
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.merge2();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Merge 2\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.merge2();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.merge2();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
    
    private void counting()
    {
        //Counting
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.counting();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|CountingSort\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.counting();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.counting();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
    
    private void bucket()
    {
        //Bucket
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.bucket();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Gnome Sort\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.bucket();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.bucket();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
   
    private void radix()
    {
        //Radix
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.radixSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Radix\t\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.radixSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.radixSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
    
    private void comb()
    {
        //Comb
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.combSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Comb\t\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.combSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.combSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
    
    private void gnome()
    {
        //Gnome
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.gnomeSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Gnome Sort\t     |", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.gnomeSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.gnomeSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
    }
    
    
    
    private void tim()
    {
        //Tim
        //Arquivo Odernado
        ordenado.initComp();
        ordenado.initMov();
        tini = (int) System.currentTimeMillis();
        ordenado.timSort();
        tfim = (int) System.currentTimeMillis();
        com = ordenado.getComp();
        mov = ordenado.getMov();
        escreveTabela("|Tim Sort\t     |", com, -1, mov, -1, tfim - tini);
        
      //Arquivo Reverso
        auxreverso.copiaArquivo(reverso);
        auxreverso.initComp();
        auxreverso.initMov();
        tini = (int) System.currentTimeMillis();
        auxreverso.timSort();
        tfim = (int) System.currentTimeMillis();
        com = auxreverso.getComp();
        mov = auxreverso.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        
        System.out.println("reverse done");
        
        //Arquivo Randômico
        auxrandomico.copiaArquivo(randomico);
        auxrandomico.initComp();
        auxrandomico.initMov();
        tini = (int) System.currentTimeMillis();
        auxrandomico.timSort();
        tfim = (int) System.currentTimeMillis();
        com = auxrandomico.getComp();
        mov = auxrandomico.getMov();
        escreveTabela("", com, -1, mov, -1, tfim - tini);
        escritor.println("\n"); 
        System.out.println("rand done");
    }
    
    public void gerarTabela() throws IOException
    {
        initArquivos();
        escreveCabecalho();
        ordenado.geraArquivoOrdenado();       
        reverso.geraArquivoReverso();
        randomico.geraArquivoRandomico();
        
       /* auxreverso.copiaArquivo(reverso);   
        
        auxrandomico.copiaArquivo(randomico);
        
       
        
        
        //ordenado.exibe();
        
        reverso.exibe();
        System.out.println("\nauxrever");
        auxreverso.exibe();       
        System.out.println("COMECANDO OUTRO \n");
        
        randomico.exibe();
        System.out.println("\nauxrand");
        auxrandomico.exibe();*/
        
        insertionSort(); //OK 
        System.out.println("1");
        binaryInsertion(); //OK
        System.out.println("2");
        selectionSort(); //OK
        System.out.println("3");
        bubbleSort(); //Ok 
        System.out.println("4");
        shakeSort(); //Ok
        System.out.println("5");
        shellSort(); //Ok
        System.out.println("6");
        heapSort(); //Ok 
        System.out.println("7");
        quickSortI(); //Ok
        System.out.println("8");        
        quickSortII(); //Ok
        System.out.println("9");
        mergeI(); //Ok         
        System.out.println("-10");
        counting(); //Ok
        System.out.println("-11");
        bucket(); //Ok
        System.out.println("-12");
        radix(); //Ok
        System.out.println("-13");
        comb();
        System.out.println("-14");  
        gnome(); //Ok 
        System.out.println("-15"); 
        mergeII();
        System.out.println("-16");
        tim();
        System.out.println("-17");
//        
        txt.close();
        
    }
    
    
}
