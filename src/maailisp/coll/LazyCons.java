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
package maailisp.coll;

/**
 *
 * @author maartyl
 */
class LazyCons<T> implements Seq<T> {

  T first;
  Seq.LazyRest<T> restFn; //doesn't work: restfn is remembered : make its own class
  Seq<T> cashedRest = null;

  public LazyCons(T first, Seq.LazyRest<T> restFn) {
    this.first = first;
    this.restFn = restFn;
  }

  @Override
  public final T first() {
    return first;
  }

  @Override
  public final Seq<T> rest() {
    if (restFn != null) {
      cashedRest = restFn.eval();
      restFn = null; //GC free + no need for extra boolean
    }
    return cashedRest;
  }
}
