package List;

import java.util.Arrays;

public class ArrayList<E> implements List<E> {

    private static final int DEFAULT_CAPACITY = 10; // 최소 용적 크기
    private static final Object[] EMPTY_ARRAY = {}; // 빈 배열
    Object[] array; // 원소를 담을 배열
    private int size; // 원소 개수

    // 생성자1 (초기 공간 할당 X)
    public ArrayList() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 생성자2 (초기 공간 할당 O)
    public ArrayList(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    // 동적 할당을 위한 resize 메서드
    private void resize() {
        int array_capacity = array.length;
        // capacity가 0일 경우
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }
        // 꽉 찰 경우
        if (size == array_capacity) {
            int new_capacity = array_capacity * 2;
            // copy
            array = Arrays.copyOf(array, new_capacity);
        }
        // size가 capacity의 절반 미만일 경우
        if (size < (array_capacity / 2)) {
            int new_capacity = array_capacity / 2;
            // copy
            array = Arrays.copyOf(array, Math.max(new_capacity, DEFAULT_CAPACITY));
            return;
        }
    }

    @Override
    public boolean add(E value) {
        addLast(value);
        return true;
    }

    public void addLast(E value) {
        // 꽉 차 있을 경우 capacity 재할당
        if (size == array.length) {
            resize();
        }
        array[size] = value;  // 마지막 위치에 요소 추가
        size++;  // size 1 증가
    }

    @Override
    public void add(int index, E value) {
        // 배열의 범위를 벗어날 경우 예외 발생
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        // index가 마지막 위치하면 addLast 메서드 사용
        if (index == size) {
            addLast(value);
        } else {
            // 배열이 꽉 찼을 시 resize
            if (size == array.length) {
                resize();
            }
            // index 기준 뒤에 있는 원소들 한 칸씩 뒤로 밀기
            for (int i = size; i > index; i--) {
                array[i] = array[i - 1];
            }
            // index 위치에 원소 할당
            array[index] = value;
            size++;
        }
    }

    public void addFirst(E value) {
        add(0, value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public E get(int index) {
        if (index >= size || index < 0) { // 배열의 범위를 벗어나면 예외 발생
            throw new IndexOutOfBoundsException();
        }
        // Object 타입에서 E 타입으로 캐스팅 후 반환
        return (E) array[index];
    }

    @Override
    public void set(int index, E value) {
        // 배열의 범위를 벗어나면 예외 발생
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        // 해당 위치의 원소 교체
        array[index] = value;
    }

    @Override
    public int indexOf(Object value) {
        // value와 같은 객체일 경우 i(위치) 반환
        for (int i = 0; i < size; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        // 일치하는 원소가 없을 경우 -1 반환
        return -1;
    }

    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(Object value) {
        // 0 이상이면 원소가 존재한다는 뜻
        if (indexOf(value) >= 0) {
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E remove(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException();
        }
        // 삭제될 원소를 반환하기 위해 임시로 담아둘 변수
        E element = (E) array[index];
        array[index] = null;
        // 삭제한 원소의 뒤에 있는 모든 원소들을 한 칸씩 당겨옴
        for (int i = index; i < size; i++) {
            array[i] = array[i + 1];
            array[i + 1] = null;
        }
        size--;
        resize();
        return element;
    }

    @Override
    public boolean remove(Object value) {
        // 삭제하고자 하는 원소의 index 찾기
        int index = indexOf(value);
        // index가 -1일 경우 배열에 원소가 없다는 뜻으로 false 반환
        if (index == -1) {
            return false;
        }
        // index 위치에 있는 원소 삭제
        remove(index);
        return true;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        // 원소가 0개일 경우 비었으므로 true 반환
        return size == 0;
    }

    @Override
    public void clear() {
        // 모든 원소를 null 처리한다.
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        resize();
    }
}
