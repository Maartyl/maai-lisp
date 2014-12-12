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
package maailisp.regex;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import maailisp.coll.Seq;

/**         *
 * @author maartyl
 */
public class SeqRegex {

  Pattern ptrn;

  public SeqRegex(Pattern p) {
    ptrn = p;
  }

  public SeqRegex(String pattern) {
    this(Pattern.compile(pattern));
  }

  public final Seq<MatchResult> matchSeq(String s) {
    return SeqRegex.matchSeq(ptrn, s);
  }

  @Override
  public String toString() {
    return "<seq> " + ptrn.toString();
  }

  public static Seq<MatchResult> matchSeq(Pattern p, String s) {
    return matchSeq(p.matcher(s));
  }

  static Seq<MatchResult> matchSeq(Matcher m) {
    if (!m.find()) return null;
    return Seq.lazy(MatchResult.build(m), () -> matchSeq(m));
  }

  public static class MatchResult {

    String match;

    MatchResult(String match) {
      this.match = match;
    }

    public String[] getGroups() {
      return null;
    }

    public boolean hasGroups() {
      return false;
    }

    public String getText() {
      return match;
    }

    @Override
    public String toString() {
      return hasGroups() ?
             "#[ " + match + " [<" + Arrays.stream(getGroups()).collect(Collectors.joining(">, <")) + ">]]" :
             "#[ " + match + " ]";
    }

    /**
     * Builds match with groups from previous find.
     * @param m stateful Matcher to generate from
     * @return new MatchResult
     */
    public static MatchResult build(Matcher m) {
      int gc = m.groupCount();
      if (gc == 0) return new MatchResult(m.group()); //most of the time
      final String match = m.group(0);
      final String[] groups = new String[gc];
      for (int i = 0; i < gc; i++)
        groups[i] = m.group(i + 1);

      return new MatchResult(match) {
        @Override
        public String[] getGroups() {
          return groups;
        }

        @Override
        public boolean hasGroups() {
          return true;
        }
      };
    }

  }
}
