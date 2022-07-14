package id.altanovela.funding.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.dao.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    /**
     * Find Users containing a Name
     * @param {@link Integer}
     * @param {@link Integer}
     * @param {@link String}
     * @return {@link Page}<{@link User}>
     */
    public Page<User> findUsers(Integer pageSize, Integer page, String name){
        return userRepository.findByNameContaining(
                name, PageRequest.of(page, pageSize, Sort.by("id").descending())
        );
    }
    
    /**
     * Add User
     * @param {@link user}
     * @return
     */
    public User addUser(User user){
        User result = userRepository.save(user);
        if(null != result && result.getId() > 0) {
            return result;
        }
        return null;
    }
}
