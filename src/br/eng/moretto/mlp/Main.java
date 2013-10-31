package br.eng.moretto.mlp;

public class Main {
    
    public static void main(String[] args) {
        

        //vetores do aprendizado supervisionado
        int data[][] = {{0,0,0},{1,0,0},{0,1,0},{0,0,1},{0,0,1},{0,1,1},{1,0,1},{0,0,0}};
        int result[][] = {{0,0},{1,0},{1,0},{1,0},{1,0},{1,0},{1,0},{0,0}};
        
        //Criando a rede
        MLP mlp = new MLP(3,6,2);
        
        //treinando a rede com os vetores de aprendizagem
        for(int i=0 ; i<500 ; i++) {
            for(int j=0; j<data.length ; j++)
                mlp.train(1,data[j],result[j]);
        }

        //testando a rede com o vetor de input
        //a rede preenche o vetor de saida out
        int inp[] = {0,0,0};
        int out[] = {0,0};
        
        mlp.test(inp,out);
        
        System.out.println(out[0]+"-"+out[1]);
    }

}
