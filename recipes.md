# Design Recipes

## HTDC

Copied from the book.

###  Designing Methods for Classes (HTDC Section 10.3)

1. formulate a purpose statement and a method header;
2. illustrate the purpose statement with functional examples;
3. lay out what you know about the method’s argument(s) in a template;
   In the case of basic classes, the template consists of the
   parameters, this, and all the fields of the class written as
   this.fieldname. Typically we just write down the latter.
4. define the method; and
5. run the examples as tests.


### Designing Methods for Classes that Contain Classes (HTDC Section 11.2)

Every time we encountered a new form of data in How to Design
Programs, we checked our design recipe and made sure it still
worked. Usually we added a step here or a detail there. For the design
of classes, we need to do the same.

In principle, the design recipe from section 10.3 applies to the case
when a class contains (or refers to) another class. Now, however, the
wish list, which we already know from How to Design Programs, begins
to play a major role:

1. Formulate a method header and a purpose statement to the class to
   which you need to add functionality; also add method headers to
   those classes that you can reach from this class via containment
   arrows. You may or may not need those auxiliary methods but doing
   so, should remind you to delegate tasks when needed.
2. Illustrate the purpose statement with functional examples; you can
   reuse the data examples from the design steps for the class.
3. Create the template for the method. Remember that the template
   contains what you know about the method’s argument(s). This
   includes schematic method calls on those selector expressions that
   refer to another class in the diagram. In short, follow the
   (direction of the) containment arrow in the diagram.
4. Define the method. If the computation needs data from a contained
   class, you will need to develop an appropriate method for this
   other class, too. Formulate a purpose statement as you place the
   method on the wish list.
5. Work on your wish list, step by step, until it is empty.
6. Run the functional examples for those classes that don’t refer to
   other classes via containment arrows. Test the others afterwards.

### Designing Methods for Unions of Classes (HTDC Section 14.3)

It is time again to reflect on our design recipe for methods (see
sections 10.3 and 11.2). The five steps work well in principle, but
clearly the organization of some classes as a union suggests
additional checkpoints:

1. Formulate a purpose statement and a method signature to the
   interface; then add the method signature to each implementing
   class.
2. Illustrate the purpose statement with examples for each class of
   the union, i.e., for each variant.
3. Lay out what you know about the method’s argument(s) in each
   concrete method. This includes references to the fields of the
   class.  Remember from section 11.2 that if any of the variants
   contain an instance of another class, you should place appropriate
   schematic method calls to methods in these other classes in the
   template. The purpose of these schematic calls is to remind you of
   the wish list during the next step. To this end, also add a
   tentative method header to that other class.
4. Define the methods. The parameters and the expressions in the
   template represent the information that may contribute to the
   result. Each schematic method call means that you may need to
   design an auxiliary method in some other class. Use the wish list
   to keep track of these auxiliary goals.
5. Turn the examples into tests and run them.

As this section showed, you can switch steps 1 and 3. That is, you can
develop the templates just based on the structure of the class
hierarchy and the classes themselves. The example in this section also
showed how helpful the template-directed design is. Once you
understand the template, the rest of the design task is often
straightforward.

### Designing Classes for Circular Objects, Constructors (HTDC Section 23.1)

The examples in this section suggest that designing classes whose
instances refer to each other in a circular manner follows mostly the
same process as the design of classes in general:

1. Read the problem statement and determine how many classes of in-
   formation there are and how they are related. Make up examples of
   information and how people will use it. This should help you
   determine whether there is a need to access a piece of information
   I from some piece of information J and vice versa.
2. Develop the interface and class diagram.

   Doing so gives you a second chance to discover the existence of
   object cycles. Specifically, inspect the class diagram for cycles
   of containment arrows. Sometimes the cycle is direct and
   uninterrupted, as in figure 115. In this case, you are almost
   guaranteed to have pieces of data that are in a circular
   relationship. What you need to confirm is an intention that each
   instance points to some other object that points back. Other times,
   such as in figure 119, there is no direct cycle; instead you must
   traverse an inheritance arrow in the reverse direction to construct
   a cycle. In those cases, you may or may not have to construct
   circular collections of objects. Only examples from the first step
   can help here.
3. Translate the class diagram into class definitions naively. Don’t
   forget to add a purpose statement to each class as usual.
4. Now modify your class definitions so that they can accommodate
   circular relationships among objects, if needed.
   
   First, try to translate the examples of circular information into
   data.  If you can translate all the information examples into data
   (using the regular constructors), you don’t need circularity. If
   there are some that you can’t translate, you have confirmed the
   need for circularity.
   
   Second, determine the class C whose objects should come into
   existence first. In our running example at the beginning of the
   section, this class was Author. Then determine the field that would
   create a circular relationship; call it cf and let its type be CT.
   
   Third, initialize the cf field with an object of type CT that
   contains no fields of the type C (or its interface). In our running
   example, we used the empty list of books for this
   purpose. Sometimes we need to use the value of last resort: null.
   
   Fourth, define an add method that assigns new values to cf . For
   now, use the examples from this section as templates; they either
   replace the value of cf with a given value or create a list of
   values. You will soon learn how to design such methods in general.
   
   Last, modify the constructors of the classes that implement
   CT. They must call the add method with this so that the circular
   references can be established.
5. Lastly, translate the circular examples of information into data,
   using just the constructors in the enforced order. Check whether
   the circular references exist by looking at it.
