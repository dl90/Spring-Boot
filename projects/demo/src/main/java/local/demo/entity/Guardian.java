package local.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Data
@AttributeOverrides({
        @AttributeOverride(name = "name", column = @Column(name = "guardianName")),
        @AttributeOverride(name = "email", column = @Column(name = "guardianEmail")),
        @AttributeOverride(name = "phoneNumber", column = @Column(name = "guardianPhoneNumber"))
})
public class Guardian {

    private String name;
    private String email;
    private String phoneNumber;
}
