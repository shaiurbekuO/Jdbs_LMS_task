package java14.models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
public class Employee {

    private Long id;
    private String firstName;
    private String lastName;
    private int age;
    private String email;
    private int jobId;

    public Employee(String firstName, String lastName, int age, String email, int jobId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.email = email;
        this.jobId = jobId;
    }
}
