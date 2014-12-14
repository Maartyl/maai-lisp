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

import maailisp.coll.Seq;

/**
 *
 * @author maartyl
 */
class LazySeq<T> implements Seq<T> {

  Seq.LazyRest<T> seqFn;
  Seq<T> cashedSeq = null;

  public LazySeq(Seq.LazyRest<T> seqFn) {
    this.seqFn = seqFn;
  }

  @Override
  public final T first() {
    assureEvaluated();
    return Seq.first(cashedSeq);
  }

  @Override
  public final Seq<T> rest() {
    assureEvaluated();
    return Seq.rest(cashedSeq);
  }

  private void assureEvaluated() {
    if (seqFn != null) {
      cashedSeq = seqFn.eval();
      seqFn = null; //GC friendly + no need for extra boolean
    }
  }

  @Override
  public boolean isNil() {
    assureEvaluated();
    return cashedSeq == null;
  }
}
