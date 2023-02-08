package com.cerdure.bookshelf.service.interfaces;

import com.cerdure.bookshelf.dto.utils.DayUtils;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface AttendanceService {

    public void saveAttendance(Authentication authentication);
    public void checkAttendance(Authentication authentication);
    public List<DayUtils> findAttendanceOfMonth(Authentication authentication);
}
