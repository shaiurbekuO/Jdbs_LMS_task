package java14.models;

import java14.enums.Position;
import java14.enums.Profession;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter @Getter
@ToString
public class Job {
    private Long id;
    private Position position;
    private Profession profession;
    private String description;
    private int experience;

    public Job(Position position, Profession profession, String description, int experience) {
        this.position = position;
        this.profession = profession;
        this.description = description;
        this.experience = experience;
    }
}
