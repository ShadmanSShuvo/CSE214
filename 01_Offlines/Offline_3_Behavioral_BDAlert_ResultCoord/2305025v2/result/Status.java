class Status {
    boolean confirmed;
    boolean orderIssued;
    boolean testimonialIssued;
    boolean certificateIssued;

    @Override
    public String toString() {
        return "Confirmation=" + done(confirmed)
                + ", Office Order=" + done(orderIssued)
                + ", Testimonial=" + done(testimonialIssued)
                + ", Certificate/Transcript=" + done(certificateIssued);
    }

    private String done(boolean value) {
        return value ? "DONE" : "PENDING";
    }
}
