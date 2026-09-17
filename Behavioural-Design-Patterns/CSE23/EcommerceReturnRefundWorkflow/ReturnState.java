public interface ReturnState {
    String getStateName();
    void updateReason(ReturnRequest request, String reason);
    void approve(ReturnRequest request);
    void reject(ReturnRequest request);
    void cancel(ReturnRequest request);
    void itemDelivered(ReturnRequest request);
    void inspect(ReturnRequest request, boolean eligible);
    void refundSuccessful(ReturnRequest request);
    void refundFailed(ReturnRequest request);
}
