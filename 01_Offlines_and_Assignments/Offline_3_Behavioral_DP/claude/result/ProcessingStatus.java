
/**
 * Tracks how far a single student has progressed through the mandatory
 * sequence of steps. Held internally by the coordinator (Mediator) --
 * the individual offices never see or modify this directly.
 */
class ProcessingStatus {
    private boolean departmentalConfirmationSubmitted = false;
    private boolean officeOrderIssued = false;
    private boolean testimonialIssued = false;
    private boolean certificateAndTranscriptIssued = false;

    public boolean isDepartmentalConfirmationSubmitted() {
        return departmentalConfirmationSubmitted;
    }

    public void setDepartmentalConfirmationSubmitted(boolean v) {
        this.departmentalConfirmationSubmitted = v;
    }

    public boolean isOfficeOrderIssued() {
        return officeOrderIssued;
    }

    public void setOfficeOrderIssued(boolean v) {
        this.officeOrderIssued = v;
    }

    public boolean isTestimonialIssued() {
        return testimonialIssued;
    }

    public void setTestimonialIssued(boolean v) {
        this.testimonialIssued = v;
    }

    public boolean isCertificateAndTranscriptIssued() {
        return certificateAndTranscriptIssued;
    }

    public void setCertificateAndTranscriptIssued(boolean v) {
        this.certificateAndTranscriptIssued = v;
    }

    @Override
    public String toString() {
        return "Departmental Confirmation: " + statusLabel(departmentalConfirmationSubmitted) +
                " | Office Order: " + statusLabel(officeOrderIssued) +
                " | Testimonial: " + statusLabel(testimonialIssued) +
                " | Certificate & Transcript: " + statusLabel(certificateAndTranscriptIssued);
    }

    private String statusLabel(boolean done) {
        return done ? "DONE" : "PENDING";
    }
}
