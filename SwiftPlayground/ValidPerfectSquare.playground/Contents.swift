/**
 Given a positive integer num, write a function which returns True if num is a perfect square else False.
 
 Note: Do not use any built-in library function such as sqrt.
 
 Example 1:
 
 Input: 16
 Returns: True
 Example 2:
 
 Input: 14
 Returns: False
 */

class Solution {
    func isPerfectSquare(_ num: Int) -> Bool {
        var tempNum = num
        var i = 1
        while tempNum > 0 {
            tempNum -= i
            i += 2
        }
        return tempNum == 0
    }
}

Solution().isPerfectSquare(16)
