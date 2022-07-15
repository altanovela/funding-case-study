package id.altanovela.funding.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import id.altanovela.funding.dao.entities.User;
import id.altanovela.funding.dao.repositories.UserRepository;
import id.altanovela.funding.services.base.BaseService;

@Service
public class UserService extends BaseService {
    
    @Autowired
    UserRepository userRepository;
    
    /**
     * <p>
     * Find Users contains a Name
     * </p>
     * @param  {@link Integer}
     * @param  {@link Integer}
     * @param  {@link String}
     * @return {@link Page}<{@link User}>
     */
    public Page<User> findUsers(Integer pageSize, Integer page, String name){
        return userRepository.findByNameContaining(
            name, PageRequest.of(page, pageSize, Sort.by("id").descending())
        );
    }
    
    /**
     * <p>
     * Find By User Id
     * </p>
     * @param  {@link Long}
     * @return {@link User} 
     */
    public User findById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }
    
    /**
     * <p>
     * Add User, return {@link User} with Id, 
     * and <b>null</b> if it's fail.
     * </p>
     * @param  {@link User}
     * @return {@link User}
     */
    public User addUser(User user){
        return geto (
            userRepository.save(user)
        );
    }
}
