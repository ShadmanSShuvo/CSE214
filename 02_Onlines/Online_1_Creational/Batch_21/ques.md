
A1
You are developing a document editor that supports 3 types of file formats: .docx, .pdf,
and .txt. To handle each file type, you need different document processors. All
document processors implement an interface called DocumentProcessor, which
contains two methods - LoadDocument and SaveDocument (For now, these may just
print a message).
Given a file name, your system has to recognize the file type and use the appropriate
document processor to load and save a file. The output will be two messages showing
that the file was loaded and saved.

A2
You are designing a system to manage two computer models: WorkPro and LiteMax. They have differences in their Processor and Display. WorkPro is a professional computer uses a Intel Xeon Processor and IPS display. LiteMax is more of a lightweight device that uses ARM Processor and OLED display. There are two different companies, one of which only produces Processors and the other only produces display. 
You have to implement the system where the user will select their preferred model. The system will create the computer with proper processor and display. The classes that represent the computers will contain functions to print the descriptions with model names, components and other characteristics.

B
You are developing a system to build meal plans for a restaurant. There are two kinds of
meals available for now. Each meal has a starter, main dish and a dessert. A Bengali
meal contains vegetable, chicken curry and sweet curd. A Chinese meal contains soup,
Peking Duck and Pudding.
After the user chooses his preferred meal, you have to create the meal with all three
courses using appropriate design pattern. For this task, you don’t need to create
classes for each of the courses and can use strings for demonstrating your
design. You must create a class that represents the final meal with all its courses.

C1
In an e-commerce application, different payment methods are accepted (e.g., Credit card,
PayPal, and Cryptocurrency like Bitcoin). As the business expands, new payment methods
may be introduced and existing ones may undergo changes. The system should be designed to
accommodate these changes without modifying the existing codebase.
You have to implement the payment interface where a user can choose their preferred method
of payment and pay using that method. The classes that represent the payment methods will
contain a function to process the payment. A message ensuring successful payment will be
shown on the screen


C2
You are designing a system to generate documents in different styles. The system needs to
support the creation of letters and resumes. The document creator you are implementing has
two modes: one for professional and formal use and the other for informal usage.
The client can select its preferred mode. After that, they can create letters or resumes from that
mode with their preferred style (formal/informal). The classes that represent document creators
must have functions that return objects of letters or resumes appropriately.