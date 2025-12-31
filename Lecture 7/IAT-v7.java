import tester.*;

// interp. an ancestor tree
interface IAT {
    // To compute the number of known ancestors of this ancestor tree
    // (excluding this ancestor tree itself)
    int count();

    // To compute the number of known ancestors of this ancestor tree
    // (*including* this ancestor tree itself)
    int countHelp();

    // To compute how many ancestors of this ancestor tree (excluding this
    // ancestor tree itself) are women older than 40 (in the current year)?
    // ASSUME: current year is 2015
    int femaleAncOver40();

    // To compute how many ancestors of this Person (*including* this Person itself)
    // are women older than 40 (in the current year).
    int femaleAncOver40Help();
    
    // Count how many generations (including this IAT’s generation) are completely known
    int numTotalGens();

    // Count how many generations (including this IAT’s generation) are at least partially known. 
    int numPartialGens();
    
    // To compute whether this ancestor tree is well-formed: are all known
    // people younger than their parents?
    boolean wellFormed();

    // To determine if this ancestry tree is older than the given year tree
    // and its parents are well-formed
    // ASSUME: the given tree is descendant of this tree
    boolean wellFormedHelp(int childYob);

    // To return the younger of this ancestor tree and the given ancestor tree
    IAT youngerIAT(IAT other);

    // Helper method for youngerIAT;
    // Return the younger of this ancestor tree and the given ancestor tree;
    IAT youngerIATHelper(IAT other, int otherYob);

    // To compute the youngest parent of this ancestry tree
    IAT youngestParent();

    // To compute the youngest grandparent of this ancestry tree
    IAT youngestGrandparent();
 
    // Return youngest ancestor in the given generation (generations start from 0)
    IAT youngestAncAtGen(int gen);

    // To compute the names of all the known ancestors in this ancestor tree
    // (including this ancestor tree itself)
    ILoString ancNames();
}

// interp. unknown member of an ancestor tree
class Unknown implements IAT {
    Unknown() { }

    public int count() {
        return 0;
    }

    public int countHelp() {
        return 0;
    }

    public int femaleAncOver40() {
        return 0;
    }

    public int femaleAncOver40Help() {
        return 0;
    }

    public int numTotalGens() {
        return 0;
    }

    public int numPartialGens() {
        return 0;
    }

    public boolean wellFormed() {
        return true;
    }

    public boolean wellFormedHelp(int childYob) {
        return true;
    }

    public IAT youngerIAT(IAT other) {
        return other;
    }

    public IAT youngerIATHelper(IAT other, int otherYob) {
        return other;
    }

    public IAT youngestParent() {
        return this;
    }

    public IAT youngestGrandparent() {
        return this;
    }

    public IAT youngestAncAtGen(int gen) {
        return this;
    }

    public ILoString ancNames() {
        return new MtLoString();
    }
}

// interp. a person with the person's ancestor trees
class Person implements IAT {
    String name;
    int yob;
    boolean isMale;
    IAT mom;
    IAT dad;
    
    Person(String name, int yob, boolean isMale, IAT mom, IAT dad) {
        this.name = name;
        this.yob = yob;
        this.isMale = isMale;
        this.mom = mom;
        this.dad = dad;
    }

    public int count() {
        return this.mom.countHelp() + this.dad.countHelp();
    }

    public int countHelp() {
        return 1 + this.mom.countHelp() + this.dad.countHelp();
    }

    public int femaleAncOver40() {
        return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
    }

    public int femaleAncOver40Help() {
        if (!this.isMale && (2015 - this.yob) > 40) {
            return 1 + this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
        } else {
            return this.mom.femaleAncOver40Help() + this.dad.femaleAncOver40Help();
        }
    }

    public int numTotalGens() {
        if (this.mom.numTotalGens() + this.dad.numTotalGens() == 0) {
            return 1;
        } else {
            return 1 + Math.min(this.mom.numTotalGens(), this.dad.numTotalGens());
        }
    }

    public int numPartialGens() {
        if (this.mom.numPartialGens() + this.dad.numPartialGens() == 0) {
            return 1;
        } else {
            return 1 + Math.max(this.mom.numPartialGens(), this.dad.numPartialGens());
        }
    }

    public boolean wellFormed() {
        return this.mom.wellFormedHelp(this.yob)
            && this.dad.wellFormedHelp(this.yob);
    }

    public boolean wellFormedHelp(int childYob) {
        return this.yob <= childYob
            && this.mom.wellFormedHelp(this.yob)
            && this.dad.wellFormedHelp(this.yob);
    }

    // IAT youngerIAT(IAT other) {
    //   ... this.mmmm()
    //   ... other.mmm()
    // }
    public IAT youngerIAT(IAT other) {
        return other.youngerIATHelper(this, this.yob);
    }

    public IAT youngerIATHelper(IAT other, int otherYob) {
        if (this.yob > otherYob) {
            return this;
        } else {
            return other;
        }
    }

    // ... this.mom.youngerIAT()
    // ... this.dad
    public IAT youngestParent() {
        return this.mom.youngerIAT(this.dad);
    }

    public IAT youngestGrandparent() {
        return this.mom.youngestParent().youngerIAT(this.dad.youngestParent());
    }

    // ... this.mom.youngestAncAtGen().youngerIAT()
    // ... this.dad.youngestAncAtGen()
    public IAT youngestAncAtGen(int gen) {
        //return new Unknown();
        if (gen == 0) {
            return this;
        } else { 
            return this.mom.youngestAncAtGen(gen - 1).youngerIAT(this.dad.youngestAncAtGen(gen - 1));
        }
    }

    public ILoString ancNames() {
        //return new MtLoString();
        return new ConsLoString(this.name, 
                                this.dad.ancNames().append(this.mom.ancNames()));
    }
}

// interp. a list of strings
interface ILoString {

    // Return list of strings with given list of strings appended before the given list
    ILoString append(ILoString lor);
}

class ConsLoString implements ILoString {
    String first;
    ILoString rest;
    ConsLoString(String first, ILoString rest) {
        this.first = first;
        this.rest = rest;
    }

    public ILoString append(ILoString lor) {
        return new ConsLoString(this.first,
                                this.rest.append(lor));
    }
        
}

class MtLoString implements ILoString {
    MtLoString() { }

    public ILoString append(ILoString lor) {
        return lor;
    }
}

class ExamplesIAT {
    IAT enid = new Person("Enid", 1904, false, new Unknown(), new Unknown());
    IAT edward = new Person("Edward", 1902, true, new Unknown(), new Unknown());
    IAT emma = new Person("Emma", 1906, false, new Unknown(), new Unknown());
    IAT eustace = new Person("Eustace", 1907, true, new Unknown(), new Unknown());
 
    IAT david = new Person("David", 1925, true, new Unknown(), this.edward);
    IAT daisy = new Person("Daisy", 1927, false, new Unknown(), new Unknown());
    IAT dana = new Person("Dana", 1933, false, new Unknown(), new Unknown());
    IAT darcy = new Person("Darcy", 1930, false, this.emma, this.eustace);
    IAT darren = new Person("Darren", 1935, true, this.enid, new Unknown());
    IAT dixon = new Person("Dixon", 1936, true, new Unknown(), new Unknown());
 
    IAT clyde = new Person("Clyde", 1955, true, this.daisy, this.david);
    IAT candace = new Person("Candace", 1960, false, this.dana, this.darren);
    IAT cameron = new Person("Cameron", 1959, true, new Unknown(), this.dixon);
    IAT claire = new Person("Claire", 1956, false, this.darcy, new Unknown());
 
    IAT bill = new Person("Bill", 1980, true, this.candace, this.clyde);
    IAT bree = new Person("Bree", 1981, false, this.claire, this.cameron);
 
    IAT andrew = new Person("Andrew", 2001, true, this.bree, this.bill);
 
    boolean testCount(Tester t) {
        return
            t.checkExpect(this.andrew.count(), 16) &&
            t.checkExpect(this.david.count(), 1) &&
            t.checkExpect(this.enid.count(), 0) &&
            t.checkExpect(new Unknown().count(), 0);
    }

    boolean testFemaleAncOver40(Tester t) {
        return
            t.checkExpect(this.andrew.femaleAncOver40(), 7) &&
            t.checkExpect(this.bree.femaleAncOver40(), 3) &&
            t.checkExpect(this.darcy.femaleAncOver40(), 1) &&
            t.checkExpect(this.enid.femaleAncOver40(), 0) &&
            t.checkExpect(new Unknown().femaleAncOver40(), 0);
    }

    boolean testNumGens(Tester t) {
        return
            t.checkExpect(new Unknown().numTotalGens(), 0)
            && t.checkExpect(this.enid.numTotalGens(), 1)
            && t.checkExpect(this.andrew.numTotalGens(), 3)
            && t.checkExpect(new Unknown().numPartialGens(), 0)
            && t.checkExpect(this.enid.numPartialGens(), 1)
            && t.checkExpect(this.andrew.numPartialGens(), 5);
    }

    boolean testWellFormed(Tester t) {
        return
            t.checkExpect(this.andrew.wellFormed(), true) &&
            t.checkExpect(new Unknown().wellFormed(), true) &&
            t.checkExpect(
                          new Person("Zane", 2000, true, this.andrew, this.bree).wellFormed(),
                          false);
    }

    boolean testIATYoungerIAT(Tester t) {
        return t.checkExpect(bill.youngerIAT(bree), bree)
            && t.checkExpect(bree.youngerIAT(bill), bree)
            && t.checkExpect(candace.youngerIAT(cameron), candace);
    }

    boolean testIATYoungestParent(Tester t) {
        return t.checkExpect(andrew.youngestParent(), bree)
            && t.checkExpect(bree.youngestParent(), cameron);
    }


    boolean testYoungestAncAtGen(Tester t) {
        return t.checkExpect(new Unknown().youngestAncAtGen(0), new Unknown())
            && t.checkExpect(new Unknown().youngestAncAtGen(10), new Unknown())
            && t.checkExpect(edward.youngestAncAtGen(0), edward)
            && t.checkExpect(edward.youngestAncAtGen(1), new Unknown())
            && t.checkExpect(andrew.youngestAncAtGen(0), andrew)
            && t.checkExpect(andrew.youngestAncAtGen(1), bree)
            && t.checkExpect(andrew.youngestAncAtGen(2), candace)
            && t.checkExpect(andrew.youngestAncAtGen(3), dixon);
    }

    boolean testYoungestGrandparent(Tester t) {
        return
            t.checkExpect(this.emma.youngestGrandparent(), new Unknown()) &&
            t.checkExpect(this.david.youngestGrandparent(), new Unknown()) &&
            t.checkExpect(this.claire.youngestGrandparent(), this.eustace) &&
            t.checkExpect(this.bree.youngestGrandparent(), this.dixon) &&
            t.checkExpect(this.andrew.youngestGrandparent(), this.candace) &&
            t.checkExpect(new Unknown().youngestGrandparent(), new Unknown());
    }

    boolean testAncNames(Tester t) {
        return
            t.checkExpect(this.david.ancNames(),
                          new ConsLoString("David",
                                           new ConsLoString("Edward", new MtLoString())))
            && t.checkExpect(this.eustace.ancNames(),
                             new ConsLoString("Eustace", new MtLoString()))
            && t.checkExpect(new Unknown().ancNames(), new MtLoString())
            && t.checkExpect(this.clyde.ancNames(),
                             new ConsLoString
                             ("Clyde", new ConsLoString
                              ("David", new ConsLoString
                               ("Edward", new ConsLoString
                                ("Daisy", new MtLoString())))));                                              
    }

    ILoString s1 = new ConsLoString("a", new MtLoString());
    ILoString s2 = new ConsLoString("a",
                                    new ConsLoString
                                    ("b", new MtLoString()));
    ILoString s3 = new ConsLoString("a",
                                    new ConsLoString
                                    ("b", new ConsLoString
                                     ("c", new MtLoString())));

                                    
    boolean testILoStringAppend(Tester t) {
        return t.checkExpect(new MtLoString()
                             .append(s1),
                             s1)
            && t.checkExpect(s1.append(s1),
                             new ConsLoString
                             ("a", new ConsLoString
                              ("a", new MtLoString())));
    }
}
