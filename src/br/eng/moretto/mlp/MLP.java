package br.eng.moretto.mlp;

import java.lang.Math;

/**
 * Classe MLP
 * Implementação de uma rede neural MLP
 * 
 * ok - @TODO generalizar os inputs e outputs 
 * ok - @TODO melhorar o construtor defaulf (passando parametro para a camada hidden)
 * @TODO fazer a entrada externa via matriz
 * @TODO fazer esquemas para leitura de arquivo (mksets talvez) (usando o pacote java.no)
 *   
 * 
 * 
 * @author emerson * @date 22/06/2006
 * @version 0.3
 * */
public class MLP {

    private static double momentum = 0.35;
    public int trainedEpochs = 0;
    
    //camadas
    public double input[];
    public double hidden[];
    public double output[];
    
    //pesos e delta entre camada input e hidden
    public double w1[][];
    public double delta1[];
    
    //pesos e delta entre camada hidden e output
    public double w2[][];
    public double delta2[];

    /**
     * Construtor da rede neural MLP
     * 
     * @param inputSize: quantidade de neuronios na camada de input
     * @param hiddenSize: quantidade de neuronios na camada hidden
     * @param outputSize: quantidade de neuronios na camada de out
     * 
     * */
    public MLP(int inputSize, int hiddenSize, int outputSize) {
        
        input = new double[inputSize];
        hidden = new double[hiddenSize];
        output = new double[outputSize];
        
        delta1 = new double[hiddenSize];
        delta2 = new double[outputSize];
        
        w1 = new double[inputSize][hiddenSize];
        w2 = new double[hiddenSize][outputSize];
        
        // inicializando os pesos entre 0.1 e 0.9
        for(int i=0; i<w1.length; i++)
            for(int j=0; j<w1[i].length; j++)
                w1[i][j] = Math.random()*0.8+0.1;

        for(int i=0; i<w2.length; i++)
            for(int j=0; j<w2[i].length; j++)
                w2[i][j] = Math.random()*0.8+0.1;
    }

    public void train(int e, int[] data, int[] result) {
        for(int i=0; i<e; i++) {
            // chamando metodo prendizagem com dados de treinamento
            learn( data, result );
            trainedEpochs++;
        }
    }

    public void learn( int inp[], int out[] ) {
        int i, j;
        double sum, out_j;

        // inicializando input
        for(i=0; i<inp.length; i++)
            input[i] = inp[i];

        // calculando as unidades hidden
        for(j=0; j<hidden.length-1; j++) {
            sum = 0;
            for(i=0; i<input.length; i++)
                sum = sum + w1[i][j]*input[i];

            hidden[j] = 1 / ( 1 + Math.exp(-sum));
        }

        // Calculando output
        for(j=0; j<output.length; j++) {
            sum = 0;
            for(i=0; i<hidden.length; i++)
                sum = sum + w2[i][j]*hidden[i];

            output[j] = 1 / (1 + Math.exp(-sum));
        }

        /*
         * Backpropagacao
         */
        
        // Calculando delta2 
        for(j=0; j<output.length; j++) {
            if( out[j] == 0 )
                out_j = 0.1;
            else if( out[j] == 1 )
                out_j = 0.9;
            else
                out_j = out[j];
            delta2[j] = output[j]*(1-output[j])*(out_j-output[j]);
        }

        // Calculando delta1 
        for(j=0; j<hidden.length; j++) {
            sum = 0;
            for(i=0; i<output.length; i++)
                sum = sum + delta2[i]*w2[j][i];

            delta1[j] = hidden[j]*(1-hidden[j])*sum;
        }

        // Ajustando pesos com delta2        
        for(i=0; i<hidden.length; i++)
            for(j=0; j<output.length; j++)
                w2[i][j] = w2[i][j] + momentum*delta2[j]*hidden[i];

        // Ajustando pesos com delta1
        for(i=0; i<input.length; i++)
            for(j=0; j<hidden.length; j++)
                w1[i][j] = w1[i][j] + momentum*delta1[j]*input[i];
    }

    public void test(int inp[], int out[]) {
        int i, j;
        double sum;

        // inicializando input
        for(i=0; i<inp.length; i++)
            input[i] = inp[i];

        // Calculando hidden
        for(j=0; j<hidden.length-1; j++) {
            sum = 0;
            for(i=0; i<input.length; i++)
                sum = sum + w1[i][j]*input[i];

            hidden[j] = 1 / ( 1 + Math.exp(-sum));
        }

        // Calculando output
        for(j=0; j<output.length; j++) {

            sum = 0;
            for(i=0; i<hidden.length; i++)
                sum = sum + w2[i][j]*hidden[i];

            output[j] = 1 / (1 + Math.exp(-sum));
        }

        // Populando o vetor de saida
        for(i=0; i<output.length; i++)
            if( output[i] >= 0.5 )
                out[i] = 1;
            else
                out[i] = 0;
    }
}