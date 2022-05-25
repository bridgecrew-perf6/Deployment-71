package personal.fy.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "deployment_server")
public class DeploymentServer implements Serializable {

    @Id
    private Long id;

    private String alias;

    private String hostname;

    private String ipaddr;

    private Integer sshPort;

    private String user;

    private String password;

    private String publicKey;


}
