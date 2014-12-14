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
 * Net thread safe yet. (Done so, that it could be improved, hopefully)
 *
 * @author maartyl
 * @param <T> Vector of what
 */
public interface Vector<T> {

  /**
   * Retrieves
   *
   * @param index
   * @return
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
   * Number 1 bigger then maximal index.
   * <p>
   * @return the number of elements of this Vector instance
   */
  int length();

}
