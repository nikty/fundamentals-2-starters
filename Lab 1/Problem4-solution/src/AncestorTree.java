// to represent ancestry tree
interface IAT {
  
}

class Unknown implements IAT {
  Unknown() {}
}

class Person implements IAT {
  IAT father;
  IAT mother;
  
  Person(IAT father, IAT mother) {
    this.father = father;
    this.mother = mother;
  }
}

class Examples {
  IAT unknown = new Unknown();

  //-unknown
  //-\---unknown
  //--\-/
  //---d---unknown
  //----\--/
  //------c----uknown
  //-------\--/
  //--------b----unknown
  //---------\--/
  //----------a
  IAT d = new Person(unknown, unknown);
  IAT c = new Person(d, unknown);
  IAT b = new Person(c, unknown);
  IAT f = new Person(unknown, unknown);
  IAT a = new Person(b, f);
}