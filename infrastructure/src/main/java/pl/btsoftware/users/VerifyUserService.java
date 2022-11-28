package pl.btsoftware.users;

import org.springframework.stereotype.Service;

@Service
class VerifyUserService implements VerifyUser {
    @Override
    public boolean isValid(UserId userId) {
        return true;
    }
}
