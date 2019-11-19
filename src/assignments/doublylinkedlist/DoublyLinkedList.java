package assignments.doublylinkedlist;

import java.util.*;

/**
 * @author Avyay Natarajan
 * TODO:
 *  - implement java.util.List<T>
 * @param <T>
 */
public class DoublyLinkedList<T> implements Iterable<T>/*, List<T>*/ {
    private Node<T> _rootNode;
    private Node<T> _endNode;
    private int _size;

    private static class Node<T>
    {
        Node<T> next;
        Node<T> previous;
        T value;

        public Node(T _val)
        {
            this.value = _val;
        }

        public void connect(Node<T> next)
        {
            this.next = next;
            next.previous = this;
        }

        public void eject()
        {
            next.previous = previous;
            previous.next = next;
        }

        public void dispose()
        {
            next = null;
            previous = null;
        }
    }

    public DoublyLinkedList(Collection<? extends T> collection)
    {
        addAll(collection);
    }

    public DoublyLinkedList()
    {

    }

    public boolean add(T value)
    {
        if (_size > 1) {
            Node<T> previous_end = _endNode;
            _endNode = new Node<>(value);
            _endNode.previous = previous_end;
            previous_end.next = _endNode;
            _size++;
        } else if (_size > 0)
        {
            Node<T> _new = new Node<>(value);
            _rootNode.next = _new;
            _new.previous = _rootNode;
            _endNode = _new;
            _size = 2;
        } else {
            _rootNode = new Node<>(value);
            _endNode = _rootNode;
            _size = 1;
        }
        return true;
    }

    public boolean add(int index, T value)
    {
        if (index == _size - 1)
            return add(value);
        if (_size/2 - index >= 0)
        {
            Node<T> atIndex = track_from_root(index);
            Node<T> afterIndex = atIndex.next;
            atIndex.next = new Node<>(value);
            afterIndex.previous = atIndex.next;
        } else
        {
            Node<T> atIndex = track_from_end(_size - index - 1);
            Node<T> afterIndex = atIndex.next;
            atIndex.next = new Node<>(value);
            afterIndex.previous = atIndex.next;
        }
        return true;
    }

    public boolean addAll(Collection<? extends T> collection)
    {
        Node<T> first = null;
        Node<T> previous = null;
        for (T value: collection)
        {
            if (previous == null) {
                previous = new Node<T>(value);
                first = previous;
            } else
            {
                previous.connect(new Node<>(value));
                previous = previous.next;
            }
        }
        try {
            if (_rootNode == null) {
                _rootNode = first;
                _endNode = previous;
                return true;
            } else if (collection.size() > 0) {
                Node<T> last = _rootNode;
                while (last.next != null) {
                    last = last.next;
                }
                last.next = first;
                _endNode = previous;
                return true;
            }
            return false;
        } finally {
            _size += collection.size();
        }
    }

    public T getRoot()
    {
        if (_size < 1) throw new IndexOutOfBoundsException();
        return _rootNode.value;
    }

    public T getEnd()
    {
        if (_size < 1) throw new IndexOutOfBoundsException();
        return _size > 1 ? _endNode.value : _rootNode.value;
    }

    private Node<T> track_from_root(int index)
    {
        Node<T> node = _rootNode;
        for (int i=0;i<index;i++)
        {
            if (node.next == null)
                throw new IndexOutOfBoundsException();
            node = node.next;
        }
        return node;
    }

    private Node<T> track_from_end(int index)
    {
        Node<T> node = _endNode;
        for (int i=0;i<index;i++)
        {
            if (node.previous == null)
                throw new IndexOutOfBoundsException();
            node = node.previous;
        }
        return node;
    }

    public T get(int index)
    {
        if (_size/2 - index >= 0)
        {
            return track_from_root(index).value;
        } else return track_from_end(_size - index - 1).value;
    }

    public int size()
    {
        return _size;
    }

    static class DLNodeIterator<T> implements Iterator<T>
    {
        private Node<T> root;

        public DLNodeIterator(Node<T> root)
        {
            this.root = root;
        }

        @Override
        public boolean hasNext() {
            return root.next != null;
        }

        @Override
        public T next() {
            try {
                return root.value;
            } finally {
                root = root.next;
            }
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DLNodeIterator<T>(_rootNode);
    }

    public String toString()
    {
        String out = "[";
        Iterator<T> iterator = iterator();
        while (iterator().hasNext())
        {
            out += String.valueOf(iterator.next());
            if (iterator.hasNext())
            {
                out += ", ";
            }
        }
        return out + "]";
    }
}
