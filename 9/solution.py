import math

class Node:
    def __init__(self, value):
        self.value = value
        self.next = None
        self.prev = None

    def __repr__(self):
        return "<" + str(self.value) + ">"

    def fromZero(self):
        node = self
        while node.value != 0:
            node = node.next

        result = [node]
        node = node.next
        while node.value != 0:
            result.append(node)
            node = node.next

        return result

    def take(self, n):
        result = []
        node = self
        while len(result) < n:
            result.append(node)
            node = node.next

        return result

    def linkTo(self, n):
        self.next = n
        n.prev = self
        return self, n

    def place(self, v):
        n = Node(v)
        n.next = self.next.next
        n.prev = self.next
        self.next.next.prev = n
        self.next.next = n
        return n

    def moveSevenBack(self):
        return self.prev.prev.prev.prev.prev.prev.prev # lol

    def remove(self):
        self.prev.next = self.next
        self.next.prev = self.prev
        return self.next


def toDoublyLinkedCircularList(l):
    nodes = [Node(l[0])]
    for i in range(1, len(l)):
        newNode = Node(l[i])
        nodes[i - 1].linkTo(newNode)
        nodes.append(newNode)

    nodes[len(nodes) - 1].linkTo(nodes[0])

    return nodes[0]

def play(r, p):
    m = toDoublyLinkedCircularList([0])
    currentMarble = 1
    scores = {}
    for i in range(p):
        scores[i] = 0

    currentPlayer = 0
    while currentMarble <= r:
        if 0 != currentMarble % 23:
            m = m.place(currentMarble)
            currentMarble += 1
        else:
            m = m.moveSevenBack()
            v = m.value
            m = m.remove()
            scores[currentPlayer] += currentMarble + v

            currentMarble += 1
            m = m.place(currentMarble)
            currentPlayer = (currentPlayer + 1) % p
            currentMarble += 1

        currentPlayer = (currentPlayer + 1) % p

    return m, scores

def highScore(p, lastMarbleValue):
    m, scores = play(lastMarbleValue, p)
    return max(scores.values())

print("The answer to part 1 is " + str(highScore(439, 71307)))
print("The answer to part 2 is " + str(highScore(439, 71307 * 100)))

exit(0)
