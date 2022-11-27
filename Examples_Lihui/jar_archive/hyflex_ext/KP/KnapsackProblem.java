// 
// Decompiled by Procyon v0.5.36
// 


import KP.heuristics.UnionCrossover;
import KP.heuristics.IntersectCrossover;
import hfu.heuristics.CrossoverHeuristic;
import KP.heuristics.PerturbativeRuinRecreate;
import hfu.heuristics.RuinRecreateHeuristic;
import KP.modifiers.Remove;
import hfu.heuristics.ModifierMutationHeuristic;
import hfu.heuristics.selector.SelectRandom;
import hfu.heuristics.MutationHeuristic;
import KP.modifiers.Swap;
import KP.evalfs.MinWeight;
import hfu.heuristics.ModifierFullLocalSearchHeuristic;
import hfu.heuristics.selector.eval.EvaluationFunction;
import KP.evalfs.MaxProfitPerPound;
import hfu.heuristics.selector.SelectBest;
import hfu.heuristics.modifiers.PerturbativeModifier;
import hfu.heuristics.selector.Selector;
import hfu.heuristics.ModifierLocalSearchHeuristic;
import KP.modifiers.Insert;
import hfu.heuristics.selector.SelectFirst;
import hfu.heuristics.LocalSearchHeuristic;
import KP.heuristics.ConstructEmpty;
import hfu.heuristics.ConstructionHeuristic;
import hfu.Parser;
import KP.parsers.CFGParserKP;
import hfu.BenchmarkInstance;
import hfu.BasicProblemDomain;

public class KnapsackProblem extends BasicProblemDomain<SolutionKP, InfoKP>
{
    public KnapsackProblem(final long seed) {
        super(seed);
    }
    
    public BenchmarkInstance<InfoKP>[] getBenchmarkInstances() {
        final Parser<InfoKP> parser = (Parser<InfoKP>)new CFGParserKP();
        final BenchmarkInstance[] benchmarks = { new BenchmarkInstance("instances/kp/0.kp", (Parser)parser), new BenchmarkInstance("instances/kp/1.kp", (Parser)parser), new BenchmarkInstance("instances/kp/2.kp", (Parser)parser), new BenchmarkInstance("instances/kp/3.kp", (Parser)parser), new BenchmarkInstance("instances/kp/4.kp", (Parser)parser), new BenchmarkInstance("instances/kp/5.kp", (Parser)parser), new BenchmarkInstance("instances/kp/6.kp", (Parser)parser), new BenchmarkInstance("instances/kp/7.kp", (Parser)parser), new BenchmarkInstance("instances/kp/8.kp", (Parser)parser), new BenchmarkInstance("instances/kp/9.kp", (Parser)parser) };
        return (BenchmarkInstance<InfoKP>[])benchmarks;
    }
    
    public ConstructionHeuristic<SolutionKP, InfoKP> getConstructionHeuristic() {
        return (ConstructionHeuristic<SolutionKP, InfoKP>)new ConstructEmpty();
    }
    
    public LocalSearchHeuristic<SolutionKP, InfoKP>[] getLocalSearchHeuristics() {
        final LocalSearchHeuristic[] llhs_ls = { (LocalSearchHeuristic)new ModifierLocalSearchHeuristic((Selector)new SelectFirst(), (PerturbativeModifier)new Insert()), (LocalSearchHeuristic)new ModifierLocalSearchHeuristic((Selector)new SelectBest(false), (PerturbativeModifier)new Insert()), (LocalSearchHeuristic)new ModifierFullLocalSearchHeuristic((Selector)new SelectFirst((EvaluationFunction)new MaxProfitPerPound()), (PerturbativeModifier)new Insert()), (LocalSearchHeuristic)new ModifierLocalSearchHeuristic((Selector)new SelectBest(false, (EvaluationFunction)new MinWeight()), (PerturbativeModifier)new Insert()), (LocalSearchHeuristic)new ModifierLocalSearchHeuristic((Selector)new SelectFirst(), (PerturbativeModifier)new Swap()), (LocalSearchHeuristic)new ModifierLocalSearchHeuristic((Selector)new SelectBest(false), (PerturbativeModifier)new Swap()) };
        return (LocalSearchHeuristic<SolutionKP, InfoKP>[])llhs_ls;
    }
    
    public MutationHeuristic<SolutionKP, InfoKP>[] getMutationHeuristics() {
        final MutationHeuristic[] llhs_mut = { (MutationHeuristic)new ModifierMutationHeuristic((Selector)new SelectRandom(), (PerturbativeModifier)new Swap()), (MutationHeuristic)new ModifierMutationHeuristic((Selector)new SelectFirst((EvaluationFunction)new MaxProfitPerPound()), (PerturbativeModifier)new Swap()), (MutationHeuristic)new ModifierMutationHeuristic((Selector)new SelectRandom(), (PerturbativeModifier)new Remove()), (MutationHeuristic)new ModifierMutationHeuristic((Selector)new SelectBest(false), (PerturbativeModifier)new Remove()), (MutationHeuristic)new ModifierMutationHeuristic((Selector)new SelectBest(false, (EvaluationFunction)new MinWeight()), (PerturbativeModifier)new Remove()) };
        return (MutationHeuristic<SolutionKP, InfoKP>[])llhs_mut;
    }
    
    public RuinRecreateHeuristic<SolutionKP, InfoKP>[] getRuinRecreateHeuristics() {
        final RuinRecreateHeuristic[] llhs_rr = { (RuinRecreateHeuristic)new PerturbativeRuinRecreate((Selector)new SelectBest(false), (PerturbativeModifier)new Insert(), (Selector)new SelectRandom(), (PerturbativeModifier)new Remove()), (RuinRecreateHeuristic)new PerturbativeRuinRecreate((Selector)new SelectBest(true, (EvaluationFunction)new MaxProfitPerPound()), (PerturbativeModifier)new Insert(), (Selector)new SelectRandom(), (PerturbativeModifier)new Remove()) };
        return (RuinRecreateHeuristic<SolutionKP, InfoKP>[])llhs_rr;
    }
    
    public String toString() {
        return "0-1 Knapsack Problem";
    }
    
    public CrossoverHeuristic<SolutionKP, InfoKP>[] getCrossoverHeuristics() {
        final CrossoverHeuristic[] llhs_xo = { (CrossoverHeuristic)new IntersectCrossover(), (CrossoverHeuristic)new UnionCrossover((Selector)new SelectFirst()), (CrossoverHeuristic)new UnionCrossover((Selector)new SelectBest(false)) };
        return (CrossoverHeuristic<SolutionKP, InfoKP>[])llhs_xo;
    }
    
    public int solveExact() {
        final int n = ((InfoKP)this.instance).getNitems();
        final int[] v = new int[n];
        for (int i = 0; i < n; ++i) {
            v[i] = ((InfoKP)this.instance).getProfit(i);
            if (v[i] < 1) {
                System.out.println("error");
            }
        }
        final int[] w = new int[n];
        for (int j = 0; j < n; ++j) {
            w[j] = ((InfoKP)this.instance).getWeight(j);
            if (w[j] < 1) {
                System.out.println("error");
            }
        }
        final int W = ((InfoKP)this.instance).getCapacity();
        this.instance = null;
        final int[] m = new int[W + 1];
        for (int k = 1; k <= n; ++k) {
            for (int l = W; l >= 0; --l) {
                if (w[k - 1] <= l) {
                    m[l] = Math.max(m[l], m[l - w[k - 1]] + v[k - 1]);
                }
            }
        }
        return m[W];
    }
}
