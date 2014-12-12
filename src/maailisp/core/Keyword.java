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
public class Keyword extends Symbol {

  public Keyword(String name, String namespace) {
    super(name, namespace);
  }

  public static Keyword of(String ns, String nm) {
    return new Keyword(ns, nm);
  }

}
