/*
  Variant A
  =========
  
  Suppose you are given a list of integers. Your task is to determine
  if this list contains:

  - A number that is even
  - A number that is positive and odd
  - A number between 5 and 10, inclusive

  The order in which you find numbers in the list satisfying these
  requirements does not matter. The list could have many more numbers
  than you need. Any number in the list may satisfy multiple
  requirements. For example, the list (in Racket notation) (list 6 5)
  satisfies all three requirements, while the list (list 4 3) does
  not.

  Design a method on lists of integers to check whether the list
  satisfies these criteria. Hint: what information do you need to
  propagate down the recursive calls as you process the list?

  Variant B
  =========

  Again, the list must contain numbers satisfying the three
  requirements above. Again, order does not matter. This time, a given
  number in the list may only be used to satisfy a single requirement;
  however, duplicate numbers are permitted to satisfy multiple
  requirements. So, (list 6 5) does not meet all the criteria for this
  variant, but (list 6 5 6) does.

  Design a new method on lists of integers to check for this stricter
  property. How does your design differ from Variant A?

  Variant C
  =========

  Again, the list must contain numbers satisfying the three
  requirements above. Again, order does not matter. Again, a given
  number in the list may only be used to satisfy a single
  requirement. This time, however, the list may not contain any
  extraneous numbers. So, (list 6 5 6) satisfies all our criteria for
  this variant, but (list 6 5 42 6) does not.

  Design a third method on lists of integers to check whether the list
  meets this new property.
*/

import tester.*;

// ...
