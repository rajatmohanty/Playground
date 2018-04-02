func quickSort2(_ array: [Int]) -> [Int] {
    return array.count <= 1 ? array : quickSort2(array.filter { $0 < array[0] }) + [array[0]] + quickSort2(array.filter { $0 > array[0] })
}

func quickSort(array: inout [Int], left: Int, right: Int) {
    if left > right {
        return
    }
    if array.count == 0 {
        return
    }
    let pivot = array[right]
    var index: Int = left
    for i in left...right {
        if array[i] < pivot {
            if i != index {
                let temp = array[index]
                array[index] = array[i]
                array[i] = temp
            }
            index = index+1
        }
    }
    
    if right != index {
        let temp = array[index]
        array[index] = array[right]
        array[right] = temp
    }
    quickSort(array: &array, left: left, right: index - 1)
    quickSort(array: &array, left: index + 1, right: right)
}

var array = [1,8,9,7,2,5,4,3,6,0]
print(quickSort(array: &array, left: 0, right: array.count - 1))
print(array)

print(quickSort2(array))
