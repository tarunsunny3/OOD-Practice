import java.util.Arrays;

class CustomArrayList<T> {
    private static final int DEFAULT_CAPACITY = 2;
    private Object[] elements;
    private int size = 0;

    public CustomArrayList() {
        elements = new Object[DEFAULT_CAPACITY];
    }

    // Add element to the list
    public void add(T element) {
        ensureCapacity();
        elements[size++] = element;
    }

    // Get element at index
    @SuppressWarnings("unchecked")
    public T get(int index) {
        checkIndex(index);
        return (T) elements[index];
    }

    // Remove element at index
    public void remove(int index) {
        checkIndex(index);
        for(int i = index; i < size-1; i++){
            elements[i] = elements[i+1];
        }
        // System.arraycopy(elements, index + 1, elements, index, size - index - 1);
        elements[--size] = null; // Prevent memory leak
    }

    // Get current size
    public int size() {
        return size;
    }

    // Ensure capacity for new elements
    private void ensureCapacity() {
        
        if (size == elements.length) {
            Object[] newElements = new Object[elements.length * 2];
            for(int i = 0; i < size; i++){
                newElements[i] =  elements[i];
            }
            elements = newElements;
        }
    }

    // Check if index is valid
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Print elements
    public void printList() {
        for (int i = 0; i < size; i++) {
            System.out.print(elements[i] + " ");
        }
        System.out.println();
    }

    // Main method for testing
    public static void main(String[] args) {
        CustomArrayList<Integer> list = new CustomArrayList<>();
        list.add(10);
        list.add(20);
        list.add(30);
        list.printList(); // Output: 10 20 30

        // list.remove(0);
        // list.printList(); //   /Output: 10 30

        // System.out.println("Element at index 1: " + list.get(0)); // Output: 30
        System.out.println("Size: " + list.size()); // Output: 2
    }
}
