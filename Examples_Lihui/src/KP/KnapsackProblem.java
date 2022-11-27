package KP;

import java.io.File;
import java.util.Stack;

import KP.SolutionKP.KnapSackNH;
import KP.SolutionKP.SwapNH;
import KP.SolutionKP.UnionNH;
import KP.SolutionKP.UnpackedNH;
import KP.evalfs.MaxProfitPerPound;
import KP.evalfs.MinWeight;
import KP.heuristics.ConstructEmpty;
import KP.heuristics.IntersectCrossover;
import KP.heuristics.PerturbativeRuinRecreate;
import KP.heuristics.UnionCrossover;
import KP.modifiers.Insert;
import KP.modifiers.Remove;
import KP.modifiers.Swap;
import KP.parsers.CFGParserKP;
import QAP.SolutionQAP;
import hfu.BasicProblemDomain;
import hfu.BenchmarkInstance;
import hfu.Parser;
import hfu.heuristics.ConstructionHeuristic;
import hfu.heuristics.CrossoverHeuristic;
import hfu.heuristics.LocalSearchHeuristic;
import hfu.heuristics.ModifierFullLocalSearchHeuristic;
import hfu.heuristics.ModifierLocalSearchHeuristic;
import hfu.heuristics.ModifierMutationHeuristic;
import hfu.heuristics.MutationHeuristic;
import hfu.heuristics.RuinRecreateHeuristic;
import hfu.heuristics.selector.SelectBest;
import hfu.heuristics.selector.SelectFirst;
import hfu.heuristics.selector.SelectRandom;

public class KnapsackProblem extends BasicProblemDomain<SolutionKP,InfoKP>{
	
	public KnapsackProblem(long seed) {
		super(seed);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BenchmarkInstance<InfoKP>[] getBenchmarkInstances() {
		Parser<InfoKP> parser = new CFGParserKP();
		String[] instanceNames = getFileName();
//		BenchmarkInstance<InfoKP>[] benchmarks = new BenchmarkInstance[]{
//				new BenchmarkInstance<InfoKP>("instances/kp/0.kp",parser), //9445
//				new BenchmarkInstance<InfoKP>("instances/kp/1.kp",parser), //5024
//				new BenchmarkInstance<InfoKP>("instances/kp/2.kp",parser), //2193
//				new BenchmarkInstance<InfoKP>("instances/kp/3.kp",parser), //29
//				new BenchmarkInstance<InfoKP>("instances/kp/4.kp",parser), //12830
//				new BenchmarkInstance<InfoKP>("instances/kp/5.kp",parser), //17836
//				new BenchmarkInstance<InfoKP>("instances/kp/6.kp",parser), //7489
//				new BenchmarkInstance<InfoKP>("instances/kp/7.kp",parser), //4167
//				new BenchmarkInstance<InfoKP>("instances/kp/8.kp",parser), //42001
//				new BenchmarkInstance<InfoKP>("instances/kp/9.kp",parser), //2103
//		};
		BenchmarkInstance<InfoKP>[] benchmarks = new BenchmarkInstance[instanceNames.length];
		for (int i = 0; i < instanceNames.length; i ++) {
			System.out.println("KP line61 "+"instances/kp_test/"+instanceNames[i]);
			benchmarks[i] = new BenchmarkInstance<InfoKP>("instances/kp_test/"+instanceNames[i],parser);
			
			if (i == 1) {
				System.out.println("instances/kp/"+instanceNames[i]+" has been put into benchmarks");
			}
		}
		return benchmarks;
	}
	
	public static String[] getFileName() {
        String path = System.getProperty("user.dir") + "/instances/kp_test"; //path
    	Stack<String> instanceStack = new Stack<>();
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return null;
        }
        else {
        	getAllFiles(f, instanceStack);
        }
        int len = instanceStack.size();
        String[] instanceNames = new String[len];
        for (int j = 0; j < instanceNames.length; j ++) {
        	instanceNames[j] = instanceStack.pop();
//        	System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
        }
        return instanceNames;
	}
	
	public static void getAllFiles(File file, Stack<String> instanceNameStack) {
		File fa[] = file.listFiles();
        for (int i = 0; i < fa.length; i ++) {
        	if (fa[i].isDirectory()) {
        		getAllFiles(fa[i], instanceNameStack);	
        	}
        	else {
            	if (!fa[i].getName().equals(".DS_Store") && !fa[i].getName().substring(0,4).equals("read")) {
            		int nameIndex = fa[i].getParent().indexOf("kp_test");
            		System.out.println(fa[i].getParent());
//            		System.out.println(nameIndex);
            		String rootName = fa[i].getParent().substring(nameIndex+"kp_test".length());
            		System.out.println(rootName);
            		String resultName = rootName + fa[i].getName();

//            		String[] rootArr = fa[i].getParent().split("/");           		
//            		String resultName = rootArr[rootArr.length-1]+"/"+fa[i].getName();
            		System.out.println("line107 "+resultName);
            		instanceNameStack.push(resultName);
            	}
            }
        }
	}
	
	
//	public static String[] getFileName() {
//        String path = System.getProperty("user.dir") + "/instances/kp"; //path
//        File f = new File(path);
//        if (!f.exists()) {
//            System.out.println(path + " not exists");
//            return null;
//        }
//        else {
//        	File[] fa = f.listFiles();
////        	String[] instanceNames = new String[fa.length-1];
////        	for (int i = 0, j = 0; i < instanceNames.length; i ++) {
////        		if (fa[i].getName().equals(".DS_Store")) {
////        			j = i + 1;
////        		}
////        		else {
////        			j = i;
////        		}
////				instanceNames[i] = fa[j].getName();
////        	}
//        	int len = 0;
//            Stack<String> instanceStack = new Stack<>();
//            for (int i = 0; i < fa.length; i ++) {
//            	if (!fa[i].getName().equals(".DS_Store")) {
//            		instanceStack.push(fa[i].getName());
//            		len ++;
////            		System.out.println(fa[i].getName()+" is put into stack"+"\n");
//            	}
//            }
//            String[] instanceNames = new String[len];
//            for (int j = 0; j < instanceNames.length; j ++) {
////            	This assumes that instanceNames.length == length of stack
//            	instanceNames[j] = instanceStack.pop();
////            	System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
//            }
//            return instanceNames;
//        }
//	}
	
	@Override
	public ConstructionHeuristic<SolutionKP, InfoKP> getConstructionHeuristic() {
		return new ConstructEmpty();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LocalSearchHeuristic<SolutionKP, InfoKP>[] getLocalSearchHeuristics() {
		LocalSearchHeuristic<SolutionKP, InfoKP>[] llhs_ls = new LocalSearchHeuristic[]{
				new ModifierLocalSearchHeuristic<SolutionKP,InfoKP,UnpackedNH>(new SelectFirst<SolutionKP,InfoKP,UnpackedNH>(), new Insert()), //packs randomly fitting piece (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectFirst<SolutionKS,InfoKS,UnpackedNH>(), new Insert()), //packs randomly fitting piece (till no more fit)
				new ModifierLocalSearchHeuristic<SolutionKP,InfoKP,UnpackedNH>(new SelectBest<SolutionKP,InfoKP,UnpackedNH>(false), new Insert()), //packs most expensive fitting piece (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectBest<SolutionKS,InfoKS,UnpackedNH>(false), new Insert()), //packs most expensive fitting piece (till no more fit)
				//new ModifierLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectFirst<SolutionKS,InfoKS,UnpackedNH>(new MaxProfitPerPound()), new Insert()), //packs randomly a fitting piece increasing Profit per Pound (1 move)
				new ModifierFullLocalSearchHeuristic<SolutionKP,InfoKP,UnpackedNH>(new SelectFirst<SolutionKP,InfoKP,UnpackedNH>(new MaxProfitPerPound()), new Insert()), //packs randomly a fitting piece increasing Profit per Pound (till no more fit)
				//new ModifierLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectBest<SolutionKS,InfoKS,UnpackedNH>(false,new MaxProfitPerPound()), new Insert()), //packs fitting piece increasing Profit per Pound most (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectBest<SolutionKS,InfoKS,UnpackedNH>(false,new MaxProfitPerPound()), new Insert()), //packs fitting piece increasing Profit per Pound most (till no more fit)
				new ModifierLocalSearchHeuristic<SolutionKP,InfoKP,UnpackedNH>(new SelectBest<SolutionKP,InfoKP,UnpackedNH>(false,new MinWeight()), new Insert()), //packs piece with the smallest weight (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,UnpackedNH>(new SelectBest<SolutionKS,InfoKS,UnpackedNH>(false,new MinWeight()), new Insert()), //packs piece with the smallest weight (till no more fit)
				new ModifierLocalSearchHeuristic<SolutionKP,InfoKP,SwapNH>(new SelectFirst<SolutionKP,InfoKP,SwapNH>(), new Swap()), //swaps a random cheaper with a fitting more expensive piece (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,SwapNH>(new SelectFirst<SolutionKS,InfoKS,SwapNH>(), new Swap()), //swaps a random cheaper with a fitting more expensive piece (till lo)
				new ModifierLocalSearchHeuristic<SolutionKP,InfoKP,SwapNH>(new SelectBest<SolutionKP,InfoKP,SwapNH>(false), new Swap()), //swap pieces increasing packed profit most (1 move)
				//new ModifierFullLocalSearchHeuristic<SolutionKS,InfoKS,SwapNH>(new SelectBest<SolutionKS,InfoKS,SwapNH>(false), new Swap()), //swap pieces increasing packed profit most (till lo)
				
		};
		return llhs_ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MutationHeuristic<SolutionKP, InfoKP>[] getMutationHeuristics() {
		MutationHeuristic<SolutionKP, InfoKP>[] llhs_mut = new MutationHeuristic[]{
				new ModifierMutationHeuristic<SolutionKP,InfoKP,SwapNH>(new SelectRandom<SolutionKP,InfoKP,SwapNH>(), new Swap()), //swap random pieces
				new ModifierMutationHeuristic<SolutionKP,InfoKP,SwapNH>(new SelectFirst<SolutionKP,InfoKP,SwapNH>(new MaxProfitPerPound()), new Swap()), //swap random pieces improving profit/pound
				//new ModifierMutationHeuristic<SolutionKS,InfoKS,SwapNH>(new SelectBest<SolutionKS,InfoKS,SwapNH>(false,new MaxProfitPerPound()), new Swap()), //swap pieces improving profit/pound most
				//new ModifierMutationHeuristic<SolutionKS,InfoKS,SwapNH>(new SelectFirst<SolutionKS,InfoKS,SwapNH>(new MinWeight()), new Swap()), //swap random pieces reducing weight
				//new ModifierMutationHeuristic<SolutionKS,InfoKS,SwapNH>(new SelectBest<SolutionKS,InfoKS,SwapNH>(false,new MinWeight()), new Swap()), //swap pieces to reduce weight most
				new ModifierMutationHeuristic<SolutionKP,InfoKP,KnapSackNH>(new SelectRandom<SolutionKP,InfoKP,KnapSackNH>(), new Remove()), //remove random pieces
				//new ModifierMutationHeuristic<SolutionKS,InfoKS,KnapSackNH>(new SelectFirst<SolutionKS,InfoKS,KnapSackNH>(new MaxProfitPerPound()), new Remove()), //remove random pieces improving profit/pound
				//new ModifierMutationHeuristic<SolutionKS,InfoKS,KnapSackNH>(new SelectBest<SolutionKS,InfoKS,KnapSackNH>(false,new MaxProfitPerPound()), new Remove()), //remove pieces to improve profit/pound most
				new ModifierMutationHeuristic<SolutionKP,InfoKP,KnapSackNH>(new SelectBest<SolutionKP,InfoKP,KnapSackNH>(false), new Remove()), //remove least expensive pieces
				new ModifierMutationHeuristic<SolutionKP,InfoKP,KnapSackNH>(new SelectBest<SolutionKP,InfoKP,KnapSackNH>(false,new MinWeight()), new Remove()), //remove heaviest pieces
		};
		return llhs_mut;
	}
	
	//random destruction, greedy packing (greedily packing based on profit, profit/pound)

	@SuppressWarnings("unchecked")
	@Override
	public RuinRecreateHeuristic<SolutionKP, InfoKP>[] getRuinRecreateHeuristics() {
		RuinRecreateHeuristic<SolutionKP, InfoKP>[] llhs_rr = new RuinRecreateHeuristic[]{
				new PerturbativeRuinRecreate<SolutionKP,InfoKP,UnpackedNH,KnapSackNH>(new SelectBest<SolutionKP,InfoKP,UnpackedNH>(false), new Insert(), new SelectRandom<SolutionKP,InfoKP,KnapSackNH>(), new Remove()),
				new PerturbativeRuinRecreate<SolutionKP,InfoKP,UnpackedNH,KnapSackNH>(new SelectBest<SolutionKP,InfoKP,UnpackedNH>(true,new MaxProfitPerPound()), new Insert(), new SelectRandom<SolutionKP,InfoKP,KnapSackNH>(), new Remove()),
		};
		return llhs_rr;
	}

	@Override
	public String toString() {
		return "0-1 Knapsack Problem";
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrossoverHeuristic<SolutionKP, InfoKP>[] getCrossoverHeuristics() {
		CrossoverHeuristic<SolutionKP, InfoKP>[] llhs_xo = new CrossoverHeuristic[]{
				new IntersectCrossover(),
				new UnionCrossover(new SelectFirst<SolutionKP,InfoKP,UnionNH>()),
				new UnionCrossover(new SelectBest<SolutionKP,InfoKP,UnionNH>(false))
		};
		return llhs_xo;
	}
	
	public int solveExact(){
		// Input:  => instance
		// Number of distinct items (n)
		int n = instance.getNitems();
		// Values (stored in array v)
		int[] v = new int[n];
		for(int i = 0; i < n;i++){
			v[i] = instance.getProfit(i);
			if(v[i] < 1){
				System.out.println("error");
			}
		}
		// Weights (stored in array w)
		int[] w = new int[n];
		for(int i = 0; i < n;i++){
			w[i] = instance.getWeight(i);
			if(w[i] < 1){
				System.out.println("error");
			}
		}
		// Knapsack capacity (W)
		int W = instance.getCapacity();
		instance = null;
		// m
		/*
		int[][] m = new int[n+1][W+1];
		
		//for j from 0 to W do
		//  m[0, j] := 0
		//end for
		for(int j = 0; j < W; j++){
			m[0][j] = 0;
		}
		//for i from 1 to n do
		//	  for j from 0 to W do
		//	    if w[i] <= j then
		//	      m[i, j] := max(m[i-1, j], m[i-1, j-w[i]] + v[i])
		//	    else
		//	      m[i, j] := m[i-1, j]
		//	    end if
		//	  end for
		//	end for
		for(int i = 1; i <= n;i++){
			for(int j = 0; j <= W; j++){
				if(w[i-1] <= j){
					m[i][j] = Math.max(m[i-1][j], m[i-1][j-w[i-1]] + v[i-1]);
				}else{
					m[i][j] = m[i-1][j];
				}
			}
		}
		return m[n][W];
		*/
		int[] m = new int[W+1];
		for(int i = 1; i <= n;i++){
			for(int j = W; j >= 0; j--){
				if(w[i-1] <= j){
					m[j] = Math.max(m[j], m[j-w[i-1]] + v[i-1]);
				}
			}
		}
		return m[W];
	}

}
