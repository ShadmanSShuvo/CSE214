class DSW extends Colleague {

    public DSW(Coordinator coordinator) {
        super(coordinator);
    }

    public void issueTestimonial(String id) {
        System.out.println("[DSW] Requesting testimonial for " + id);
        coordinator.testimonial(id);
    }
}
