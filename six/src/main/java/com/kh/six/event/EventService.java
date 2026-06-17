package com.kh.six.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@Slf4j
@RequiredArgsConstructor
public class EventService {
    private final EventMapper eventMapper;

    public int checkCount(String no) {
        return eventMapper.checkCount(no);
    }

    public int insertAttendance(String no) {
        return eventMapper.insertAttendance(no);
    }
}
