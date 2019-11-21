package assignments.doublylinkedlist;

import java.util.*;

/**
 * @author Avyay Natarajan
 * TODO:
 *  - implement java.util.List<T>
 * @param <T>
 */
public class DoublyLinkedList<T> implements Iterable<T>, List<T> {
    private Node<T> _rootNode;
    private Node<T> _endNode;
    private volatile int _size;

    private static class Node<T>
    {
        Node<T> next;
        Node<T> previous;
        T value;

        public Node(T _val)
        {
            this.value = _val;
        }

        public void link(Node<T> next)
        {
            this.next = next;
            next.previous = this;
        }

        public void eject()
        {
            if (next != null)
                next.previous = previous;
            if (previous != null)
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

    public DoublyLinkedList() { }
    public DoublyLinkedList(T o) { _rootNode = new Node<>(o); }

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

    public void add(int index, T value)
    {
        if (index > _size || index < 0) throw new IndexOutOfBoundsException();
        if (index == _size) if (add(value)) return;
        if (index == 0)
        {
            Node<T> temp = _rootNode;
            _rootNode = new Node<>(value);
            _rootNode.link(temp);
            _size++;
            return;
        } else if (_size/2 - index >= 0) {
            Node<T> atIndex = track_from_root(index - 1);
            Node<T> afterIndex = atIndex.next;
            atIndex.link(new Node<>(value));
            atIndex.next.link(afterIndex);
            _size++;
        }
        else
        {
            Node<T> atIndex = track_from_end(_size - index);
            Node<T> afterIndex = atIndex.next;
            atIndex.link(new Node<>(value));
            atIndex.next.link(afterIndex);
            _size++;
        }
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
                previous.link(new Node<>(value));
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
        if (index > _size - 1 || index < 0) throw new IndexOutOfBoundsException();
        if (_size/2 - index >= 0)
        {
            return track_from_root(index).value;
        } else return track_from_end(_size - index - 1).value;
    }

    public int size()
    {
        return _size;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    static class DLNodeIterator<T> implements Iterator<T>
    {
        private Node<T> root;
        private int step;

        public DLNodeIterator(Node<T> root, int size)
        {
            this.root = root; this.step = size;
        }

        @Override
        public boolean hasNext() {
            return step > 0 || root != null && root.next != null;
        }

        @Override
        public T next() {
            synchronized (this) {
                try {
                    return root.value;
                } finally {
                    step--;
                    root = root.next;
                }
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return _rootNode == null || _size == 0;
    }

    @Override
    public boolean contains(Object o) {
        for (T t: this)
            if (t == o) return true;
        return false;
    }

    @Override
    public Object[] toArray() {
        Object[] out = new Object[_size];
        Iterator<T> iterator = iterator();
        int index = 0;
        while (iterator.hasNext())
        {
            out[index] = iterator.next(); index++;
        }
        return out;
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return a;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o: c)
            if (!contains(o)) return false;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {
        synchronized (this)
        {
            while (_size > 0)
            {
                remove(0);
            }
        }
    }

    @Override
    public T set(int index, T element) {
        return null;
    }

    @Override
    public T remove(int index) {
        try {
            if (index == 0) {
                Node<T> temp = _rootNode;
                _rootNode = temp.next;
                temp.eject();
                temp.dispose();
                _size--;
                return temp.value;
            } else if (index == _size - 1) {
                Node<T> temp = _endNode;
                _endNode = temp.previous;
                temp.eject();
                temp.dispose();
                _size--;
                return temp.value;
            }
            if (_size / 2 - index >= 0) {
                Node<T> temp = track_from_root(index);
                temp.eject();
                temp.dispose();
                _size--;
                return temp.value;
            } else {
                Node<T> temp = track_from_end(_size - index - 1);
                temp.eject();
                temp.dispose();
                _size--;
                return temp.value;
            }
        } finally {
            if (_size == 0) {
                _rootNode = null;
                _endNode = null;
            } else if (_size == 1)
                _endNode = _rootNode;
        }
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int lastIndexOf(Object o) {
        return 0;
    }

    @Override
    public ListIterator<T> listIterator() {
        return null;
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return null;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        DoublyLinkedList<T> out = new DoublyLinkedList<>();
        for (fromIndex = fromIndex; fromIndex < toIndex; fromIndex++)
            out.add(get(fromIndex));
        return out;
    }

    @Override
    public Iterator<T> iterator() {
        return new DLNodeIterator<T>(_rootNode, _size);
    }

    public String toString()
    {
        String out = "[";
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
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
