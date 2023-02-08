package com.cerdure.bookshelf.service;

import com.cerdure.bookshelf.domain.member.Attendance;
import com.cerdure.bookshelf.domain.member.Member;
import com.cerdure.bookshelf.dto.utils.DayUtils;
import com.cerdure.bookshelf.repository.AttendanceRepository;
import com.cerdure.bookshelf.service.interfaces.AttendanceService;
import com.cerdure.bookshelf.service.interfaces.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final MemberService memberService;

    public void saveAttendance(Authentication authentication){
        if(authentication == null) {
            throw new IllegalStateException("로그인 상태가 아닙니다.");
        } else {
            Member member = memberService.findMember(authentication);
            Attendance today = attendanceRepository.findByMemberAndRegDate(member, LocalDate.now());
            Attendance yesterday = attendanceRepository.findByMemberAndRegDate(member, LocalDate.now().minusDays(1));
            if(today == null){
                if (yesterday == null || yesterday.getPointed()){  // || yesterday.getRegDate().getDayOfMonth() == 1
                    member.changeAtdCount(1);
                } else {
                    member.changeAtdCount(member.getAtdCount() + 1);
                }
                if (member.getAtdCount() == 7) {
                    member.changePoint(member.getPoint() + 1000);
                    memberService.save(member);
                    attendanceRepository.save(Attendance.builder().member(member).pointed(true).build());
                    throw new EmptyStackException();
                }
                memberService.save(member);
                attendanceRepository.save(Attendance.builder().member(member).build());
            } else {
                throw new IllegalStateException("이미 출석이 되어있습니다.");
            }
        }
    }

    @Override
    public void checkAttendance(Authentication authentication) {
        if (authentication != null) {
            Member member = memberService.findMember(authentication);
            Attendance attendance = attendanceRepository.findByMemberAndRegDate(member, LocalDate.now());
            if (attendance != null) {
                throw new IllegalStateException();
            }
        } else {
            throw new IllegalStateException();
        }
    }

    public List<DayUtils> findAttendanceOfMonth(Authentication authentication){
        LocalDate currentDate = LocalDate.now();
        LocalDate startDate = LocalDate.of(currentDate.getYear(),currentDate.getMonth(),1).minusDays(1);
        LocalDate endDate = LocalDate.of(currentDate.getYear(),currentDate.getMonth().plus(1),1).minusDays(1);
        List<DayUtils> days = new ArrayList<>();
        for(int day=1; day<=endDate.getDayOfMonth(); day++){
            days.add(DayUtils.builder()
                    .localDate(LocalDate.of(currentDate.getYear(), currentDate.getMonth(), day))
                    .day(day)
                    .build());
        }

        if(authentication != null) {
            List<Attendance> attendances =
                    attendanceRepository.findByMemberAndRegDateAfter(memberService.findMember(authentication), startDate);

            days.forEach(calenderDay -> {
                calenderDay.setChecked(
                        attendances.stream().filter(
                                        attendance -> attendance.getRegDate().equals(calenderDay.getLocalDate()))
                                .count() > 0 ? true : false);
            });
        }
        return days;
    }

}