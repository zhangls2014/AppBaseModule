/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.zhangls.base.util

import java.util.*

/**
 *
 * Operations for random `String`s.
 *
 * Currently *private high surrogate* characters are ignored.
 * These are unicode characters that fall between the values 56192 (db80)
 * and 56319 (dbff) as we don't know how to handle them.
 * High and low surrogates are correctly dealt with - that is if a
 * high surrogate is randomly chosen, 55296 (d800) to 56191 (db7f)
 * then it is followed by a low surrogate. If a low surrogate is chosen,
 * 56320 (dc00) to 57343 (dfff) then it is placed after a randomly
 * chosen high surrogate.
 *
 *
 * #ThreadSafe#
 * @author Apache Software Foundation
 * @author [Steven Caswell](mailto:steven@caswell.name)
 * @author Gary Gregory
 * @author Phil Steitz
 * @since 1.0
 * @version $Id: RandomStringUtils.java 1056988 2011-01-09 17:58:53Z niallp $
 */
class RandomStringUtils {
  /**
   *
   * Random object used by random method. This has to be not local
   * to the random method so as to not return the same value in the
   * same millisecond.
   */
  private val RANDOM = Random()

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of characters whose
   * ASCII value is between `32` and `126` (inclusive).
   *
   * @param count  the length of random string to create
   * @return the random string
   */
  fun randomAscii(count: Int): String {
    return random(count, 32, 127, false, false)
  }

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of alphabetic
   * characters.
   *
   * @param count  the length of random string to create
   * @return the random string
   */
  fun randomAlphabetic(count: Int): String {
    return random(count, true, false)
  }

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of alpha-numeric
   * characters.
   *
   * @param count  the length of random string to create
   * @return the random string
   */
  fun randomAlphanumeric(count: Int): String {
    return random(count, true, true)
  }

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of numeric
   * characters.
   *
   * @param count  the length of random string to create
   * @return the random string
   */
  fun randomNumeric(count: Int): String {
    return random(count, false, true)
  }
  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of alpha-numeric
   * characters as indicated by the arguments.
   *
   * @param count  the length of random string to create
   * @param letters  if `true`, generated string will include
   * alphabetic characters
   * @param numbers  if `true`, generated string will include
   * numeric characters
   * @return the random string
   */
  // Random
  //-----------------------------------------------------------------------
  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of all characters.
   *
   * @param count  the length of random string to create
   * @return the random string
   */
  @JvmOverloads
  fun random(count: Int, letters: Boolean = false, numbers: Boolean = false): String {
    return random(count, 0, 0, letters, numbers)
  }
  /**
   *
   * Creates a random string based on a variety of options, using
   * supplied source of randomness.
   *
   *
   * If start and end are both `0`, start and end are set
   * to `' '` and `'z'`, the ASCII printable
   * characters, will be used, unless letters and numbers are both
   * `false`, in which case, start and end are set to
   * `0` and `Integer.MAX_VALUE`.
   *
   *
   * If set is not `null`, characters between start and
   * end are chosen.
   *
   *
   * This method accepts a user-supplied [Random]
   * instance to use as a source of randomness. By seeding a single
   * [Random] instance with a fixed seed and using it for each call,
   * the same random sequence of strings can be generated repeatedly
   * and predictably.
   *
   * @param count  the length of random string to create
   * @param start  the position in set of chars to start at
   * @param end  the position in set of chars to end before
   * @param letters  only allow letters?
   * @param numbers  only allow numbers?
   * @param chars  the set of chars to choose randoms from.
   * If `null`, then it will use the set of all chars.
   * @param random  a source of randomness.
   * @return the random string
   * @throws ArrayIndexOutOfBoundsException if there are not
   * `(end - start) + 1` characters in the set array.
   * @throws IllegalArgumentException if `count` &lt; 0.
   * @since 2.0
   */
  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of alpha-numeric
   * characters as indicated by the arguments.
   *
   * @param count  the length of random string to create
   * @param start  the position in set of chars to start at
   * @param end  the position in set of chars to end before
   * @param letters  if `true`, generated string will include
   * alphabetic characters
   * @param numbers  if `true`, generated string will include
   * numeric characters
   * @return the random string
   */
  /**
   *
   * Creates a random string based on a variety of options, using
   * default source of randomness.
   *
   *
   * This method has exactly the same semantics as
   * [.random], but
   * instead of using an externally supplied source of randomness, it uses
   * the internal static [Random] instance.
   *
   * @param count  the length of random string to create
   * @param start  the position in set of chars to start at
   * @param end  the position in set of chars to end before
   * @param letters  only allow letters?
   * @param numbers  only allow numbers?
   * @param chars  the set of chars to choose randoms from.
   * If `null`, then it will use the set of all chars.
   * @return the random string
   * @throws ArrayIndexOutOfBoundsException if there are not
   * `(end - start) + 1` characters in the set array.
   */
  @JvmOverloads
  fun random(
    size: Int, start: Int, end: Int, letters: Boolean, numbers: Boolean,
    chars: CharArray? = null, random: Random = RANDOM
  ): String {
    var count = size
    var startPos = start
    var endPos = end
    if (count == 0) {
      return ""
    } else require(count >= 0) { "Requested random string length $count is less than 0." }
    if (startPos == 0 && endPos == 0) {
      endPos = 'z'.code + 1
      startPos = ' '.code
      if (!letters && !numbers) {
        startPos = 0
        endPos = Int.MAX_VALUE
      }
    }
    val buffer = CharArray(count)
    val gap = endPos - startPos
    while (count-- != 0) {
      val ch: Char = if (chars == null) {
        (random.nextInt(gap) + startPos).toChar()
      } else {
        chars[random.nextInt(gap) + startPos]
      }
      if (letters && Character.isLetter(ch)
        || numbers && Character.isDigit(ch)
        || !letters && !numbers
      ) {
        if (ch.code >= 56320 && ch.code <= 57343) {
          if (count == 0) {
            count++
          } else {
            // low surrogate, insert high surrogate after putting it in
            buffer[count] = ch
            count--
            buffer[count] = (55296 + random.nextInt(128)).toChar()
          }
        } else if (ch.code >= 55296 && ch.code <= 56191) {
          if (count == 0) {
            count++
          } else {
            // high surrogate, insert low surrogate before putting it in
            buffer[count] = (56320 + random.nextInt(128)).toChar()
            count--
            buffer[count] = ch
          }
        } else if (ch.code >= 56192 && ch.code <= 56319) {
          // private high surrogate, no effing clue, so skip it
          count++
        } else {
          buffer[count] = ch
        }
      } else {
        count++
      }
    }
    return String(buffer)
  }

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of characters
   * specified.
   *
   * @param count  the length of random string to create
   * @param chars  the String containing the set of characters to use,
   * may be null
   * @return the random string
   * @throws IllegalArgumentException if `count` &lt; 0.
   */
  fun random(count: Int, chars: String?): String {
    return if (chars == null) {
      random(
        count,
        0,
        0,
        false,
        false,
        null,
        RANDOM
      )
    } else random(count, chars.toCharArray())
  }

  /**
   *
   * Creates a random string whose length is the number of characters
   * specified.
   *
   *
   * Characters will be chosen from the set of characters specified.
   *
   * @param count  the length of random string to create
   * @param chars  the character array containing the set of characters to use,
   * may be null
   * @return the random string
   * @throws IllegalArgumentException if `count` &lt; 0.
   */
  fun random(count: Int, chars: CharArray?): String {
    return if (chars == null) {
      random(
        count,
        0,
        0,
        false,
        false,
        null,
        RANDOM
      )
    } else random(
      count,
      0,
      chars.size,
      false,
      false,
      chars,
      RANDOM
    )
  }
}