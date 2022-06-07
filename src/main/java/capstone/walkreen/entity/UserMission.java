package capstone.walkreen.entity;

import capstone.walkreen.controller.MissionController;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class UserMission extends BaseEntity {

    @ManyToOne
    //@JoinColumn(name)
    private User user;

    private Mission mission;
}
