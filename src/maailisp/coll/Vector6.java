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
 * Invariant: net enforced, but left sub_vector should always be full; in this case: 3
 *
 * @author maartyl
 * @param <T>
 */
public class Vector6<T> extends AVector<T> {

  private final Vector<T> subv0;
  private final Vector<T> subv1;

  public Vector6(Vector<T> subv0, Vector<T> subv1) {
    assert subv0.length() == 3;
    this.subv0 = subv0;
    this.subv1 = subv1;
  }

  @Override
  public T get(int index) {
    if (index <= 3) return subv0.get(index);
    if (index <= 3 * 2) return subv0.get(index - 3);
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index <= 3) return new Vector6<>(subv0.set(index, elem), subv1);
    if (index <= 3 * 2) return new Vector6<>(subv0, subv1.set(index - 3, elem));
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> add(T elem) {
    if (length() < 3 * 2)
      return new Vector6<>(subv0, subv1.add(elem));
//possibly add 1 more layer? (like 12 | 18 | 24 ... )
//upgrade to M0
    @SuppressWarnings("unchecked")
    T[] nelems = (T[]) new Object[7];
    for (int i = 0; i < 6; i++) //slow copy, want to keep notM0 possibly short...
      nelems[i] = get(i);
    
    nelems[6] = elem;
    return new VectorM0<>(nelems);
  }

  @Override
  public int length() {
    return subv0.length() + subv1.length();
  }

}
