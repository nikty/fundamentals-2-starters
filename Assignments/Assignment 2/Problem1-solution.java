/*
  The following DrRacket data definition describes a simple piece of embroidery:

  ;;An EmbroideryPiece is a (make-embroidery-piece String Motif)
  (define-struct embroidery-piece (name motif))
     
  ;; Motif is one of
  ;; -- CrossStitchMotif
  ;; -- ChainStitchMotif
  ;; -- GroupMotif
     
  ;; A CrossStitchMotif is a (make-cross-stitch-motif String Double)
  (define-struct cross-stitch-motif (description difficulty))
  ;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult
     
  ;; A ChainStitchMotif is a (make-chain-stitch-motif String Double)
  (define-struct chain-stitch-motif (description difficulty))
  ;; interpretation: difficulty is a number between 0 and 5, with 5 being the most difficult
     
  ;; A GroupMotif is a (make-group-motif String [List-of Motif])
  (define-struct group-motif (description motifs))
*/

import tester.*;

// interp. represents embroidery piece
class EmbroideryPiece {
    String name;
    IMotif motif;

    EmbroideryPiece(String name, IMotif motif) {
        this.name = name;
        this.motif = motif;
    }

    // Produce the average difficulty of all of the cross-stich and
    // chain-stich motifs in this embroidery piece
    double averageDifficulty() {
        return this.motif.averageDifficulty();
    }

    // Produce string representation of this piece
    String embroideryInfo() {
        return this.name + ": " + this.motif.motifInfo() + ".";
    }
}

// interp. represents motifs. One of
// - CrossStitchMotif
// - ChainStitchMotif
// - GroupMotif
interface IMotif {

    // Return average difficulty of all of the cross-stich and
    // chain-stich motifs in this motif
    double averageDifficulty();

    // Return sum of difficulties of all the cross-stich and
    // chain-stich motif in this motif
    double totalDifficulty();

    // Return count of all the cross-stich and chain-stich motif in
    // this motif
    int motifCount();

    // Return string representation of this motif
    String motifInfo();
}

class CrossStitchMotif implements IMotif {
    String description;
    double difficulty;

    CrossStitchMotif(String description, double difficulty) {
        this.description = description;
        this.difficulty = difficulty;
    }

    public double averageDifficulty() {
        return this.difficulty;
    }

    public double totalDifficulty() {
        return this.difficulty;
    }

    public int motifCount() {
        return 1;
    }

    public String motifInfo() {
        return this.description + " (cross stitch)";
    }
}

class ChainStitchMotif implements IMotif {
    String description;
    double difficulty;

    ChainStitchMotif(String description, double difficulty) {
        this.description = description;
        this.difficulty = difficulty;
    }

    public double averageDifficulty() {
        return this.difficulty;
    }

    public double totalDifficulty() {
        return this.difficulty;
    }

    public int motifCount() {
        return 1;
    }

    public String motifInfo() {
        return this.description + " (chain stitch)";
    }
}
        
class GroupMotif implements IMotif {
    String description;
    ILoMotif motifs;

    GroupMotif(String description, ILoMotif motifs) {
        this.description = description;
        this.motifs = motifs;
    }

    public double averageDifficulty() {
        return this.motifs.averageDifficulty();
    }

    public double totalDifficulty() {
        return this.motifs.totalDifficulty();
    }

    public int motifCount() {
        return this.motifs.motifCount();
    }

    public String motifInfo() {
        return this.motifs.motifInfo();
    }
}

// interp. represents list of motifs. One of
// - MtMotif
// - ConsMotif
interface ILoMotif {
    // Return the average difficulty for cross-stich and chain-stich
    // motifs in the list
    double averageDifficulty();

    // Return sum of difficulties of cross-stich and chain-stich motifs
    // in this list of motifs
    double totalDifficulty();

    // Return count of cross-stich and chain-stich motifs in this list
    int motifCount();

    // Return string representation of motifs in the list
    String motifInfo();
}

class MtMotif implements ILoMotif {
    MtMotif() {}

    public double averageDifficulty() {
        return 0;
    }

    public double totalDifficulty() {
        return 0;
    }

    public int motifCount() {
        return 0;
    }

    public String motifInfo() {
        return "";
    }
}

class ConsMotif implements ILoMotif {
    IMotif first;
    ILoMotif rest;

    ConsMotif(IMotif first, ILoMotif rest) {
        this.first = first;
        this.rest = rest;
    }

    // !!!
    // public double averageDifficulty() {
    //   ... this.first.averageDifficulty()
    //   ... this.rest.averageDifficulty()
    // }

    public double averageDifficulty() {
        if (this.motifCount() == 0) {
            return 0;
        } else {
            return (this.first.totalDifficulty() + this.rest.totalDifficulty())
                / (this.first.motifCount() + this.rest.motifCount());
        }
    }

    public double totalDifficulty() {
        return this.first.totalDifficulty() + this.rest.totalDifficulty();
    }
    
    public int motifCount() {
        return this.first.motifCount() + this.rest.motifCount();
    }

    public String motifInfo() {
        if (this.first.motifInfo().equals("")) {
            if (this.rest.motifInfo().equals("")) {
                return "";
            } else {
                return this.rest.motifInfo();
            }
        } else {
            if (this.rest.motifInfo().equals("")) {
                return this.first.motifInfo();
            } else {
                return this.first.motifInfo() + ", " + this.rest.motifInfo();
            }
        }
    }
}
    
class ExamplesEmbroidery {
    ExamplesEmbroidery() {}

    IMotif bird = new CrossStitchMotif("bird", 4.5);
    IMotif tree = new ChainStitchMotif("tree", 3.0);
    IMotif rose = new CrossStitchMotif("rose", 5.0);
    IMotif poppy = new ChainStitchMotif("poppy", 4.75);
    IMotif daisy = new CrossStitchMotif("daisy", 3.2);
    IMotif flowers = new GroupMotif("flowers",
                                    new ConsMotif
                                    (rose, new ConsMotif
                                     (poppy, new ConsMotif
                                      (daisy, new MtMotif()))));
    IMotif nature = new GroupMotif("nature",
                                   new ConsMotif
                                   (bird, new ConsMotif
                                    (tree, new ConsMotif
                                     (flowers, new MtMotif()))));
    EmbroideryPiece pillowCover = new EmbroideryPiece("Pillow Cover",
                                                      nature);

    IMotif m1 = new GroupMotif("empty", new MtMotif());
    IMotif m2 = new GroupMotif("bird",
                               new ConsMotif(bird, new MtMotif()));
    IMotif m3 = new GroupMotif("Two Empty", new ConsMotif(
                                                          new GroupMotif("Nested empty 1", new MtMotif()),
                                                          new ConsMotif(
                                                                        new GroupMotif("Nested empty 2", new MtMotif()),
                                                                        new MtMotif())));

    EmbroideryPiece ep1 = new EmbroideryPiece("Empty", m1);
    EmbroideryPiece ep2 = new EmbroideryPiece("ep2", bird);
    EmbroideryPiece ep3 = new EmbroideryPiece("ep3", m2);
    EmbroideryPiece ep4 = new EmbroideryPiece("ep4", flowers);
    EmbroideryPiece ep5 = new EmbroideryPiece("Two Empty", m3);
                                                                                    

                                              

    boolean testEPAverageDifficulty(Tester t) {
        return t.checkInexact(ep1.averageDifficulty(), 0.0, .001)
            && t.checkInexact(ep5.averageDifficulty(), 0.0, .001)
            && t.checkInexact(ep2.averageDifficulty(), 4.5, .001)
            && t.checkInexact(ep3.averageDifficulty(), 4.5, .001)
            && t.checkInexact(ep4.averageDifficulty(), (5.0 + 4.75 + 3.2)/3, .001)
            && t.checkInexact(pillowCover.averageDifficulty(), (4.5 + 3.0 + 5.0 + 4.75 + 3.2)/5, .001);
    }

    boolean testILoMotifMotifCount(Tester t) {
        return t.checkExpect(m1.motifCount(), 0)
            && t.checkExpect(bird.motifCount(), 1)
            && t.checkExpect(m2.motifCount(), 1)
            && t.checkExpect(flowers.motifCount(), 3)
            && t.checkExpect(m3.motifCount(), 0)
            && t.checkExpect(pillowCover.motif.motifCount(), 5);
    }

    boolean testEPEmbroideryInfo(Tester t) {
        return t.checkExpect(ep1.embroideryInfo(), "Empty: .")
            && t.checkExpect(ep2.embroideryInfo(), "ep2: bird (cross stitch).")
            && t.checkExpect(ep3.embroideryInfo(), "ep3: bird (cross stitch).")
            && t.checkExpect(ep5.embroideryInfo(), "Two Empty: .")
            && t.checkExpect(pillowCover.embroideryInfo(),
                             "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch).");
    }

    boolean testIMotifMotifInfo(Tester t) {
        return t.checkExpect(m1.motifInfo(), "");
    }
}
