package controller;

import java.util.LinkedList;
import java.util.List;

public class PriorityQueue<T> {

    private List<Tuple<T, Double>> elements = new LinkedList<Tuple<T, Double>>();

    public void enqueue(T item, double priority) {
        elements.add(new Tuple<T, Double>(item, priority));
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public T dequeue() {
        int bestIndex = 0;

        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).item2 < elements.get(bestIndex).item2) {
                bestIndex = i;
            }
        }

        T bestItem = elements.get(bestIndex).item1;
        elements.remove(bestIndex);
        return bestItem;
    }

    private static class Tuple<T, O> {
        public final T item1;
        public final O item2;

        public Tuple(T item1, O item2) {
            this.item1 = item1;
            this.item2 = item2;
        }

    }
}
