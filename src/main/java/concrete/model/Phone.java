package concrete.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author fred
 *
 */
@Entity
@Table(name = "phones")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Phone {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
    
    private String number;

    private String ddd;

    @ManyToOne
    @Cascade(value={org.hibernate.annotations.CascadeType.ALL})
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

}
