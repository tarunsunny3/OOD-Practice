class MyStack<T> {
    private Object[] st; // Use Object array to handle generics
    private int size;
    private int initialCap;

    MyStack() {
        this.size = 0;
        this.initialCap = 10;
        this.st = new Object[initialCap];
    }

    public T pop() {
        if (size == 0) {
            throw new IllegalStateException("Stack is empty!");
        }
       
        T popped = (T) st[size];
        st[size] = null; // Nullify to prevent memory leaks
        return popped;

    }

    public void push(T element) {
        if (size == initialCap) {
            resize();
        }
        st[size] = element;
        size++;
    }

    private void resize() {
        int newCapacity = initialCap * 2;
        Object[] newStack = new Object[newCapacity];
        System.arraycopy(st, 0, newStack, 0, size);
        st = newStack;
        initialCap = newCapacity;
    }

    public void printStack() {
        for (int i = 0; i < size; i++) {
            System.out.println("el is " + st[i]);
        }
    }
}

class StackDriver {
    public static void main(String[] args) {
        MyStack<Integer> st = new MyStack<>();
        st.push(1);
        st.push(2);
        st.printStack();
        System.out.println("Popped: " + st.pop());
        st.printStack();
    }
}
