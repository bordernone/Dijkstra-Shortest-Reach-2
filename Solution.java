import java.io.*;
import java.util.*;
import java.text.*;
import java.math.*;
import java.util.regex.*;

public class Solution {
    public static void main(String[] args){
        FastReader in=new FastReader();
        int t=in.nextInt();
        for (int tt=0;tt<t;tt++){
            int nbSommets=in.nextInt();
            TreeMap<Integer,Long>[] graph=new TreeMap[nbSommets]; //(Voisin,Cout pour aller a ce voisin)
            int nbArretes=in.nextInt();
            for (int mm=0;mm<nbArretes;mm++){
                int src=in.nextInt()-1;
                int destination=in.nextInt()-1;
                long cout=in.nextInt();
                if (graph[src]==null){
                    graph[src]=new TreeMap<>();
                }
                if(graph[src].containsKey(destination)){
                    long pred=graph[src].get(destination);
                    if(cout<pred){
                        graph[src].replace(destination,cout);
                    }
                }else {
                    graph[src].put(destination,cout);
                }
                if (graph[destination]==null){
                    graph[destination]=new TreeMap<>();
                }
                if(graph[destination].containsKey(src)){
                    long pred=graph[destination].get(src);
                    if(cout<pred){
                        graph[destination].replace(src,cout);
                    }
                }else {
                    graph[destination].put(src,cout);
                }
            }
            int source=in.nextInt()-1;
            Djikstra djikstra=new Djikstra();
            int[] predecesseurs=new int[nbSommets];
            long[] distances=djikstra.getPlusCourtChemins(graph,source,predecesseurs);
            for (int i=0;i<distances.length;i++){
                if(i!=source){
                    if(distances[i]==Long.MAX_VALUE){
                        System.out.print(-1);
                    }else {
                        System.out.print(distances[i]);
                    }
                    if (i<distances.length-1){
                        System.out.print(" ");
                    }
                }
            }
            System.out.println();
        }
    }
    static class FastReader
    {
        BufferedReader br;
        StringTokenizer st;

        public FastReader()
        {
            br = new BufferedReader(new
                    InputStreamReader(System.in));
        }

        String next()
        {
            while (st == null || !st.hasMoreElements())
            {
                try
                {
                    st = new StringTokenizer(br.readLine());
                }
                catch (IOException  e)
                {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt()
        {
            return Integer.parseInt(next());
        }

        long nextLong()
        {
            return Long.parseLong(next());
        }

        double nextDouble()
        {
            return Double.parseDouble(next());
        }

        String nextLine()
        {
            String str = "";
            try
            {
                str = br.readLine();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            return str;
        }
    }
}
class Djikstra{
    public long[] getPlusCourtChemins(TreeMap<Integer,Long>[] graph,int source,int[] predecesseurs){
        long[] distances=new long[graph.length];
        TreeSet<Integer> ensembleNonMarquer=initialisation(distances,predecesseurs,source);
        while (!ensembleNonMarquer.isEmpty()){
            int sommetAMarquer=getSommetAMarquer(distances,ensembleNonMarquer);
            if(sommetAMarquer!=-1){
                ensembleNonMarquer.remove(sommetAMarquer);
                if(graph[sommetAMarquer]!=null){
                    for (Map.Entry<Integer,Long> voisin_cout:graph[sommetAMarquer].entrySet()){
                        if(distances[sommetAMarquer]!=Long.MAX_VALUE){
                            long newDistance=distances[sommetAMarquer]+voisin_cout.getValue();
                            if(newDistance<distances[voisin_cout.getKey()]){
                                distances[voisin_cout.getKey()]=newDistance;
                                predecesseurs[voisin_cout.getKey()]=sommetAMarquer;
                            }
                        }
                    }
                }
            }else {
                return distances;
            }
        }
        return distances;
    }
    private TreeSet<Integer> initialisation(long[] distances,int[] predecesseurs,int source){
        TreeSet<Integer> ensembleNonMarquer=new TreeSet<>();
        for (int i=0;i<distances.length;i++){
            distances[i]=Long.MAX_VALUE;
            predecesseurs[i]=-1;
            ensembleNonMarquer.add(i);
        }
        distances[source]=0;
        predecesseurs[source]=0;
        return ensembleNonMarquer;
    }
    private int getSommetAMarquer(long[] distances,TreeSet<Integer> ensembleNonMarquer){
        int sommetAmarquer=ensembleNonMarquer.first();
        long min_distance=distances[sommetAmarquer];
        for (Integer sommet:ensembleNonMarquer) {
            if(distances[sommet]<min_distance){
                sommetAmarquer=sommet;
                min_distance=distances[sommet];
            }
        }
        if(min_distance==Long.MAX_VALUE){
            return -1;
        }
        return sommetAmarquer;
    }

}
