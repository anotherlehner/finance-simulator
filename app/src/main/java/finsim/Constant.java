package finsim;

import java.time.LocalDate;

// TODO: get rid of this, most of this should come from data config
public interface Constant {
    double INFLATION_RATE = 0.03;

    double DEFAULT_LIMIT = 1000000.0;

    // TODO: fake a retirement constant but this should go in data files
    LocalDate RETIREMENT_DATE = LocalDate.of(2100, 8, 13);

    double HOUSE_MAINT_RATE = 0.035d;
}
