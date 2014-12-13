/*
 * Copyright (c) 2014 maartyl.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    maartyl - initial API and implementation and/or initial documentation
 */
package maailisp.core;

/**
 *
 * @author maartyl
 */
public final class Keyword extends Symbol {

  public Keyword(String name, String namespace) {
    super(name, namespace);
  }

  @Override
  public String toString() {
    return ":" + super.toString();
  }


  public static Keyword of(String s) {
    if (s.charAt(0) == ':') s = s.substring(1);//strip of : ;; I can make a keyword of something that doesnt start with a :
    if (s.length() == 1) return of(null, s);
    int slash = s.lastIndexOf('/');
    if (slash <= 0) return of(null, s);
    return of(s.substring(0, slash - 1), s.substring(slash + 1));
  }

  public static Keyword of(String ns, String nm) {
    assert nm != null;
    return new Keyword(ns, nm);
  }

}
