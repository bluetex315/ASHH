//import java.text.Format;
//import java.text.SimpleDateFormat;
//import java.util.Date;

//import be.kahosl.chesc.acceptance.AcceptanceCriterionType;
//import be.kahosl.chesc.problem.ProblemName;
//import be.kahosl.chesc.selection.SelectionMethodType;
//import be.kahosl.chesc.util.Print;
//import be.kahosl.chesc.util.Vars;
//import be.kahosl.chesc.util.WriteInfo;

import java.io.FileNotFoundException;

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

//import PersonnelScheduling.PersonnelScheduling;



/**
 * This class shows how to run a selected hyper-heuristic on a selected problem domain.
 * It shows the minimum that must be done to test a hyper heuristic on a problem domain, and it is 
 * intended to be read before the ExampleRun2 class, which provides an example of a more complex set-up
 */
public class TestOnInstanceNumber {

	private static long seed = 1234;

//  private static long totalExecTime = 600000;
//	private static int numberOfTrails = 10;
	
	
//	Feb. 12th
	// try with adriaesen HH
	private static HHName[] HHList = {
									  HHName.AcceptAllHH,
//									  HHName.FairShareILS,
//									  HHName.GIHH,
									  };
	
	private static ProblemName[] problemList = {  
												ProblemName.SAT,
												ProblemName.BinPacking,
	                                    		ProblemName.FlowShop,
												ProblemName.PersonelScheduling,
												ProblemName.TSP,
										        ProblemName.VRP,
												ProblemName.Knapsack,
										        ProblemName.QAP,
										        ProblemName.MaxCut,
    									        }; 	
	
//	private static int[] instanceNumber = {15}; // does not exist
//private static int[] instanceNumber = {10, 10, 10, 10, 10, 10, 10, 10, 10};
	
	
	public static void main(String[] args) throws FileNotFoundException {
//		["AcceptAllHH", "SAT", "10", "600"]
		// args contains the parameters of instance instance numbers
		int[] instanceNumber = new int[args.length];
		
		for (int i = 0; i < args.length; i ++) {
			instanceNumber[i] = Integer.parseInt(args[i]);
		}
//		long totalExecutionTime = 276000;//553000
		long totalExecutionTime = 6000; // in ms
		long numberOfTrails = 1;

//		Print.hyperheuristic = true;
//		Print.iterationBasedInfo = true;
		
		//Print.selection = true;
		
		String resultFileName;
		
//		Date today = new Date(); 
//		Format dateFormatter = new SimpleDateFormat("ddMMyyyyHHmmss");
		
//		WriteInfo.resultSubFolderName = dateFormatter.format(today);
	
		// run in different group and multiple times
		// linux shell command 
//		1. hh
//		2. check for system 
//		system to submit tasks, automatically assign
		for(int pr = 0; pr < problemList.length; pr++){
			for(int hh = 0; hh < HHList.length; hh++){
				for(int ins = 0; ins < instanceNumber[pr]; ins++){
	//				for(int hs = 0; hs < selectionList.length; hs++){
	//					for(int ac = 0; ac < acceptanceList.length; ac++){
							
					for(int tr = 0; tr < numberOfTrails; tr++){ //trials are handled inside HH
						resultFileName =  problemList[pr].toString().replace(" ", "")+"__"+
										  "HH:"+HHList[hh].toString().replace(" ", "")+"__"+
										  "INST:"+ins+"__"+
										  "TR:"+tr;
						
						System.out.println(" @@ "+resultFileName);
						
						// initialize problem domain and hyper-heuristic
						ProblemDomain problem = null;
						HyperHeuristic hyperh = null;
						
						// for every trail fix one seed by multiplying it with the trail number (Feb. 1st)
						long tempSeed = seed * (tr + 1);
									
						if (problemList[pr] == ProblemName.SAT){
							problem = new SAT(tempSeed);
						}else if (problemList[pr] == ProblemName.BinPacking){
							problem = new BinPacking(tempSeed);
						}else if (problemList[pr] == ProblemName.FlowShop){
							problem = new FlowShop(tempSeed);
						}else if (problemList[pr] == ProblemName.PersonelScheduling){
							problem = new PersonnelScheduling(tempSeed);
						}else if (problemList[pr] == ProblemName.TSP){
							problem = new TSP(tempSeed);
						}else if (problemList[pr] == ProblemName.VRP){
							problem = new VRP(tempSeed);
						}else if (problemList[pr] == ProblemName.Knapsack){
							problem = new KnapsackProblem(tempSeed);
						}else if (problemList[pr] == ProblemName.QAP){
							problem = new QAP(tempSeed);
						}else if (problemList[pr] == ProblemName.MaxCut){
							problem = new MaxCut(tempSeed);
						}
						
						problem.loadInstance(ins);
						
						if (HHList[hh] == HHName.AcceptAllHH) {
							hyperh = new AcceptAllHH(seed);
						}else if (HHList[hh] == HHName.FairShareILS){
							hyperh = new FairShareILS(seed);
						}else if (HHList[hh] == HHName.GIHH){
							// Feb 13, how should adjust the NumberOfHeuristis
							SelectionMethodType selectionType = SelectionMethodType.AdaptiveLimitedLAassistedDHSMentorSTD;
							AcceptanceCriterionType acceptanceType = AcceptanceCriterionType.AdaptiveIterationLimitedListBasedTA;
							
							hyperh = new GIHH(seed, problem.getNumberOfHeuristics(), 
									totalExecutionTime, resultFileName, selectionType, acceptanceType);
						}
						
//						System.out.println("\n\n\n >> HyperHeuristic "+problemList[pr]+" ins#"+ins+" tr#"+tr);
					
						hyperh.setTimeLimit(totalExecutionTime);
						hyperh.loadProblemDomain(problem);
						hyperh.run();
						String result = String.valueOf(hyperh.getBestSolutionValue());
						System.out.println("GeneralAcceptAll line157: "+result);
						
//						CSVWriter_1 writer = new CSVWriter_1();
//						writer.CSVWrite(resultFileName, result);
						System.out.println(" ## FINISHED");
						System.out.println("\r\n");
					}
				}
			}
		}
	}
}
