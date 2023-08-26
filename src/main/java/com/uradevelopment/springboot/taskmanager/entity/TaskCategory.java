package com.uradevelopment.springboot.taskmanager.entity;

import jakarta.persistence.*;

@Entity
@Table(name="category")
public class TaskCategory {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @ManyToOne(cascade= {CascadeType.PERSIST, /*CascadeType.MERGE,*/
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name="owner_id")
    private TaskOwner owner;

    @Column(name="name")
    private String name;

    @Column(name="priority")
    private int priority;

    @Column(name="percentage")
    private int percentage;

    public TaskCategory() {

    }

    public TaskCategory(TaskOwner owner, Task task, String name, int priority) {
        this.owner = owner;
        this.name = name;
        this.priority = priority;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TaskOwner getOwner() {
        return owner;
    }

    public void setOwner(TaskOwner owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    @Override
    public String toString() {
        return "TaskCategory [id=" + id + ", owner=" + owner.getId() + ", name=" + name + ", priority=" + priority
                + ", percentage=" + percentage + "]";
    }

}
