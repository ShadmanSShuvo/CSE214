interface Coordinator {
    void register(Colleague colleague);

    void confirm(String id);

    void officeOrder(String id);

    void testimonial(String id);

    void certificate(String id);

    void status(String id);
}
