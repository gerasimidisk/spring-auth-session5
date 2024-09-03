package gr.aueb.cf.springauthsession5.service;

import gr.aueb.cf.springauthsession5.dto.RegisterTeacherDTO;
import gr.aueb.cf.springauthsession5.mapper.Mapper;
import gr.aueb.cf.springauthsession5.model.Teacher;
import gr.aueb.cf.springauthsession5.model.User;
import gr.aueb.cf.springauthsession5.repository.TeacherRepository;
import gr.aueb.cf.springauthsession5.repository.UserRepository;
import gr.aueb.cf.springauthsession5.service.exceptions.TeacherAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TeacherServiceImpl implements ITeacherService {

    private final UserRepository userRepository;
    private final TeacherRepository teacherRepository;

    @Transactional
    @Override
    public Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException {
       Teacher teacher;
       User user;

       try {
           teacher  = Mapper.extractTeacherFromRegisterTeacherDTO(dto);
           user = Mapper.extractUserFromRegisterTeacherDTO(dto);

           Optional<User> returnedUser = userRepository.findByUsername(user.getUsername());
           if (returnedUser.isPresent()) throw new TeacherAlreadyExistsException(dto.getUsername());

           teacher.addUser(user);
           teacherRepository.save(teacher);
           log.info("Teacher with id " + teacher.getId() + " inserted");
       } catch (TeacherAlreadyExistsException e) {
           log.error(e.getMessage());
           throw e;
       }
       return teacher;
    }

    @Override
    public List<Teacher> findAllTeachers() throws Exception {
        return teacherRepository.findAll();
    }
}
