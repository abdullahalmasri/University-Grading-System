package edu.university.schools.models;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface StudentAbility {
    String add();
    String drop();
    Map<Integer,BigDecimal> CalAvg(List<Integer> id);
    double CalFees(int id) throws SQLException;
}
