package ru.svlobachev.organizer_server.registration.controller.service;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.svlobachev.organizer_server.registration.model.entity.Users;
import ru.svlobachev.organizer_server.registration.model.repository.UserRepository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Component
public class RegistrationDatabaseProcessing {
    @Autowired
    UserRepository userRepository;


    public String getUserUserUuidFromDataBase(@NotNull HashMap<String, String> newRegistrationBox) {
        String phoneNumber = newRegistrationBox.get("phoneNumber");
        List<Users> users = userRepository.findByValue3(phoneNumber);
        if (!users.isEmpty()) {
            for (Users user : users) {
                if (phoneNumber.equals(user.getValue3())) {
                    newRegistrationBox.put("serverUserUuid", user.getLevel1());
                    return newRegistrationBox.get("serverUserUuid");
                }
            }
        }
        return newRegistrationBox.get("serverUserUuid");
    }

    public boolean findUserPhoneNumberInDataBase(@NotNull HashMap<String, String> newRegistrationBox) {
        String phoneNumber = newRegistrationBox.get("phoneNumber");
        List<Users> users = userRepository.findByValue3(phoneNumber);
        if (!users.isEmpty()) {
            for (Users user : users) {
                if (phoneNumber.equals(user.getValue3())) {
                    return true;
                }
            }
        }
        return false;
    }

    public void addNewUserInDateBase(@NotNull HashMap<String, String> newRegistrationBox) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String currentTimestamp = String.valueOf(timestamp.getTime());


        if (!newRegistrationBox.get("serverUserUuid").isEmpty() && !newRegistrationBox.get("phoneNumber").isEmpty()) {

            String serverUserUuid = newRegistrationBox.get("serverUserUuid");
            String phoneNumber = newRegistrationBox.get("phoneNumber");

            Users row1 = Users.builder()
                    .level1(serverUserUuid)
                    .value1("")
                    .level2("last_activity")
                    .value2(currentTimestamp)
                    .build();
            userRepository.save(row1);

            Users row2 = Users.builder()
                    .level1(serverUserUuid)
                    .value1("")
                    .level2("money")
                    .value2("0")
                    .build();
            userRepository.save(row2);

            Users row3 = Users.builder()
                    .level1(serverUserUuid)
                    .value1("")
                    .level2("contacts")
                    .value2("")
                    .level3("phone")
                    .value3(phoneNumber)
                    .build();
            userRepository.save(row3);
        }
    }


}
