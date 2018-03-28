class Solution {
    func permute(_ nums: [Int]) -> [[Int]] {
        var nums = nums
        if nums.count == 1 {
            return [nums]
        } else {
            var temp: [[Int]] = []
            for i in 0..<nums.count {
                for result in permute(nums.filter { $0 != nums[i] }) {
                    var tempResult = result
                    tempResult.insert(nums[i], at: 0)
                    temp.append(tempResult)
                }
            }
            return temp
        }
    }
}

Solution().permute([1,2,3,4,5])
