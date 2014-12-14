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
 * Immutable vector.
 * If indexes were changed to longs, chosen design would allow to have long.maxValue long vectors. (no longer fast, though...)
 * all changes are of logarithmic complexity, base 32. (for integers: max is ~9.5 : ~constant)
 * <p>
 * Will be also used for tuples and well, general structures, where position matters.
 *
 * @author maartyl
 * @param <T> Vector of what
 */
public interface Vector<T> extends Seq<T> {

  @Override
  public default T first() {
    return get(0);
  }

  @Override
  public default Seq<T> rest() {
    return Seq.ofVector(this, 1);
  }



  /**
   * Retrieves
   *
   * @param index
   * @return associated element
   */
  T get(int index);

  /**
   * Returns new vector with element at given index.
   * <p>
   *
   * @param index
   * @param elem  element to set
   * @return
   */
  Vector<T> set(int index, T elem);

  /**
   * Creates new vector with added element.
   * <p>
   * @param elem
   * @return
   */
  Vector<T> add(T elem);

  /**
   * Number 1 bigger then the maximal index.
   * <p>
   * @return the number of elements of this Vector instance
   */
  int length();

  /**
   * empty Stub. Needs to be retyped each time: Java lacks types like Nothing.... (which then turns into whatever necessary)
   */
  public static Vector EMPTY = new Vector0<>();

  @SuppressWarnings("unchecked")
  public static <T> Vector<T> ofSeq(Seq<T> seq) {
    return Seq.reduce(seq, (Vector<T>) EMPTY, (v, e) -> v.add(e));
  }

}
