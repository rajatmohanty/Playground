import UIKit

let array = [1, -2, 3, 5, -2, 6, -1]

func maxSum(in array: [Int]) -> Int {
    var max: Int = 0
    var current: Int = 0
    for i in (0..<array.count) {
        current += array[i]
        if current < 0 {
            current = 0
        }
        if current > max {
            max = current
        }
    }
    return max
}

maxSum(in: array)
