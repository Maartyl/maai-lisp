/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maailisp.core;

/**
 *
 * @author maartyl
 */
public class Symbol extends Obj {
  public final String name;     //public for immutable; only used from 1 project; if I ever decide it's not ok, I can change it to getters everywhere; unlikely
  public final String namespace;

  protected Symbol(String name, String namespace) {
    this.name = name;
    this.namespace = namespace;
  }

  public String getName() {
    return name;
  }

  public String getNamespace() {
    return namespace;
  }

  @Override
  public String toString() {
    return namespace == null ? name : namespace + "/" + name;
  }


  //add some method to resolve in context: keywords override to resove itself

  public static Symbol of(String s) {
    int slash = s.lastIndexOf('/');
    if (slash <= 0) return of(null, s);
    return of(s.substring(0, slash - 1), s.substring(slash + 1));
  }

  public static Symbol of(String ns, String nm) {
    if (nm.charAt(0) == ':') return Keyword.of(ns, nm);
    return new Symbol(ns, nm);
  }

}
