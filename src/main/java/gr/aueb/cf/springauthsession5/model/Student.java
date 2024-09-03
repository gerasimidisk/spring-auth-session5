package gr.aueb.cf.springauthsession5.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

    @EqualsAndHashCode(callSuper=true)
    @Entity
    @Getter
    @Setter
    @NoArgsConstructor
    @Table(name ="students")
    public class Student extends AbstractEntity {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        private String firstName;
        private String lastName;

        public Student(String firstName, String lastName, Boolean isActive) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.setIsActive(isActive);
        }

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "user_id", referencedColumnName = "id")
        private User user;
}
