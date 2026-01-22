import tester.*;

class Task {
  int id; // id of the task
  ILoId prereqs; // list of prerequisite tasks

  Task(int id, ILoId prereqs) {
    this.id = id;
    this.prereqs = prereqs;
  }
  
  // Produce the list of tasks that one can complete from this task
  ILoT completable(ILoT todo, ILoT rsf, ILoT pending) {
    
    if (this.completableWith(rsf)) {
      
      rsf = new ConsLoT(this, rsf);
      
      // Get from pending
      ILoT resolved = pending.getResolved(rsf);
      
      return todo.completableHelper(
          resolved.append(rsf),
          pending.remove(resolved)
          );
    } else {
      return todo.completableHelper(
          rsf,
          //new ConsLoT(this, pending)
          pending.append(new ConsLoT(this, new MtLoT()))
          );
    }
  }
  
  boolean completableWith(ILoT lot) {
    return this.prereqs.resolve(lot).length() == 0;
  }
}

// interp. A list of prerequisites. One of:
// - MtLoId
// - ConsLoId
interface ILoId {
  ILoId resolve(ILoT lot);
  
  int length();
}

class MtLoId implements ILoId {
  MtLoId() {
  }
  
  
  public ILoId resolve(ILoT lot) {
    return this;
  }
  
  public int length() {
    return 0;
  }
  
}

class ConsLoId implements ILoId {
  int first;
  ILoId rest;

  ConsLoId(int first, ILoId rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoId resolve(ILoT lot) {
    if (lot.containsTaskId(this.first)) {
      return this.rest.resolve(lot);
    } else {
      return new ConsLoId(this.first, this.rest.resolve(lot));
    }
  }
  
  public int length() {
    return this.rest.length() + 1;
  }
  
}

//interp. A list of tasks. One of:
//- MtLoT
//- ConsLoT
interface ILoT {
  // Produce the list of the tasks one can complete from these tasks.
  // ASSUME: assume all task ids are unique.
  ILoT completable();
  
  // Append given list of tasks to this list
  ILoT append(ILoT other);
  
  // Return true if this list contains the task with the given id
  boolean containsTaskId(int id);
  
  ILoT completableHelper(ILoT rsf, ILoT pending);
  
  String print();
  
  // Given list of tasks, get those tasks which are completable
  ILoT getResolved(ILoT lot);
  
  ILoT remove(ILoT lot);
  
  boolean contains(Task t);
  
  ILoT reverse(); 
}

class MtLoT implements ILoT {
  MtLoT() {
  }
  public ILoT completable() {
    return this;
  }
  
  public ILoT append(ILoT other) {
    return other;
  }
  
  public boolean containsTaskId(int id) {
    return false;
  }
  
  public ILoT completableHelper(ILoT rsf, ILoT pending) {
    return pending.getResolved(rsf).append(rsf).reverse();
  }
  
  public String print() {
    return "";
  }
  
  public ILoT getResolved(ILoT lot) {
    return this;
  }
  
  public ILoT remove(ILoT lot) {
    return this;
  }
  
  public boolean contains(Task t) {
    return false;
  }
  
  public ILoT reverse() {
    return this;
  }
}

class ConsLoT implements ILoT {
  Task first;
  ILoT rest;

  ConsLoT(Task first, ILoT rest) {
    this.first = first;
    this.rest = rest;
  }
  
  public ILoT completable() {
    return this.completableHelper(
        new MtLoT(), // rsf - list of completable tasks
        new MtLoT()) // pending - list of tasks not resolved (yet)
        ;
  }
  
  
  public ILoT append(ILoT other) {
    return new ConsLoT(this.first, this.rest.append(other));
  }
  
  public boolean containsTaskId(int id) {
    if (this.first.id == id) {
      return true;
    } else {
      return this.rest.containsTaskId(id);
    }
  }
  
  public ILoT completableHelper(ILoT rsf, ILoT pending) {
    return this.first.completable(
        this.rest,
        rsf,
        pending);
  }
  
  public String print() {
    return Integer.toString(this.first.id).concat(this.rest.print());
  }
  
  public ILoT getResolved(ILoT lot) {
    if (this.first.completableWith(lot)) {
      return new ConsLoT(this.first, this.rest.getResolved(lot));
    } else {
      return this.rest.getResolved(lot);
    }
  }
  
  public ILoT remove(ILoT lot) {
    if (lot.contains(this.first)) {
      return this.rest.remove(lot);
    } else {
      return new ConsLoT(this.first, this.rest.remove(lot));
    }
  }
  
  public boolean contains(Task t) {
    if (this.first.id == t.id) {
      return true;
    }
    return this.rest.contains(t);
  }
  
  public ILoT reverse() {
    return this.rest.reverse().append(new ConsLoT(this.first, new MtLoT()));
  }
}

class Examples {
  // 1---2---3---4---5---6---7---8---9
  // |---|---|---|---|---|---|---|---|
  // ()--1---2---5---()--()--8---9---7
  // --------4---------------!CYCLE!
  // --------6
  ILoId t1pre = new MtLoId();
  ILoId t2pre = new ConsLoId(1, new MtLoId());
  ILoId t3pre = new ConsLoId(2, new ConsLoId(4, new ConsLoId(6, new MtLoId())));
  ILoId t4pre = new ConsLoId(5, new MtLoId());
  ILoId t5pre = new MtLoId();
  ILoId t6pre = new MtLoId();
  ILoId t7pre = new ConsLoId(8, new MtLoId());
  ILoId t8pre = new ConsLoId(9, new MtLoId());
  ILoId t9pre = new ConsLoId(7, new MtLoId());

  Task t1 = new Task(1, t1pre); // no dependencies
  Task t2 = new Task(2, t2pre); // depends on t1
  Task t3 = new Task(3, t3pre); // depends on t2, t4, t6
  Task t4 = new Task(4, t4pre); // depends on t5
  Task t5 = new Task(5, t5pre);
  Task t6 = new Task(6, t6pre);
  Task t7 = new Task(7, t7pre);
  Task t8 = new Task(8, t8pre);
  Task t9 = new Task(9, t9pre);

  ILoT lot1 = new ConsLoT(t1, new ConsLoT(t2, new ConsLoT(t3, new ConsLoT(t4, new ConsLoT(t5,
      new ConsLoT(t6, new ConsLoT(t7, new ConsLoT(t8, new ConsLoT(t9, new MtLoT())))))))));
  ILoT lot1Completable = new ConsLoT(t1, new ConsLoT(t2,
      new ConsLoT(t5, new ConsLoT(t4, new ConsLoT(t6, new ConsLoT(t3, new MtLoT()))))));
  ILoT lot2 = new ConsLoT(t7, new ConsLoT(t8, new ConsLoT(t9, new MtLoT())));

  boolean testCompletable(Tester t) {
    return t.checkExpect(lot1.completable(), lot1Completable)
        && t.checkExpect(lot2.completable(), new MtLoT())
        && t.checkExpect(lot1.reverse().completable(),
            new ConsLoT(t6, new ConsLoT(t5,
                new ConsLoT(t4, new ConsLoT(t1, new ConsLoT(t2, new ConsLoT(t3, new MtLoT())))))));
  }
}
