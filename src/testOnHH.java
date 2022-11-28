import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

// if import AbstractClasses then need to check `extend`
//import AbstractClasses.HyperHeuristic;
//import AbstractClasses.ProblemDomain;

import be.kuleuven.kahosl.acceptance.AcceptanceCriterionType;
import be.kuleuven.kahosl.selection.SelectionMethodType;

import SAT.SAT;
import BinPacking.BinPacking;
import FlowShop.FlowShop;
import VRP.VRP;
import travelingSalesmanProblem.TSP;

import PersonnelScheduling.PersonnelScheduling;

import KP.KnapsackProblem;
import QAP.QAP;
import MAC.MaxCut;

import Hyper_Heuristics.aco.ACO_HH;
import Hyper_Heuristics.antq.Ant_Q;
import Hyper_Heuristics.eph.eph.EPH;
import Hyper_Heuristics.genhive.csput.CSPUTGeneticHiveHyperHeuristic;
import Hyper_Heuristics.giss.acuna.GISS;
import Hyper_Heuristics.haea.gomez.HaeaHH;
import Hyper_Heuristics.haha.lehrbaum.LehrbaumHAHA;
import Hyper_Heuristics.isea.kubalik.EvoCOPHyperHeuristic;
import Hyper_Heuristics.ksatshh.sim.SimSATS_HH;
import Hyper_Heuristics.leangihh.leangihh.LeanGIHH;
import Hyper_Heuristics.mchhs.mcclymont.McClymontMCHHS;
import Hyper_Heuristics.ml.laroseml.LaroseML;
import Hyper_Heuristics.nahh.iridia.MyHyperHeuristic;
import Hyper_Heuristics.phunter.pearlhunter.PearlHunter;
import Hyper_Heuristics.sails.jiang.sa_ilsHyperHeuristic;
import Hyper_Heuristics.selfsearch.elomari.elomariSS;
import Hyper_Heuristics.vnstw.hsiao.HsiaoCHeSCHyperheuristic;
import Hyper_Heuristics.xcj.shafi.ShafiXCJ;
import Hyper_Heuristics.avegnep.urli.*;
import Hyper_Heuristics.bader.bader.Clean;
import Hyper_Heuristics.dynils.johnston.JohnstonDynamicILS;


/**
 * This class shows how to run a selected hyper-heuristic on a selected problem domain.
 * It shows the minimum that must be done to test a hyper heuristic on a problem domain, and it is 
 * intended to be read before the ExampleRun2 class, which provides an example of a more complex set-up
 */
public class testOnHH {

	private static long seed = 1234;

//	private static long totalExecutionTime = 600;
//	private static int numberOfTrails = 10;
	
	public static void main(String[] args) throws FileNotFoundException {
		
		ProblemDomain problem = null;
		HyperHeuristic hyperh = null;
		
        String HHName = args[0];
        String problemName = args[1];
        int instanceNumber = Integer.parseInt(args[2]);
        int trialNumber = Integer.parseInt(args[3]);
//		long totalExecutionTime = 276000;//553000
        long totalExecutionTime = Integer.parseInt(args[4]); // in ms
//		long totalExecutionTime = 6000; // in ms
		
		String resultFileName;
		long tempSeed = seed * (trialNumber + 1);
		
		Date today = new Date(); 
//		Format dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
//		WriteInfo.resultSubFolderName = dateFormatter.format(today);
		problem = null;
		hyperh = null;
		ArrayList<String> results = new ArrayList<String>();
		CSVWriter_1 writer = new CSVWriter_1();
		resultFileName = problemName.toString().replace(" ", "")+"__"+
				HHName.toString().replace(" ", "")+"__"+
					"INST"+instanceNumber+"__"+
						"TIME"+totalExecutionTime+"__"+
							today;
		System.out.println(resultFileName);
//System.out.println(" @@ "+resultFileName);
		for (int ins = 0; ins < instanceNumber; ins ++) {
			
			if (problemName.equals("SAT")){
				problem = new SAT(tempSeed);
			}else if (problemName.equals("Binpacking")){
				problem = new BinPacking(tempSeed);
			}else if (problemName.equals("FlowShop")){
				problem = new FlowShop(tempSeed);
			}else if (problemName.equals("PersonnelScheduling")){
				problem = new PersonnelScheduling(tempSeed);
			}else if (problemName.equals("TSP")){
				problem = new TSP(tempSeed);
			}else if (problemName.equals("VRP")){
				problem = new VRP(tempSeed);
			}else if (problemName.equals("KnapsackProblem")){
				problem = new KnapsackProblem(tempSeed);
			}else if (problemName.equals("QAP")){
				problem = new QAP(tempSeed);
			}else if (problemName.equals("MaxCut")){
				problem = new MaxCut(tempSeed);
			}
			
			// again check the binpacking definition (offline vs online)
			if (HHName.equals("AcceptAllHH")) {
				hyperh = new AcceptAllHH(seed);
			}else if (HHName.equals("FairShareILS")){
				hyperh = new FairShareILS(seed);
			}else if (HHName.equals("GIHH")){
				SelectionMethodType selectionType = SelectionMethodType.AdaptiveLimitedLAassistedDHSMentorSTD;
				AcceptanceCriterionType acceptanceType = AcceptanceCriterionType.AdaptiveIterationLimitedListBasedTA;
				hyperh = new GIHH(seed, problem.getNumberOfHeuristics(), 
						totalExecutionTime, "GIHH_result", selectionType, acceptanceType);
			}else if (HHName.equals("EPH")){
				hyperh = new EPH(seed);			// there is some problem with EPH
			}else if (HHName.equals("ACO")) {
				hyperh = new ACO_HH(seed);
			}else if (HHName.equals("Ant-Q")) {
				hyperh = new Ant_Q(seed);
			}else if (HHName.equals("avegnep")) {
				hyperh = new Urli_AVEG_NeptuneHyperHeuristic(seed);
			}else if (HHName.equals("bader")) {
				hyperh = new Clean(seed);
			}else if (HHName.equals("DynILS")) {
				hyperh = new JohnstonDynamicILS(seed);
			}else if (HHName.equals("GenHive")) {
				hyperh = new CSPUTGeneticHiveHyperHeuristic(seed);
			}else if (HHName.equals("GISS")) {
				hyperh = new GISS(seed);
			}else if (HHName.equals("HAEA")) {
				hyperh = new HaeaHH(seed);
			}else if (HHName.equals("HAHA")) {
				hyperh = new LehrbaumHAHA(seed);
			}else if (HHName.equals("ISEA")) {
				hyperh = new EvoCOPHyperHeuristic(seed);
			}else if (HHName.equals("KSATS-HH")) {
				hyperh = new SimSATS_HH(seed);
			}else if (HHName.equals("LeanGIHH")) {
				hyperh = new LeanGIHH(seed);
			}else if (HHName.equals("MCHH-S")) {
				hyperh = new McClymontMCHHS(seed);
			}else if (HHName.equals("ML")) {
				hyperh = new LaroseML(seed);
			}else if (HHName.equals("NAHH")) {
				hyperh = new MyHyperHeuristic(seed);
			}else if (HHName.equals("PHUNTER")) {
				hyperh = new PearlHunter(seed);
			}else if (HHName.equals("SA-ILS")) {
				hyperh = new sa_ilsHyperHeuristic(seed);
			}else if (HHName.equals("SELF-SEARCH")) {
				hyperh = new elomariSS(seed);
			}else if (HHName.equals("VNS-TW")) {
				hyperh = new HsiaoCHeSCHyperheuristic(seed);
			}else if (HHName.equals("XCJ")) {
				hyperh = new ShafiXCJ(seed);
			}
			
			hyperh.setTimeLimit(totalExecutionTime);
		
			problem.loadInstance(ins);
//			System.out.println(problem.toString());
//			resultFileName = problem.toString().replace(" ", "")+"__"+
//								"HH:"+hyperh.toString().replace(" ", "")+"__"+
//									"INST:"+ins+"__";
//			System.out.println(" @@ "+resultFileName);
			
			hyperh.loadProblemDomain(problem);
			hyperh.run();
			
			String result = String.valueOf(hyperh.getBestSolutionValue());
			results.add(result);
			System.out.println("testOnHH 209 best solution: "+result);
			
			System.out.println(" ## FINISHED " + "INSTANCE --- "+ins);
			System.out.println("\r\n");
			
		}
		writer.CSVWrite(resultFileName, results);
		
	}
}
