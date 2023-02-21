package com.cerdure.bookshelf.service.classes.event.interfaces;

import com.cerdure.bookshelf.utils.DayUtils;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AttendanceService {
    void saveAttendance(Authentication authentication);
    void checkAttendance(Authentication authentication);
    List<DayUtils> findAttendanceOfMonth(Authentication authentication);
}
