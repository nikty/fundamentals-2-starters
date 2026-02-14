import tester.Tester;

// a json value
interface JSON {
  // to accept a given visitor
  <R> R accept(JSONVisitor<R> visitor);
}

// no value
class JSONBlank implements JSON {
  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// a number
class JSONNumber implements JSON {
  int number;

  JSONNumber(int number) {
    this.number = number;
  }

  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// a boolean
class JSONBool implements JSON {
  boolean bool;

  JSONBool(boolean bool) {
    this.bool = bool;
  }

  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// a string
class JSONString implements JSON {
  String str;

  JSONString(String str) {
    this.str = str;
  }

  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// a list of JSON values
class JSONList implements JSON {
  IList<JSON> values;

  JSONList(IList<JSON> values) {
    this.values = values;
  }

  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// a list of JSON pair
class JSONObject implements JSON {
  IList<Pair<String, JSON>> pairs;

  JSONObject(IList<Pair<String, JSON>> pairs) {
    this.pairs = pairs;
  }

  public <R> R accept(JSONVisitor<R> visitor) {
    return visitor.visit(this);
  }
}

// generic pairs
class Pair<X, Y> {
  X key;
  Y value;

  Pair(X key, Y value) {
    this.key = key;
    this.value = value;
  }
}

class PairGetValue implements IFunc<Pair<String, JSON>, JSON> {
  public JSON apply(Pair<String, JSON> pair) {
    return pair.value;
  }
}

// Implement the JSONVistor<T> interface, which is a IFunc<JSON, T>
// and follows the visitor pattern over JSONs.

interface JSONVisitor<T> extends IFunc<JSON, T> {
  T apply(JSON json);
  T visit(JSONBlank json);
  T visit(JSONNumber json);
  T visit(JSONBool json);
  T visit(JSONString json);
  T visit(JSONList json);
  T visit(JSONObject json);
}

// Define a JSONToNumber visitor, which coverts a JSON to its number value.
// Blanks are converted to 0, booleans 0 or 1 depending on if the value is
// false or true, strings their length, and numbers their value.

class JSONToNumber implements JSONVisitor<Integer> {
  public Integer apply(JSON json) {
    return json.accept(this);
  }

  public Integer visit(JSONBlank json) {
    return 0;
  }

  public Integer visit(JSONNumber json) {
    return json.number;
  }

  public Integer visit(JSONBool json) {
    if (json.bool) {
      return 1;
    } else {
      return 0;
    }
  }

  public Integer visit(JSONString json) {
    return json.str.length();
  }

  public Integer visit(JSONList json) {
    return json.values.map(this).foldr(new IntegerSum(), 0);
  }

  public Integer visit(JSONObject json) {
    return json.pairs.map(new PairGetValue()).map(this).foldr(new IntegerSum(), 0);
  }
}

// 
class JSONFind implements JSONVisitor<JSON> {
  String key;
  
  JSONFind(String key) {
    this.key = key;
  }
  
  public JSON apply(JSON json) {
    return json.accept(this);
  }
  
  public JSON visit(JSONBlank json) {
    return new JSONBlank();
  }
  
  public JSON visit(JSONNumber json) {
    return new JSONBlank();
  }
  
  public JSON visit(JSONString json) {
    return new JSONBlank();
  }
  
  public JSON visit(JSONBool json) {
    return new JSONBlank();
  }
  
  public JSON visit(JSONList json) {
    return new JSONBlank();
  }
  
  public JSON visit(JSONObject json) {
    return json.pairs.foldr(new FindValueByKey(this.key), new JSONBlank());
  }
}

class FindValueByKey implements IFunc2<Pair<String, JSON>, JSON, JSON> {
  String match;
  
  FindValueByKey(String match) {
    this.match = match;
  }
  
  public JSON apply(Pair<String, JSON> pair, JSON result) {
    if (result instanceof JSONBlank && pair.key.equals(this.match)) {
      return pair.value;
    }
    return result;
  }
}

// Map over a list of JSON and produce all of their numbers as a test.

class JsonExamples {
  JSON blank = new JSONBlank();
  JSON n1 = new JSONNumber(1); 
  JSON n2= new JSONNumber(2); 
  JSON n3 = new JSONNumber(3); 
  JSON n10 = new JSONNumber(10); 
  JSON n20 = new JSONNumber(20); 
  JSON n30 = new JSONNumber(30);
  JSON bool1 = new JSONBool(false);
  JSON bool2 = new JSONBool(true);
  JSON str1 = new JSONString("foo");
  JSON str2 = new JSONString("bar");



  IList<JSON> loj1 = new ConsList<>(blank,
      new ConsList<>(n1, new ConsList<>(n20, new ConsList<>(bool1,
          new ConsList<>(str1, new MtList<>())))));
  IList<Integer> loj1ExpectedToNumber = new ConsList<>(0,
      new ConsList<>(1, new ConsList<>(20, new ConsList<>(0,
          new ConsList<>(3, new MtList<>())))));

  JSON list1 = new JSONList(loj1);

  IList<JSON> loj2 = new ConsList<>(blank,
      new ConsList<>(list1, new MtList<>()));
  IList<Integer> loj2ExpectedToNumber = new ConsList<>(0,
      new ConsList<>(24, new MtList<>()));

  Pair<String, JSON> pairJohn = new Pair<>("john", new JSONString("John Doe"));
  Pair<String, JSON> pairJane = new Pair<>("jane", new JSONString("Jane Doe"));
  Pair<String, JSON> pairBool= new Pair<>("aBool", new JSONBool(true));
  Pair<String, JSON> pairList= new Pair<>("aList", list1);


  JSON obj1 = new JSONObject(new ConsList<>(pairJohn,
      new ConsList<>(pairJane, new ConsList<>(pairBool,
          new ConsList<>(pairList, new MtList<>())))));

  IList<JSON> loj3 = new ConsList<>(obj1, new MtList<>());
  IList<Integer> loj3ExpectedToNumber = new ConsList<>(8 + 8 + 1 + 24, new MtList<>());


  public boolean testJSONToNumber(Tester t) {
    return
        t.checkExpect(obj1.accept(new JSONToNumber()), 8 + 8 + 1 + 24)
        && t.checkExpect(blank.accept(new JSONToNumber()), 0)

        && t.checkExpect(loj1.map(new JSONToNumber()), loj1ExpectedToNumber)
        && t.checkExpect(loj2.map(new JSONToNumber()), loj2ExpectedToNumber)
        && t.checkExpect(loj3.map(new JSONToNumber()),
            loj3ExpectedToNumber);
  }
  
  public boolean testJSONFind(Tester t) {
    return t.checkExpect(obj1.accept(new JSONFind("john")),
        new JSONString("John Doe"))
        && t.checkExpect(obj1.accept(new JSONFind("jane")),
            new JSONString("Jane Doe"));
  }
}










