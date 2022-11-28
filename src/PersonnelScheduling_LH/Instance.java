package PersonnelScheduling_LH;

public class Instance
{
    String name;
    int best;
    boolean knownOptimal;
    
    public Instance(final String name, final int bestKnown, final boolean knownOptimal) {
        this.name = name;
        this.best = bestKnown;
        this.knownOptimal = knownOptimal;
    }
    
    public String getName() {
        return this.name;
    }
    
    public boolean isBestKnownOptimal() {
        return this.knownOptimal;
    }
    
    public int getBestKnown() {
        return this.best;
    }
}