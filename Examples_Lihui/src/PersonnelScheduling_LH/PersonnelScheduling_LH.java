package PersonnelScheduling_LH;

import PersonnelScheduling.PersonnelScheduling;

public class PersonnelScheduling_LH extends PersonnelScheduling {
	
	private Instance[] newDataSets;

	public PersonnelScheduling_LH(final long seed) {
		super(seed);
		this.DataSets = new Instance[] 
				{ new Instance("BCV-3.46.1", 3280, false), new Instance("BCV-A.12.2", 1953, false), 
				  new Instance("ORTEC02", 270, true), new Instance("Ikegami-3Shift-DATA1", 2, true), 
			      new Instance("Ikegami-3Shift-DATA1.1", 3, true), new Instance("Ikegami-3Shift-DATA1.2", 3, true), 
				  new Instance("CHILD-A2", 1111, false), new Instance("ERRVH-A", 2197, false), 
				  new Instance("ERRVH-B", 6859, false), new Instance("MER-A", 9915, false), 
				  new Instance("BCV-A.12.1", 1294, false), new Instance("ORTEC01", 270, true) };
	}
	
	public void loadInstance(final int instanceID) {
        this.emptySolutions();
        if (instanceID < 0 || instanceID >= this.newDataSets.length) {
            System.out.println("Invalid instanceID: " + instanceID);
            return;
        }
        this.schedulingPeriodID = this.newDataSets[instanceID].getName();
        final RosterLoader rosterLoader = new RosterLoader();
        rosterLoader.VERBOSE = false;
        try {
            final InputStream fis = this.getClass().getClassLoader().getResourceAsStream("data/" + this.schedulingPeriodID + ".ros");
            if (fis != null) {
                this.bestRoster = rosterLoader.CreateEmptyRoster(fis);
            }
            else {
                String fName = ".\\data\\" + this.schedulingPeriodID + ".ros";
                File file = new File(fName);
                if (!file.exists()) {
                    fName = "..\\NRPJava\\data\\" + this.schedulingPeriodID + ".ros";
                    file = new File(fName);
                    if (!file.exists()) {
                        System.out.println("Unable to find data file.");
                    }
                }
                this.bestRoster = rosterLoader.CreateEmptyRoster(fName);
            }
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
        }
        if (this.bestRoster == null) {
            System.out.println("Problem loading instanceID: " + instanceID + " (" + this.schedulingPeriodID + ")");
            return;
        }
        this.bestRoster.RecalculateAllPenalties();
        this.shiftsDB = new TestShiftDB2(this.bestRoster);
        this.MakeInitialAssignments(this.bestRoster);
        this.bestPenalty = this.bestRoster.getTotalPenalty();
    }
}
	