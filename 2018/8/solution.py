with open('input.txt', 'r') as f:
  s = f.read()
  input = list(map(int, s[:-1].split(' ')))


class Node:
  def __init__(self, n_children, n_metadata, children, metadata):
    self.n_children = n_children
    self.n_metadata = n_metadata
    self.children = children
    self.metadata = metadata

  def __repr__(self):
    return "(" + str(self.metadata) + ")<" + str(self.children) + ">"

  def sum_metadata(self):
    return sum(self.metadata) + sum([c.sum_metadata() for c in self.children])

  def value(self):
    if self.n_children == 0:
      return sum(self.metadata)
    else:
      return sum([self.children[m - 1].value() for m in self.metadata if m <= self.n_children])


def parse_leaf(l):
  n_children = 0
  n_metadata = l[1]
  children = []
  metadata = l[2 : 2 + n_metadata]
  dropped_l = l[2 + n_metadata :]
  return Node(n_children, n_metadata, children, metadata), dropped_l

def parse_node(l):
  if l[0] == 0:
    return parse_leaf(l)

  n_children = l[0]
  n_metadata = l[1]
  l = l[2:]
  children = []

  while len(children) < n_children:
    child, l = parse_node(l)
    children.append(child)

  metadata = l[:n_metadata]
  dropped_l = l[n_metadata:]
  return Node(n_children, n_metadata, children, metadata), dropped_l

root, l = parse_node(input)
assert(l == [])

answerp1 = root.sum_metadata()
answerp2 = root.value()

print("The answer to part 1 is " + str(answerp1))
print("The answer to part 2 is " + str(answerp2))

exit(0)
