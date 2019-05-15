package ua.com.codeminers.rnd.springjpaordercolumn;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderColumn;
import javax.persistence.SequenceGenerator;

@Entity
public class TestParent {

    @Id
    @SequenceGenerator(name = "SEQ_TEST_PARENT", sequenceName = "SEQ_TEST_PARENT", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQ_TEST_PARENT")
    private Long id;

    @Column
    private String details;

    @ManyToMany
    @JoinTable(
            name = "TEST_PARENT_CHILD",
            joinColumns = { @JoinColumn(name = "parent_id") },
            inverseJoinColumns = { @JoinColumn(name = "child_id") }
    )
    @OrderColumn(name="child_order")
    private List<TestChild> childs;

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

    public List<TestChild> getChilds() {
        return childs;
    }

    public void setChilds(List<TestChild> childs) {
        this.childs = childs;
    }

}
