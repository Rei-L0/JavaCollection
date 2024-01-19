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
}
