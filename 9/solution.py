class Node:
    def __init__(self, value):
        self.value = value
        self.next = None
        self.prev = None

    def __repr__(self):
        return "<" + str(self.value) + ">"

def link(n1, n2):
    n1.next = n2
    n2.prev = n1
    return n1, n2

def toMarbles(l):
    nodes = [Node(l[0])]
    for i in range(1, len(l)):
        newNode = Node(l[i])
        link(nodes[i - 1], newNode)
        nodes.append(newNode)

    link(nodes[len(nodes) - 1], nodes[0])

    return nodes
