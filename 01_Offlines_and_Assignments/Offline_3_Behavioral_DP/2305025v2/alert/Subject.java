public interface Subject {
    void attach(AlertObserver observer);

    void detach(AlertObserver observer);

    void notifyObservers(Alert alert);
}
