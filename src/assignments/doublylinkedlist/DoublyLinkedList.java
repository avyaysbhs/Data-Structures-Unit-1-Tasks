package assignments.doublylinkedlist;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Avyay Natarajan
 * TODO:
 *  - implement java.util.List<T>
 * @param <T>
 */
public class DoublyLinkedList<T> implements Iterable<T>, List<T>/*, FunctionalList<T>*/ {
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

        public boolean hasNext() { return next != null; }
        public boolean hasPrevious() { return previous != null; }

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

        public String toString() { return String.valueOf(value); }
    }

    public DoublyLinkedList(Collection<? extends T> collection)
    {
        addAll(collection);
    }

    public DoublyLinkedList() { }
    public DoublyLinkedList(T o) { _rootNode = new Node<>(o); }

    public boolean add(T value)
    {
        if(_rootNode==null) {
            _rootNode=new Node<>(value);
            _endNode=_rootNode;
            _size++;
            return true;
        }
        else
        {
            Node<T> newNode=new Node<>(value);
            _endNode.link(newNode);
            _endNode=newNode;
            _size++;
            if (true) return true;
        }

        if (_size > 1) {
            Node<T> previous_end = _endNode;
            _endNode = new Node<>(value);
            previous_end.link(_endNode);
            _size++;
        } else if (_size > 0)
        {
            _endNode = new Node<>(value);
            _rootNode.link(_endNode);
            _size = 2;
        } else {
            _rootNode = new Node<>(value);
            _endNode = _rootNode;
            _size = 1;
        }
        return true;
    }

    public DoublyLinkedList<T> clone()
    {
        DoublyLinkedList<T> out = new DoublyLinkedList<>();
        out.addAll(this);
        return out;
    }

    public void add(int index, T value)
    {
        if (index > _size || index < 0) throw new ArrayIndexOutOfBoundsException();
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

//    public boolean addAll(Collection<? extends T> collection)
//    {
//        Node<T> first = null;
//        Node<T> previous = null;
//        for (T value: collection)
//        {
//            if (previous == null) {
//                previous = new Node<T>(value);
//                first = previous;
//            } else
//            {
//                previous.link(new Node<>(value));
//                previous = previous.next;
//            }
//        }
//        try {
//            if (_rootNode == null) {
//                _rootNode = first;
//                _endNode = previous;
//                return true;
//            } else if (collection.size() > 0) {
//                Node<T> last = _rootNode;
//                while (last.next != null) {
//                    last = last.next;
//                }
//                last.next = first;
//                _endNode = previous;
//                return true;
//            }
//            return false;
//        } finally {
//            _size += collection.size();
//        }
//    }

    public boolean addAll(Collection<? extends T> collection)
    {
        int size = _size;
        collection.forEach(this::add);
        return _size == size + collection.size();
    }

    public T getRoot()
    {
        if (_size < 1) throw new ArrayIndexOutOfBoundsException();
        return _rootNode.value;
    }

    public T getEnd()
    {
        if (_size < 1) throw new ArrayIndexOutOfBoundsException();
        return _size > 1 ? _endNode.value : _rootNode.value;
    }

    private Node<T> track_from_root(int index)
    {
        Node<T> node = _rootNode;
        for (int i=0;i<index;i++)
        {
            //System.out.println(i + "=" + node + ", root=" + _rootNode + ", size=" + _size + ", 0th=" + track_from_root(0));
            if (!node.hasNext()) throw new ArrayIndexOutOfBoundsException();
            node = node.next;
        }
        return node;
    }

    private Node<T> track_from_end(int index)
    {
        Node<T> node = _endNode;
        for (int i=0;i<index-1;i++)
        {
            if (node.previous == null)
                throw new ArrayIndexOutOfBoundsException();
            node = node.previous;
        }
        return node;
    }

    public T get(int index)
    {
        if (index >= _size || index < 0) throw new ArrayIndexOutOfBoundsException();
        return track_from_root(index).value;
//        if (_size/2 - index >= 0)
//        {
//            return track_from_root(index).value;
//        } else return track_from_end(_size - index - 1).value;
    }

    public List<T> median()
    {
        List<T> out = new ArrayList<>();
        out.add(get(_size/2-1));

        if (_size % 2 == 0)
            out.add(get(_size/2));
        return out;
    }

    public DoublyLinkedList<T> reverse()
    {
        DoublyLinkedList<T> out = new DoublyLinkedList<>();
        forEach(e -> out.add(0, e));
        return out;
    }

    public int size()
    {
        return _size;
    }
    @Override
    public boolean remove(Object o) {
        return false;
    }

    public T next() {
        remove(0);
        return _rootNode == null ? null : _rootNode.value;
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
            out[index++] = iterator.next();
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
        try
        {
            AtomicInteger i = new AtomicInteger(index);
            c.forEach(e ->
            {
                this.add(i.getAndIncrement(), e);
            });
            return true;
        } catch (Exception e)
        {
            return false;
        }
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        for (Object o: c)
            if (!this.remove(o)) return false;
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        for (int i=0;i<_size;i++)
            if (!c.contains(get(i))) {
                remove(i); i--;
            }
        return true;
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
        if (index < 0 || index >= _size)
            throw new ArrayIndexOutOfBoundsException();
        Node<T> insertion = new Node<>(element);
        Node<T> old;
        //if (_size/2 - index >= 0)
            old = track_from_root(index);
        //else old = track_from_end(_size - index - 1);

        if (old.hasNext())
            insertion.link(old.next);
        else if (old == _endNode)
            _endNode = insertion;

        if (old.hasPrevious())
            old.previous.link(insertion);
        else if (old == _rootNode)
            _rootNode = insertion;

//        old.eject();
        old.dispose(); return element;
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
        for (int i=0; i<_size; i++)
            if (get(i) == o) return i;
        return -1;
    }

    @Override
    public int lastIndexOf(Object o) {
        for (int i=_size-1; i>0; i--)
            if (get(i) == o) return i;
        return -1;
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        DoublyLinkedList<T> out = new DoublyLinkedList<>();
        for (fromIndex = fromIndex; fromIndex < toIndex; fromIndex++)
            out.add(get(fromIndex));
        return out;
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DoublyLinkedListIterator<T>(this);
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return new DoublyLinkedListIterator<T>(index, this);
    }

    @Override
    public Iterator<T> iterator() {
        return new DoublyLinkedIterator<T>(_rootNode, _size);
    }

    protected static class DoublyLinkedListIterator<T> implements ListIterator<T> {
        private Node<T> lastReturned = null;
        private Node<T> next;
        private Node<T> first;
        private Node<T> last;
        private DoublyLinkedList<T> list;
        private int nextIndex;
        int size;

        DoublyLinkedListIterator(int index, DoublyLinkedList<T> list) {
            next = (index == list.size()) ? null : list.track_from_root(index);
            nextIndex = index;
            first = list.track_from_root(0);
            last = list.track_from_end(0);
            this.list = list;
        }

        DoublyLinkedListIterator(DoublyLinkedList<T> list)
        {
            this(0, list);
        }

        public boolean hasNext() {
            return nextIndex < size;
        }

        public T next() {
            assert hasNext();
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.value;
        }

        public boolean hasPrevious() {
            return nextIndex > 0;
        }

        public T previous() {
            if (!hasPrevious())
                throw new NoSuchElementException();
            lastReturned = next = (next == null) ? last : next.previous;
            nextIndex--;
            return lastReturned.value;
        }

        @Override
        public int nextIndex() {
            return nextIndex;
        }

        @Override
        public int previousIndex() {
            return nextIndex - 1;
        }

        @Override
        public void remove() {
            list.remove(lastReturned.value);
            nextIndex--;
        }

        @Override
        public void set(T t) {
            list.set(nextIndex - 1, t);
        }

        @Override
        public void add(T t) {
            list.add(nextIndex - 1, t);
        }
    }

    protected static class DoublyLinkedIterator<E> implements Iterator<E> {
        private Node<E> root;
        //private List<E> list;
        //private int step;

        public DoublyLinkedIterator(Node<E> root, int size) {
            this.root = root;
            //this.list = list;
            //this.step = size;
        }

        @Override
        public boolean hasNext() {
            return root != null;
        }

        @Override
        public E next() {
            synchronized (this) {
                try {
                    return root.value;
                } finally {
                    //step--;
                    root = root.next;
                }
            }
        }

        @Override
        public void remove() {
            Node<E> eNode = root.next;
            root.eject();
            root.dispose();
            root = eNode;
        }
    }

    public String toString()
    {
        String out = "[";
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
        {
            out += String.valueOf(iterator.next());
            if (iterator.hasNext())
                out += ", ";
        }
        return out + "]";
    }

    public String toReversedString()
    {
        String out = "]";
        Iterator<T> iterator = iterator();
        while (iterator.hasNext())
        {
            out = iterator.next() + out;
            if (iterator.hasNext())
            {
                out = ", " + out;
            }
        }
        return "[" + out;
    }

}