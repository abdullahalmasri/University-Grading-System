package edu.university.schools.models;

import java.sql.Timestamp;

public interface Semester {
    Timestamp startTime(Timestamp dateStart);
    Timestamp endTime(Timestamp dateEnd);
}
