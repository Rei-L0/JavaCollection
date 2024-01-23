package List;

import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements List<E> {

    // 노드의 첫 부분
    private Node<E> head;
    // 노드의 마지막 부분
    private Node<E> tail;
    // 원소 개수
    private int size;

    // 생성자
    public SinglyLinkedList() {
        this.head = null;
        this.tail = null;
        this.size = 0;
    }

    private Node<E> search(int index) {
        // index가 범위 밖일 경우 예외 처리
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        // head가 가르키는 노드부터 시작
        Node<E> x = head;
        for (int i = 0; i < index; i++) {
            // x 노드의 다음 노드를 x에 저장한다.
            x = x.next;
        }
        return x;
    }

    public void addFirst(E value) {
        // 새 노드 생성
        Node<E> newNode = new Node<E>(value);
        // 새 노드의 다음 노드로 head 노트 연결
        newNode.next = head;
        // head가 가리키는 노드를 새 노드로 변경
        head = newNode;
        size++;
        /**
         * 다음에 가리킬 노드가 없는 경우(=데이터가 새 노드밖에 없는 경우)
         * 데이터가 한 개(새 노드)밖에 없으므로 새 노드는 처음 시작노드이자
         * 마지막 노드다. 즉 tail = head 다.
         */
        if (head.next == null) {
            tail = head;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        Node<E> newNode = new Node<E>(value);
        // 처음 넣는 노드일 경우 addFirst로 추가
        if (size == 0) {
            addFirst(value);
            return;
        }
        /**
         *  마지막 노드(tail)의 다음 노드(next)가 새 노드를 가리키도록 하고
         *  tail이 가르키는 노드를 새 노드로 바꿔준다.
         */
        tail.next = newNode;
        tail = newNode;
        size++;
    }

    @Override
    public void add(int index, E value) {
        // 잘못된 index를 참조할 경우 예외 발생
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        // 추가하려는 index가 가장 앞일 경우 addFirst() 호출
        if (index == 0) {
            addFirst(value);
            return;
        }
        // 추가하려는 index가 가장 뒤일 경우 addLast() 호출
        if (index == size) {
            addLast(value);
            return;
        }
        // 추가하려는 위치의 이전 노드 탐색
        Node<E> prevNode = search(index - 1);
        // 추가하려는 위치의 노드
        Node<E> nextNode = prevNode.next;
        Node<E> newNode = new Node<E>(value);

        /*
         이전 노드가 가르키는 노드의 연결을 끊은 뒤
         새 노드로 변경해준다.
         또한 새 노드가 가르키는 노드는
         nextNode로 설정한다.
         */

        prevNode.next = null;
        prevNode.next = newNode;
        newNode.next = nextNode;
        size++;
    }

    public E remove() {
        Node<E> headNode = head;
        if (headNode == null) {
            throw new NoSuchElementException();
        }
        // 삭제할 노드를 저장할 변수
        E element = headNode.data;
        // head의 다음 노드
        Node<E> nextNode = headNode.next;

        head.next = null;
        head.data = null;

        head = nextNode;
        size--;

        /* 노드가 1개만 있었을 경우
            해당 노드는 head겸 tail이였기 때문에
            size가 0일 경우 tail도 null로 설정한다.
         */

        if (size == 0) {
            tail = null;
        }
        return element;
    }

    @Override
    public E remove(int index) {
        // 삭제하려는 노드가 첫 번째 원소일 경우
        if (index == 0) {
            return remove();
        }
        // index를 잘못 입력할 경우 예외 처리
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> prevNode = search(index - 1);
        Node<E> removeNode = prevNode.next;
        Node<E> nextNode = removeNode.next;

        // 반환할 데이터를 저장할 변수
        E element = removeNode.data;

        // 이전 노드의 next 변경
        prevNode.next = nextNode;

        // 삭제했던 노드가 마지막 노드라면 tail을 이전 노드로 갱신
        if (prevNode.next == null) {
            tail = prevNode;
        }
        // 데이터 삭제
        removeNode.data = null;
        removeNode.next = null;
        size--;
        return element;
    }

    @Override
    public boolean remove(Object value) {
        Node<E> prevNode = head;
        boolean hasValue = false;
        Node<E> removeNode = head;

        // value와 일치하는 노드를 찾는다.
        for (; removeNode != null; removeNode = removeNode.next) {
            if (value.equals(removeNode.data)) {
                hasValue = true;
                break;
            }
            prevNode = removeNode;
        }
        // 일치하는 노드가 없을 경우 false 반환
        if (removeNode == null) {
            return false;
        }
        // 삭제하려는 노드가 head라면 remove() 사용
        if (removeNode.equals(head)) {
            remove();
            return true;
        } else {
            // 이전 노드의 링크를 삭제하려는 노드의 다음 노드로 연결
            prevNode.next = removeNode.next;
            // 삭제할 노드가 마지막 노드라면 prevNode를 tail로 갱신
            if (prevNode.next == null) {
                tail = prevNode;
            }
            removeNode.data = null;
            removeNode.next = null;
            size--;
            return true;
        }
    }

    @Override
    public E get(int index) {
        return search(index).data;
    }

    @Override
    public void set(int index, E value) {
        Node<E> replaceNode = search(index);
        replaceNode.data = null;
        replaceNode.data = value;
    }

    @Override
    public int indexOf(Object value) {
        int index = 0;
        for (Node<E> x = head; x != null; x = x.next) {
            if (value.equals(x.data)) {
                return index;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) >= 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        for (Node<E> x = head; x != null; ) {
            Node<E> nextNode = x.next;
            x.data = null;
            x.next = null;
            x = nextNode;
        }
        head = tail = null;
        size = 0;
    }
}
