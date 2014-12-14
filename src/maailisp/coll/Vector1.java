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

import java.util.Objects;

/**
 *
 * @author maartyl
 */
public class Vector1<T> extends AVector<T> {

  private final T elem0;

  public Vector1(T elem0) {
    this.elem0 = elem0;
  }


  @Override
  public T get(int index) {
    if (index == 0) return elem0;
    throw outOfBounds(index); 
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index == 0) return new Vector1<>(elem);
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> add(T elem) {
    return new Vector2<>(elem0, elem);
  }

  @Override
  public int length() {
    return 1;
  }

  @Override
  public String toString() {
    return "[" + elem0 + "]";
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(this.elem0);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) return false;
    if (getClass() != obj.getClass()) return false;
    final Vector1<?> other = (Vector1<?>) obj;
    return Objects.equals(this.elem0, other.elem0);
  }


}
