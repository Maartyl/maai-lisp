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

import java.util.Objects;

/**
 *
 * @author maartyl
 */
public class Symbol extends Obj {
  private final String name;
  private final String namespace;

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

  @Override
  public int hashCode() {
    if (namespace == null) return Objects.hashCode(this.name);
    int hash = 7;
    hash = 83 * hash + Objects.hashCode(this.name);
    hash = 83 * hash + Objects.hashCode(this.namespace);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Symbol other = (Symbol) obj;
    if (!Objects.equals(this.name, other.name)) return false;
    return Objects.equals(this.namespace, other.namespace);
  }


  //add some method to resolve in context: keywords override to resove itself

  public static Symbol of(String s) {
    if (s.length() == 1) return of(null, s);
    int slash = s.lastIndexOf('/');
    if (slash <= 0) return of(null, s);
    return of(s.substring(0, slash - 1), s.substring(slash + 1));
  }

  public static Symbol of(String ns, String nm) {
    if (nm.charAt(0) == ':') return Keyword.of(ns, nm);
    return new Symbol(ns, nm);
  }

}

//possible split to SymbolNS, rhat would include namespace, Symbol would always retrun null
