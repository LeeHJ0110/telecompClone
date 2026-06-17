package com.kh.six.admin.employeemanage;


import com.kh.six.util.PageVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class EmployeeManageService {

    private final EmployeeManageMapper employeeManageMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Transactional
    public int insert(EmployeeManageVo vo, MultipartFile profile) {
        checkValid(vo);

        //save file
        if( profile != null && !profile.isEmpty()){
            vo.setProfileOriginName(profile.getOriginalFilename());
        }

        //encrypt
        String encodedPw = bCryptPasswordEncoder.encode(vo.getPw());
        vo.setPw(encodedPw);

        return employeeManageMapper.insert(vo);
    }


    private void checkValid(EmployeeManageVo vo) {
        //title , content
        //issue ? exception
    }

    public List<EmployeeManageVo> selectList(PageVo pvo) {
        return employeeManageMapper.selectList(pvo);
    }

    @Transactional
    public EmployeeManageVo selectOne(String no) {
        return employeeManageMapper.selectOne(no);
    }

    @Transactional
    public int updateByNo(EmployeeManageVo vo) {
        System.out.println("service vo = " + vo);
        return employeeManageMapper.updateByNo(vo);
    }


    @Transactional
    public int deleteByNo(EmployeeManageVo vo) {
        return employeeManageMapper.deleteByNo(vo);
    }

    public int selectCount() {
        return employeeManageMapper.selectCount();
    }

    public List<EmployeeManageVo> search(String type, String keyword){
        return employeeManageMapper.search(type, keyword);
    }

    public List<EmployeeManageVo> selectJobList() {
        return employeeManageMapper.selectJobList();
    }

    public List<EmployeeManageVo> selectAgencyList() {
        return employeeManageMapper.selectAgencyList();
    }
}
