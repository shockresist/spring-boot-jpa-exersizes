package ua.com.codeminers.rnd.springjpaordercolumn;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;

@Entity
public class TestChild {

    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @SequenceGenerator(sequenceName = "SEQ_TEST_CHILD", name = "SEQ_TEST_CHILD", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TEST_CHILD")
    private Long id;

    @Column
    private String details;

    @ManyToMany(mappedBy = "childs")
    private List<TestParent> parents;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public List<TestParent> getParents() {
        return parents;
    }

    public void setParents(List<TestParent> parents) {
        this.parents = parents;
    }

}