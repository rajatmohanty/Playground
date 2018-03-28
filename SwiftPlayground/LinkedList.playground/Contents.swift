import UIKit

class DoubleNode {
    var value: Int
    var next: DoubleNode?
    var last: DoubleNode?
    
    init(number: Int) {
        self.value = number
    }
    
    func print() {
        var node: DoubleNode? = self
        var string: String = ""
        while node != nil {
            string.append("\(node!.value)")
            node = node!.next
            if node != nil {
                string.append(" <-> ")
            }
        }
        Swift.print(string)
    }
}

func reverseDoubleLinkedList(headNode: DoubleNode?) -> DoubleNode? {
    var headNode = headNode
    var lastNode: DoubleNode? = nil
    var nextNode: DoubleNode? = nil
    while headNode != nil {
        nextNode = headNode!.next
        headNode!.next = lastNode
        headNode!.last = nextNode
        lastNode = headNode
        headNode = nextNode
    }
    return lastNode
}

let doubleNodeA = DoubleNode(number: 1)
let doubleNodeB = DoubleNode(number: 2)
let doubleNodeC = DoubleNode(number: 3)
let doubleNodeD = DoubleNode(number: 4)
let doubleNodeE = DoubleNode(number: 5)
doubleNodeA.next = doubleNodeB
doubleNodeB.next = doubleNodeC
doubleNodeC.next = doubleNodeD
doubleNodeD.next = doubleNodeE
doubleNodeE.last = doubleNodeD
doubleNodeD.last = doubleNodeC
doubleNodeC.last = doubleNodeB
doubleNodeB.last = doubleNodeA
doubleNodeA.print()
reverseDoubleLinkedList(headNode: doubleNodeA)?.print()

class SingleNode {
    var value: Int
    var next: SingleNode?
    
    init(number: Int) {
        self.value = number
    }
    
    func print() {
        var node: SingleNode? = self
        var string: String = ""
        while node != nil {
            string.append("\(node!.value)")
            node = node!.next
            if node != nil {
                string.append(" -> ")
            }
        }
        Swift.print(string)
    }
}

func reverseSingleLinkedList(headNode: SingleNode?) -> SingleNode? {
    var headNode = headNode
    var lastNode: SingleNode?
    var nextNode: SingleNode?
    
    while headNode != nil {
        nextNode = headNode!.next
        headNode!.next = lastNode
        lastNode = headNode
        headNode = nextNode
    }
    
    return lastNode
}

let singleNodeA = SingleNode(number: 1)
let singleNodeB = SingleNode(number: 2)
let singleNodeC = SingleNode(number: 3)
let singleNodeD = SingleNode(number: 4)
let singleNodeE = SingleNode(number: 5)
singleNodeA.next = singleNodeB
singleNodeB.next = singleNodeC
singleNodeC.next = singleNodeD
singleNodeD.next = singleNodeE
singleNodeA.print()
reverseSingleLinkedList(headNode: singleNodeA)?.print()
