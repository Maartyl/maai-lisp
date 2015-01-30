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
package maailisp.util;

/**
 *
 * @author maartyl
 */
public class H {

  private H() {
  }

  /**
   * Allows to perform some action inside function call. like cleaning up (arg=null)
   * Useful for freeing vars so GC can collect them etc.
   * <p>
   * @param <T>
   * @param a   pass through
   * @param b   ignore, evaluate for side effects
   * @return a
   */
  public static <T> T ret1(T a, Object b) {
    return a;
  }

  /**
   * http://www.mail-archive.com/javaposse@googlegroups.com/msg05984.html
   * <p>
   * "throw H.sneakyThrow(ex)" to fix Java exception handling bug
   * <p>
   * <p>
   * I love this bug-fix. If anyone were to be against it, I would like to remind them, that they have said themselves that
   * explicit throwing of all exceptions in all method declarations was a bad design decision in the first place.
   * <p>
   * @param t
   * @return t
   */
  public static RuntimeException sneakyThrow(Throwable t) {
    if (t == null) throw new NullPointerException("t");
    H.<RuntimeException>sneakyThrow0(t);
    return null;
  }

  @SuppressWarnings("unchecked")
  private static <T extends Throwable> void sneakyThrow0(Throwable t) throws T {
    throw (T) t;
  }
}
