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

  - Draw a class diagram for the classes that represent this data
  definition. (It is optional to submit your diagram. You can draw
  this on paper, or in ASCII art as a comment in your submitted file.)

  - Define Java classes that represent the definitions above.

  - Name your examples class ExamplesEmbroidery

  In the ExamplesEmbroidery class design the example of the following:

  - A piece named "Pillow Cover", with the following motifs:
  - - A "nature" group motif that has a
  - - - "bird" cross-stitch motif with 4.5 difficulty, a
  - - - "tree" chain-stitch motif with 3.0 difficulty, and a
  - - - "flowers" group motif with a
  - - - - "rose" cross-stitch motif with 5.0 difficulty, a
  - - - - "poppy" chain-stitch motif with 4.75 difficulty, and a
  - - - - "daisy" cross-stitch motif with 3.2 difficulty

  Name this example pillowCover Our test program will check that the
  field pillowCover in the class ExamplesEmbroidery represents this
  information. (You may name the other motifs, and all the items
  inside them, anything you like, though the names should be
  reasonably descriptive.)

  Design the method averageDifficulty that computes the average difficulty of all of the cross-stitch and chain-stitch motifs in an EmbroideryPiece.

  Hint: you will need some helper methods for this...

  Tricky! Design the method embroideryInfo that produces one String that has in it all names of cross-stitch and chain-stitch motifs in an EmbroideryPiece, their stitch types in parentheses, and each motif separated by comma and space. The String should end in a period.

  So for the Pillow Cover example above this String would be

  "Pillow Cover: bird (cross stitch), tree (chain stitch), rose (cross stitch), poppy (chain stitch), daisy (cross stitch)."

  Note: You can combine two Strings with a + operator, or by invoking the method concat: assuming both s1 and s2 are Strings, then both s1 + s2 and s1.concat(s2) do the same thing as the BSL expression (string-append s1 s2).

  Note: There is a comma and space between any two entries, but not after the last one.
*/
