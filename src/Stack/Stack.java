package Stack;

import java.util.Arrays;
import java.util.EmptyStackException;

public class Stack<E> implements StackInterface<E> {

    // 최소 capacity
    private static final int DEFAULT_CAPACITY = 10;
    // 빈 배열
    private static final Object[] EMPTY_ARRAY = {};

    // 원소를 담을 배열
    private Object[] array;
    // 원소 개수
    private int size;

    // 초기 공간 할당 X
    public Stack() {
        this.array = EMPTY_ARRAY;
        this.size = 0;
    }

    // 초기 공간 할당 O
    public Stack(int capacity) {
        this.array = new Object[capacity];
        this.size = 0;
    }

    private void resize() {
        // 빈 배열 일 경우
        if (Arrays.equals(array, EMPTY_ARRAY)) {
            array = new Object[DEFAULT_CAPACITY];
            return;
        }
        // 현재 capacity
        int arrayCapacity = array.length;
        // capacity가 가득 찰 경우
        if (size == arrayCapacity) {
            int newCapacity = arrayCapacity * 2;
            array = Arrays.copyOf(array, newCapacity);
            return;
        }
        // capacity의 절반 미만으로 원소가 차지할 경우
        if (size < (arrayCapacity / 2)) {
            int newCapacity = (arrayCapacity / 2);
            array = Arrays.copyOf(array, Math.max(newCapacity, DEFAULT_CAPACITY));
            return;
        }
    }

    @Override
    public E push(E value) {
        // 배열이 꽉 찼다면 capacity 재할당
        if (size == array.length) {
            resize();
        }
        // 마지막 위치에 원소 삽입
        array[size] = value;
        size++;
        return value;
    }

    @Override
    public E pop() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        @SuppressWarnings("unchecked")
        E obj = (E) array[size - 1];
        array[size - 1] = null;
        size--;
        resize();
        return obj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E peek() {
        if (size == 0) {
            throw new EmptyStackException();
        }
        return (E) array[size - 1];
    }

    @Override
    public int search(Object value) {
        /*
         value가 null일 때는 eqauls(null)이되어
         null pointer exception이 발생할 수 있으니,
         == 로 null값을 비교해준다.
         */
        if (value == null) {
            for (int idx = size - 1; idx >= 0; idx--) {
                if (array[idx] == null) {
                    return size - idx;
                }
            }
        } else {
            for (int idx = size - 1; idx >= 0; idx--) {
                if (value.equals(array[idx])) {
                    return size - idx;
                }
            }
        }
        return -1;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            array[i] = null;
        }
        size = 0;
        resize();
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }
}
