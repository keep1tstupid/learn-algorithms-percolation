import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF matrixUnion;
    private final StateMatrix matrixState;
    private int[] bottomRowIndexes;
    private int matrixSide;

    public Percolation(int n) throws IllegalArgumentException {
        if (n <= 0) {
            throw new IllegalArgumentException("Number " + n + " is not good enough, should be > 0.");
        }

        matrixUnion = new WeightedQuickUnionUF(n * n + 2);
        matrixState = new StateMatrix(n);
        matrixSide = n;

        bottomRowIndexes = new int[matrixSide];

        for (int i = 0; i < matrixSide; i++) {
            bottomRowIndexes[i] = (matrixSide * matrixSide) - i;
        }

        matrixState.setOpen(0);
    }

    private int[] findNeighbours(int x, int y) {
        int bottom = y == matrixSide
                ? -1
                : coordsToIndex(x, y + 1);
        int top = y == 1 ? 0 : coordsToIndex(x, y - 1);
        int right = x + 1 <= matrixSide
                ? coordsToIndex(x + 1, y)
                : -1;
        int left = x - 1 > 0
                ? coordsToIndex(x - 1, y)
                : -1;

        return new int[]{bottom, top, right, left};
    }

    private int coordsToIndex(int x, int y) {
        return (y - 1) * matrixSide + x;
    }

    private void validateCoordValue(int val) {
        if (val <= 0 || val > matrixSide) {
            throw new IllegalArgumentException("Coord " + val + " is not between 1 and " + (matrixSide));
        }
    }
    
    private void connectNeighbours(int index, int[] neighbours) {
        for (int nIndex : neighbours) {
            if ((nIndex > -1) && (matrixState.isOpen(nIndex))) {
                matrixUnion.union(index, nIndex);
            }
        }
    }
    
    public void open(int row, int col) {
        validateCoordValue(col);
        validateCoordValue(row);
        int index = coordsToIndex(col, row);
        matrixState.setOpen(index);
        int[] neighbours = findNeighbours(col, row);
        connectNeighbours(index, neighbours);
    }

    public boolean isOpen(int row, int col) {
        validateCoordValue(col);
        validateCoordValue(row);
        return matrixState.isOpen(coordsToIndex(col, row));
    }

    //is connected to the root?
    public boolean isFull(int row, int col) {
        validateCoordValue(col);
        validateCoordValue(row);
        int index = coordsToIndex(col, row);
        return matrixUnion.find(index) == matrixUnion.find(0);
    }

    public int numberOfOpenSites() {
        int counter = 0;
        for (int i = 1; i <= matrixState.count(); i++) {
            if (matrixState.isOpen(i)) {
                counter++;
            }
        }
        return counter;
    }

    // does the system percolate? (0 >> last row)
    public boolean percolates() {
        for (int i: bottomRowIndexes) {
            if ( matrixState.isOpen(i) && matrixUnion.find(i) == matrixUnion.find(0)) {
                return true;
            }
        }
        return false;
    }

    public void test() {
        System.out.println("bottom is open: " + matrixState.isOpen(matrixSide * matrixSide + 1));
        System.out.println("Row is connected: (0, 1) " + matrixUnion.connected(0, 1));
        System.out.println("Row is connected: (0, 10) " + matrixUnion.connected(0, 10));
        System.out.println("Row is not connected: (0, 11) " + !matrixUnion.connected(0, 11));
    }
}