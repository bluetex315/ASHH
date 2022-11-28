//import java.util.Date;
//import SAT.SAT;
import travelingSalesmanProblem.TSP;
import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;


public class ExampleRun_AcceptAll {

	public static void main(String[] args) {
//		long seed = new Date().getTime();
		long seed = 42;
		
		//algorithm used (AcceptAllHH with default parameter settings)
		HyperHeuristic algo = new AcceptAllHH(seed);
		
		//benchmark instance solved (4th instance in the Maximum Satisfiability problem domain)
		ProblemDomain problem = new TSP(seed);
		int instance = 3;
		problem.loadInstance(instance);
		
		//time we're allowed to optimize
		long t_allowed = 6000;
		algo.setTimeLimit(t_allowed);

		algo.loadProblemDomain(problem);
		
		//start optimizing
		System.out.println("Testing "+algo+" for "+t_allowed+" ms on "+problem.getClass().getSimpleName()+"["+instance+"]...");
		algo.run();

		//print out quality of best solution found
		System.out.println(algo.getBestSolutionValue());
	}

}
