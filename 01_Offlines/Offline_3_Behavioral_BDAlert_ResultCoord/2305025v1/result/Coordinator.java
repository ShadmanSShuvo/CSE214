interface Coordinator {
    void register(DeptOffice dept);

    void register(CtrlOffice ctrl);

    void register(DSW dsw);

    void register(Student student);

    void confirm(String id);

    void officeOrder(String id);

    void testimonial(String id);

    void certificate(String id);

    void status(String id);
}
