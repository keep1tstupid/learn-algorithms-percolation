public class StateMatrix {
    private boolean[] matrix;
    private int count;

    public StateMatrix(int n) {
        count = n - 1;
        matrix = new boolean[n];
        for (int i = 0; i < n; i++) {
            matrix[i] = false;
        }
    }

    public void setOpen(int i) {
        validate(i);
        matrix[i] = true;
    }

    public boolean isOpen(int i) {
        validate(i);
        return matrix[i];
    }

    // validate that p is a valid index
    private void validate(int i) throws IllegalArgumentException {
        int n = matrix.length;
        if (i < 0 || i >= n) {
            throw new IllegalArgumentException("index " + i + " is not between 0 and " + (n-1));
        }
    }

    public int count() {
        return count;
    }
}