import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;

public class AcceptAllHH extends HyperHeuristic {
	
	public AcceptAllHH(long r) {
		super(r); 
	}
	
	public void solve(ProblemDomain problem) {
		int hs = problem.getNumberOfHeuristics();
		problem.initialiseSolution(0);
		double init_q = problem.getFunctionValue(0);
		System.out.println("AcceptAll 14 quality of init "+init_q);
		while (!hasTimeExpired()) {
			int v = rng.nextInt(hs);
			// try write information into csv format
			System.out.println("AcceptAll 18 heuristic "+v);
			System.out.println("AcceptAll 18 prev obj "+problem.getFunctionValue(0));
			problem.applyHeuristic(v, 0, 0);
			System.out.println("AcceptAll 20 curr obj "+problem.getFunctionValue(0));
			System.out.println("-----------------------");
		}
	}

	public String toString() {
		return "AA-HH";
	}

}
