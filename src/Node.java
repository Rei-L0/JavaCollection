class Node<E> {

    E data;
    // 다음 노드 객체를 가르키는 변수
    Node<E> next;

    public Node(E data, Node<E> next) {
        this.data = data;
        this.next = next;
    }
}