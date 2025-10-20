package com.pastiara.app.service1;

import org.springframework.stereotype.Service;
import com.pastiara.app.model.User;
import com.pastiara.app.model.UserInfo;
import com.pastiara.app.repository.UserRepository;
import com.pastiara.app.repository.UserInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserInfoRepository userInfoRepository;

    public UserService(UserRepository userRepository, UserInfoRepository userInfoRepository) {
        this.userRepository = userRepository;
        this.userInfoRepository = userInfoRepository;
    }

    // ==========================
    // CRUD para User
    // ==========================
    public User save(User user) {
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    // ==========================
    // Métodos para UserInfo
    // ==========================
    public UserInfo getUserInfo(Long userId) {
        return userInfoRepository.findByUserId(userId).orElse(null);
    }

    public UserInfo updateUserInfo(Long userId, UserInfo updatedInfo) {
        Optional<UserInfo> existingOpt = userInfoRepository.findByUserId(userId);
        if (existingOpt.isPresent()) {
            UserInfo existing = existingOpt.get();
            existing.setName(updatedInfo.getName());
            existing.setLastName(updatedInfo.getLastName());
            existing.setTelephone(updatedInfo.getTelephone());
            existing.setState(updatedInfo.getState());
            existing.setZipCode(updatedInfo.getZipCode());
            existing.setStreet(updatedInfo.getStreet());
            return userInfoRepository.save(existing);
        } else {
            updatedInfo.setUserId(userId);
            return userInfoRepository.save(updatedInfo);
        }
    }
}
