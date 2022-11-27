package MAC;

import java.io.File;
import java.util.Stack;

import KP.InfoKP;
import MAC.SolutionMAC.InsertNH;
import MAC.SolutionMAC.RemoveNH;
import MAC.SolutionMAC.SwapNH;
import MAC.heuristics.MultiParentCrossover;
import MAC.heuristics.OnePointCrossover;
import MAC.heuristics.RandomizedNEH;
import MAC.modifiers.Insert;
import MAC.modifiers.Remove;
import MAC.modifiers.RemoveRadial;
import MAC.modifiers.Swap;
import MAC.modifiers.SwapNeighbours;
import MAC.modifiers.nhs.SwapNeighboursNH;
import MAC.parsers.CFGParserMAC;
import hfu.BasicProblemDomain;
import hfu.BenchmarkInstance;
import hfu.Parser;
import hfu.heuristics.ConstructionHeuristic;
import hfu.heuristics.CrossoverHeuristic;
import hfu.heuristics.LocalSearchHeuristic;
import hfu.heuristics.ModifierFullLocalSearchHeuristic;
import hfu.heuristics.ModifierLocalSearchHeuristic;
import hfu.heuristics.ModifierMutationHeuristic;
import hfu.heuristics.ModifierRuinRecreateHeuristic;
import hfu.heuristics.MutationHeuristic;
import hfu.heuristics.RuinRecreateHeuristic;
import hfu.heuristics.selector.SelectBest;
import hfu.heuristics.selector.SelectFirst;
import hfu.heuristics.selector.SelectRandom;

public class MaxCut extends BasicProblemDomain<SolutionMAC,InfoMAC>{

	public MaxCut(long seed) {
		super(seed);
	}

	@SuppressWarnings("unchecked")
	@Override
	public BenchmarkInstance<InfoMAC>[] getBenchmarkInstances() {
		Parser<InfoMAC> parser = new CFGParserMAC();
		String[] instanceNames = getFileName();
//		BenchmarkInstance<InfoMAC>[] benchmarks = new BenchmarkInstance[]{
//				new BenchmarkInstance<InfoMAC>("instances/mac/g3-8.txt",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g3-15.txt",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g14.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g15.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g16.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g22.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g34.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/g55.rud",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/pm3-8-50.txt",parser),
//				new BenchmarkInstance<InfoMAC>("instances/mac/pm3-15-50.txt",parser),
//		};
//		
		BenchmarkInstance<InfoMAC>[] benchmarks = new BenchmarkInstance[instanceNames.length];
		for (int i = 0; i < instanceNames.length; i ++) {
			System.out.println("current instance: "+instanceNames[i]);
			benchmarks[i] = new BenchmarkInstance<InfoMAC>("instances/mac/"+instanceNames[i],parser);
		}
		return benchmarks;
	}
	
	public static String[] getFileName() {
        String path = System.getProperty("user.dir") + "/instances/mac"; //path
        File f = new File(path);
        if (!f.exists()) {
            System.out.println(path + " not exists");
            return null;
        }
        else {
        	File fa[] = f.listFiles();
        	int len = 0;
            Stack<String> instanceStack = new Stack<>();
            for (int i = 0; i < fa.length; i ++) {
            	if (!fa[i].getName().equals(".DS_Store")) {
            		instanceStack.push(fa[i].getName());
            		len ++;
//            		System.out.println(fa[i].getName()+" is put into stack"+"\n");
            	}
            }
            String[] instanceNames = new String[len];
            for (int j = 0; j < instanceNames.length; j ++) {
//            	This assumes that instanceNames.length == length of stack
            	instanceNames[j] = instanceStack.pop();
//            	System.out.println(instanceNames[j]+" is pop out of the stack and into the result array");
            }
            return instanceNames;
        }
	}

	@Override
	public ConstructionHeuristic<SolutionMAC, InfoMAC> getConstructionHeuristic() {
		return new RandomizedNEH();
	}

	@SuppressWarnings("unchecked")
	@Override
	public LocalSearchHeuristic<SolutionMAC, InfoMAC>[] getLocalSearchHeuristics() {
		LocalSearchHeuristic<SolutionMAC, InfoMAC>[] llhs_ls = new LocalSearchHeuristic[]{
				new ModifierFullLocalSearchHeuristic<SolutionMAC,InfoMAC,SwapNH>(new SelectFirst<SolutionMAC,InfoMAC,SwapNH>(), new Swap(), true), //random order first-improvement hill-climbing (terminated in local optima or after x iterations)
				new ModifierFullLocalSearchHeuristic<SolutionMAC,InfoMAC,SwapNH>(new SelectBest<SolutionMAC,InfoMAC,SwapNH>(false), new Swap(), true), //best-improvement hill-climbing (terminated in local optima or after x iterations)
				new ModifierLocalSearchHeuristic<SolutionMAC,InfoMAC,SwapNeighboursNH>(new SelectBest<SolutionMAC,InfoMAC,SwapNeighboursNH>(false), new SwapNeighbours()), //best-improvement changing the partition of 2 neighbors
		};
		return llhs_ls;
	}

	@SuppressWarnings("unchecked")
	@Override
	public MutationHeuristic<SolutionMAC, InfoMAC>[] getMutationHeuristics() {
		MutationHeuristic<SolutionMAC, InfoMAC>[] llhs_mut = new MutationHeuristic[]{
				new ModifierMutationHeuristic<SolutionMAC,InfoMAC,SwapNH>(new SelectRandom<SolutionMAC,InfoMAC,SwapNH>(), new Swap()), //change the partition of x randomly selected vertices
				new ModifierMutationHeuristic<SolutionMAC,InfoMAC,SwapNeighboursNH>(new SelectRandom<SolutionMAC,InfoMAC,SwapNeighboursNH>(), new SwapNeighbours()), //change the partition of x randomly selected neighbor pairs
		};
		return llhs_mut;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RuinRecreateHeuristic<SolutionMAC, InfoMAC>[] getRuinRecreateHeuristics() {
		RuinRecreateHeuristic<SolutionMAC, InfoMAC>[] llhs_rr = new RuinRecreateHeuristic[]{
				new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectRandom<SolutionMAC,InfoMAC,InsertNH>(), new Insert(), new SelectRandom<SolutionMAC,InfoMAC,RemoveNH>(), new Remove()),
				//new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectRandom<SolutionMAC,InfoMAC,InsertNH>(), new Insert(), new SelectBest<SolutionMAC,InfoMAC,RemoveNH>(true), new Remove()),
				new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectBest<SolutionMAC,InfoMAC,InsertNH>(true), new Insert(), new SelectRandom<SolutionMAC,InfoMAC,RemoveNH>(), new Remove()),
				//new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectBest<SolutionMAC,InfoMAC,InsertNH>(true), new Insert(), new SelectBest<SolutionMAC,InfoMAC,RemoveNH>(true), new Remove()),
				//new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectRandom<SolutionMAC,InfoMAC,InsertNH>(), new Insert(), new SelectRandom<SolutionMAC,InfoMAC,RemoveNH>(), new RemoveRadial()),
				//new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectRandom<SolutionMAC,InfoMAC,InsertNH>(), new Insert(), new SelectBest<SolutionMAC,InfoMAC,RemoveNH>(true), new RemoveRadial()),
				new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectBest<SolutionMAC,InfoMAC,InsertNH>(true), new Insert(), new SelectRandom<SolutionMAC,InfoMAC,RemoveNH>(), new RemoveRadial()),
				//new ModifierRuinRecreateHeuristic<SolutionMAC,InfoMAC,InsertNH,RemoveNH>(new SelectBest<SolutionMAC,InfoMAC,InsertNH>(true), new Insert(), new SelectBest<SolutionMAC,InfoMAC,RemoveNH>(true), new RemoveRadial()),
		};
		return llhs_rr;
	}

	@Override
	public String toString() {
		return "Max-Cut Problem";
	}

	@SuppressWarnings("unchecked")
	@Override
	public CrossoverHeuristic<SolutionMAC, InfoMAC>[] getCrossoverHeuristics() {
		CrossoverHeuristic<SolutionMAC, InfoMAC>[] llhs_xo = new CrossoverHeuristic[]{
				new OnePointCrossover(),
				new MultiParentCrossover()
		};
		return llhs_xo;
	}

}
