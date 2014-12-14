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
 * Tuple
 *
 * @author maartyl
 */
public class Vector2<T> extends AVector<T> {

  private T elem0;
  private T elem1;

  public Vector2(T elem0, T elem1) {
    this.elem0 = elem0;
    this.elem1 = elem1;
  }

  @Override
  public T get(int index) {
    if (index == 0) return elem0;
    if (index == 1) return elem1;
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> set(int index, T elem) {
    if (index == 0) return new Vector2<>(elem, elem1);
    if (index == 1) return new Vector2<>(elem0, elem);
    throw outOfBounds(index);
  }

  @Override
  public Vector<T> add(T elem) {
    return new Vector3<>(elem0, elem1, elem);
  }

  @Override
  public int length() {
    return 2;
  }

}
