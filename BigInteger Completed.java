package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */
public class BigInteger {
	
	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	boolean borrow;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		BigInteger parse = new BigInteger();
		for (int i=0; i<integer.length(); i++) {
			char c = integer.charAt(i);
			if (parse.front == null) {
				if (c == '-') {
					parse.negative = true;
				}else if (c == '+') {
					continue;
				}else if (Character.isDigit(c)) {
					if (c == '0') {
						continue;
					}else {
						DigitNode digit = new DigitNode(Character.getNumericValue(c),null);
						parse.front = digit;
						parse.numDigits = parse.numDigits +1;
					}
				}else {throw new IllegalArgumentException();}
			}else {
				if (Character.isDigit(c)) {
					DigitNode newDigit = new DigitNode(Character.getNumericValue(c),parse.front);
					parse.front = newDigit;
					parse.numDigits = parse.numDigits+1;
				}else {throw new IllegalArgumentException();}
			}
		}
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return parse; 
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	
	public DigitNode Reverse(DigitNode front) {
		DigitNode reverse = null;
		for (DigitNode ptr = front; ptr!=null;ptr=ptr.next) {
			if (reverse == null) {
				DigitNode newDigit = new DigitNode(ptr.digit,reverse);
				reverse = newDigit;
			} else {
				DigitNode newDigit = new DigitNode(ptr.digit,reverse);
				reverse = newDigit;
			}
		}
		return reverse;
	}
	
	public static BigInteger add(BigInteger first, BigInteger second) {
		DigitNode res = null; // res is head node of the resultant list 
		DigitNode prev = null; 
		DigitNode temp = null; 
		BigInteger answer = new BigInteger();
		int carry = 0, sum; 
		DigitNode firstReverse = first.Reverse(first.front);
		DigitNode secondReverse = second.Reverse(second.front);
		
		boolean firstNeg = first.negative;
		boolean secondNeg = second.negative;
		boolean add = true;
		boolean subtractfirst = false;
		
		
		if(firstNeg == false && secondNeg == false ) {
			answer.negative = false;
		} else if (firstNeg == true && secondNeg == true) {
			answer.negative = true;
		} else if (firstNeg == true && secondNeg == false) {
			if (second.numDigits > first.numDigits) {
				answer.negative = false;
				add = false;
				subtractfirst = true;
			}else if (first.numDigits == second.numDigits) {
				while (firstReverse != null || secondReverse != null) {
					if (firstReverse.digit > secondReverse.digit) {
						answer.negative = true;
						add = false;
						subtractfirst = false;
						break;
					} else if (firstReverse.digit < secondReverse.digit) {
						answer.negative = false;
						add = false;
						subtractfirst = false;
						break;
					} else if (firstReverse.digit == secondReverse.digit) {
						continue;
					}
					firstReverse = firstReverse.next;
					secondReverse = secondReverse.next;			
			} }else {
				answer.negative = false;
				add = false;
				subtractfirst = true;
			}
			
		} else if (firstNeg == false && secondNeg == true) {
			if (second.numDigits > first.numDigits) {
				answer.negative = true;
				add = false;
				subtractfirst = false;
			}else if (first.numDigits == second.numDigits) {
				while (firstReverse != null || secondReverse != null) {
					if (firstReverse.digit > secondReverse.digit) {
						answer.negative = false;
						add = false;
						subtractfirst = true;
						break;
					} else if (firstReverse.digit < secondReverse.digit) {
						answer.negative = true;
						add = false;
						subtractfirst = false;
						break;
					} else if (firstReverse.digit == secondReverse.digit) {
						continue;
					}
					firstReverse = firstReverse.next;
					secondReverse = secondReverse.next;				
			} }else {
				answer.negative = false;
				add = false;
				subtractfirst = true;
			}
		}

		
		
		while (first.front != null || second.front != null) //while both lists exist 
		{ 
		   
			int firstDigit = (first.front != null)? first.front.digit: 0;
			int secondDigit = (second.front != null)? second.front.digit:0;
			if (add == true) {
				sum = carry + (first.front != null ? firstDigit : 0) 
						+ (second.front != null ? secondDigit : 0); 
			} else  if ( subtractfirst = true){
				sum = ((first.front != null ? firstDigit : 0) 
						- (second.front != null ? secondDigit : 0)); 
			} else {
				sum = carry + ((second.front != null ? secondDigit : 0) 
						- (first.front != null ? firstDigit : 0)); 
			}
		    

		    
		    carry = (sum >= 10 || sum <=-10) ? 1 : 0; 

		
		    if (sum>=10 || sum <=-10) {
		    	sum = sum % 10; 
		    }
		    

		    temp = new DigitNode(sum,null); 


		    if (res == null) { 
		        res = temp; 
		    }else {
		        prev.next = temp; 
		    } 

		    prev = temp; 
		   
		    if (first.front != null) { 
		        first.front = first.front.next; 
		    } 
		    if (second.front != null) { 
		        second.front = second.front.next;
		    } 
		} 

		if (carry > 0) { 
		    temp.next = new DigitNode(carry,null); 
		} 

		answer.front=res;

	
		return answer; 
	}
	
		public static BigInteger multiply(BigInteger other, BigInteger second) {
		
			boolean turnnegativity = false;
			BigInteger patchadd = new BigInteger();
			DigitNode firstnode = new DigitNode(0, null);
			patchadd.front = firstnode;

			if (other.negative == true && second.negative == true) {
				other.negative = false;
				second.negative = false;
			} else {
				if (other.negative == true || second.negative == true) {
					other.negative = false;
					second.negative = false;
					turnnegativity = true;
				}
			}

			BigInteger bottomsup = other;
			BigInteger topdown = second;
			int otherassigned;
			int cloneassigned;
			if (other.toString().length() >= 10) {
				if (other.negative != true) {
					otherassigned = Integer.parseInt(other.toString().substring(0, 8));

				} else {
					otherassigned = Integer.parseInt(other.toString().substring(1, 9));
				}
			} else {
				otherassigned  = Integer.parseInt(other.toString().substring(0, other.toString().length()));
			}
			if (second.toString().length() >= 10) {
				if (second.negative != true) {
					cloneassigned = Integer.parseInt(second.toString().substring(0, 8));
				} else {
					cloneassigned = Integer.parseInt(second.toString().substring(1, 9));
				}
			} else {
				cloneassigned = Integer.parseInt(second.toString().substring(0, second.toString().length()));
			}
			if (otherassigned > cloneassigned) {
				topdown = other;
				bottomsup = second;
			} else {
				bottomsup = other;
				topdown = second;
			}
			String tempstring = topdown.toString();
			int addfactor = 0;
			int sum = 0;
			int bottomnum;
			String totalsum = "";
			String oursum = "";
			BigInteger oursumnode = new BigInteger();
			BigInteger totalsumnode = new BigInteger();
			int row = 0;
			int topnum;
			String result = "";
			while (bottomsup.front != null || topdown.front != null) {
				sum = addfactor;
				if (topdown.front == null && addfactor != 0) {
					oursum = oursum + addfactor;
					addfactor = 0;
				} else {
					if (topdown.front != null && bottomsup.front != null) {
						bottomnum = Integer.parseInt(bottomsup.front.toString());
						topnum = Integer.parseInt(topdown.front.toString());
						sum += bottomnum * topnum;
						topdown.front = topdown.front.next;
						addfactor = sum / 10;
						sum = sum % 10;
						oursum = oursum + sum;
					} else {
						if (bottomsup.front != null) {
							oursum = reversedigits(oursum);
							totalsumnode = BigInteger.parse(oursum);
							BigInteger nullint = new BigInteger();
							patchadd= BigInteger.add(totalsumnode, nullint);
							totalsumnode = BigInteger.add(oursumnode,nullint);
							sum = 0;
							addfactor = 0;
							bottomsup.front = bottomsup.front.next;
							BigInteger topback = BigInteger.parse(tempstring);
							topdown = topback;
							row++;
							totalsum = result;
							String nextstring = "";
							for (int s = 0; s < row; s++) {
								nextstring = nextstring + "0";
							}
							oursumnode = BigInteger.parse(oursum);
							oursum = nextstring;
						} else {
							if (bottomsup.front == null) {
								break;
							}
						}
					}
				}
			}
			DigitNode zerosss = new DigitNode(0, null);

			oursumnode.front = zerosss;
			totalsumnode.front = zerosss;
			totalsumnode = patchadd;
			if (turnnegativity == true) {
				totalsumnode.negative = true;
			}
			return totalsumnode;
		}
	
	
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */

		
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
	private static BigInteger reversenodes(BigInteger thething) {
		BigInteger reverse = new BigInteger();
		DigitNode nextone = new DigitNode(1, null);
		while (thething.front != null) {
			nextone = thething.front.next;
			thething.front.next = reverse.front;
			reverse.front = thething.front;
			thething.front = nextone;
		}
		BigInteger answer = reverse;
		return answer;
	}

	private static BigInteger additonpremethod(BigInteger other) {
		BigInteger clone = new BigInteger();
		int carry = 0;
		clone = other;
		boolean tempstat = true;
		DigitNode temp2t = null;
		DigitNode temp = null;
		BigInteger NodeAdditon = new BigInteger();
		int sum = 0;
		int first = 0;
		while (clone.front != null || other.front != null) {
			first++;
			sum = carry;
			if (clone.front != null) {
				sum = sum + Integer.parseInt(clone.front.toString());
				clone.front = clone.front.next;
			}
			if (other.front != null) {
				sum = sum + Integer.parseInt(other.front.toString());
				other.front = other.front.next;
			}
			carry = sum / 10;
			sum = sum % 10;
			if (first > 1 && (tempstat == false)) {
				temp = new DigitNode(sum, temp2t);
				NodeAdditon.front = temp;
				tempstat = true;
			} else {
				if (first > 1 && (tempstat == true)) {
					temp2t = new DigitNode(sum, temp);
					NodeAdditon.front = temp2t;
					tempstat = false;
				}
			}
			if (first == 1) {
				temp = new DigitNode(sum, null);
				NodeAdditon.front = temp;
			}
		}
		if (carry != 0 && tempstat == false) {
			DigitNode tempnode = new DigitNode(carry, temp2t);
				NodeAdditon.front = tempnode;
		}
		if (carry != 0 && tempstat == true) {
			DigitNode tempnode = new DigitNode(carry, temp);
			NodeAdditon.front = tempnode;
		}
		return reversenodes(NodeAdditon);
	}

	private static BigInteger subtractionpremethod(BigInteger other) {
		boolean turnneg = false;
		BigInteger clone = new BigInteger();
		int carry = 0;
		clone = other;
		boolean tempstat = true;
		DigitNode temp2 = null;
		DigitNode temp = null;
		BigInteger addnode = new BigInteger();
		BigInteger tempnode2 = new BigInteger();
		int sum = 0;
		int first = 0;
		int otherasint;
		int cloneasint;
		if (other.negative == false) {
			tempnode2 = other;
			other = clone;
			clone = tempnode2;
		}
		if (other.toString().length() >= 10) {
			if (other.negative == true) {
				otherasint = Integer.parseInt(other.toString().substring(1, 10));
			} else {
				otherasint = Integer.parseInt(other.toString().substring(0, 9));
			}
		} else {
			if (other.negative == true) {
				otherasint = Integer.parseInt(other.toString().substring(1, other.toString().length()));
			} else {
				otherasint = Integer.parseInt(other.toString().substring(0, other.toString().length()));
			}
		}
		if (clone.toString().length() >= 10) {
			if (clone.negative == true) {
				cloneasint = Integer.parseInt(clone.toString().substring(1, 10));
			} else {
				cloneasint = Integer.parseInt(clone.toString().substring(0, 9));
			}

		} else {
			if (clone.negative == true) {
				cloneasint = Integer.parseInt(clone.toString().substring(1, clone.toString().length()));
			} else {
				cloneasint = Integer.parseInt(clone.toString().substring(0, clone.toString().length()));

			}
		}

		if (otherasint > cloneasint) {
			tempnode2 = other;
			other = clone;
			clone = tempnode2;
			turnneg = true;
		} else {
		}
		while (clone.front != null || other.front != null) {
			first++;
			sum = carry;
			if ((other.front == (null)) && (clone.front != null)) {
				if (clone.front.toString().equals("0") && carry != 0) {
					System.out.println("carry is not 0");

					sum += Integer.parseInt(clone.front.toString()) + 10;
					carry = -1;
					clone.front = clone.front.next;
				} else {
					sum += Integer.parseInt(clone.front.toString());
					clone.front = clone.front.next;
					carry = 0;
				}
			} else {
				if (clone.front == null || other.front == null) {
					break;
				}
				if (Integer.parseInt(clone.front.toString()) == Integer.parseInt(other.front.toString())
						&& carry == -1) {
					sum += Integer.parseInt(clone.front.toString()) + 10 - Integer.parseInt(other.front.toString());
					carry = -1;

					clone.front = clone.front.next;
					other.front = other.front.next;
				} else {
					if (Integer.parseInt(clone.front.toString()) >= Integer.parseInt(other.front.toString())) {
						sum += Integer.parseInt(clone.front.toString()) - Integer.parseInt(other.front.toString());
						System.out.println("we are subtracting " + Integer.parseInt(clone.front.toString()) + " - "
								+ Integer.parseInt(other.front.toString()));
						carry = 0;
						clone.front = clone.front.next;
						other.front = other.front.next;
					} else {
						if (clone.front.next != null) {
							sum += Integer.parseInt(clone.front.toString()) + 10
									- Integer.parseInt(other.front.toString());
							System.out.println(
									"we are subtracting/carrying " + (Integer.parseInt(clone.front.toString()) + 10)
											+ " - " + Integer.parseInt(other.front.toString()));
							carry = -1;
							clone.front = clone.front.next;
							other.front = other.front.next;
						} else {
							sum += Integer.parseInt(clone.front.toString()) + 10
									- Integer.parseInt(other.front.toString());
							sum = sum * -1;
							carry = 0;
							clone.front = clone.front.next;
							other.front = other.front.next;
						}
					}
				}
			}
			if (other.front == null && clone.front == null) {
			}
			if (first == 1) {
				temp = new DigitNode(sum, null);
				addnode.front = temp;
			} else {
				if (tempstat == false) {
					temp = new DigitNode(sum, temp2);
					addnode.front = temp;
					tempstat = true;
				} else {
					if (tempstat == true) {
						temp2 = new DigitNode(sum, temp);
						addnode.front = temp2;
						tempstat = false;
					}
				}
			}
		}
		addnode = reversenodes(addnode);
		if (turnneg == true) {
			addnode.negative = true;
		}
		addnode = zeroelim(addnode);
		return addnode;
	}

	public static BigInteger zeroelim(BigInteger other) {
		boolean turnneg = false;
		DigitNode zero = new DigitNode(0, null);
		BigInteger zeroresult = new BigInteger();
		zeroresult.front = zero;
		boolean allzero = true;
		char z;
		int zerocount1 = 0;
		boolean neg = false;
		for (int x = 0; x < other.toString().length(); x++) {
			z = other.toString().charAt(x);
			System.out.println(z);
			if (z == '-') {
				neg = true;
			}
			if (z != '0' && z != '-') {
				allzero = false;
				break;
			} else {
				zerocount1++;
			}
		}
		if (allzero == true) {
			return zeroresult;
		} else {
			other = reversenodes(other);
			if (neg != false) {
				zerocount1 = zerocount1 - 1;
				neg = false;
			}
			for (int q = 0; q < zerocount1; q++) {
				other.front = other.front.next;
				System.out.println(other.toString());
			}
			other = reversenodes(other);
			if (turnneg == true) {
				other.negative = true;
			}
			BigInteger answer = other;
			return answer;
		}
	}

	public static String reversedigits(String input) {
		int beg = 0;
		char[] a = input.toCharArray();

		int end = a.length - 1;
		char temp;
		while (end > beg) {
			temp = a[beg];
			a[beg] = a[end];
			a[end] = temp;
			end--;
			beg++;
		}
		return new String(a);
	}
}
	
