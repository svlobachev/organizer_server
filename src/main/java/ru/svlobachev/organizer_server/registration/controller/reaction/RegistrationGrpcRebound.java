package ru.svlobachev.organizer_server.registration.controller.reaction;

import com.fasterxml.uuid.Generators;
import com.google.common.base.Splitter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.svlobachev.organizer_server.registration.controller.service.RegistrationDatabaseProcessing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class RegistrationGrpcRebound {
    @Autowired
    RegistrationDatabaseProcessing registrationDatabaseProcessing;

    public Map<String, String> registrationResponse(Map<String, String> registrationBox) {
        System.out.println(registrationBox);
        HashMap<String, String> newRegistrationBox = new HashMap<>(registrationBox);

        if (!registrationBox.get("phoneNumber").isEmpty() && registrationBox.get("serverUserUuid").isEmpty()) {
            UUID uuid = Generators.timeBasedGenerator().generate();
            String pin_code = getPinCodeFromSmsRu(registrationBox.get("phoneNumber"));
            newRegistrationBox.put("serverUserUuid", uuid.toString());
            newRegistrationBox.put("registrationPinCode", pin_code);
            newRegistrationBox.put("registrationComplete", "false");
            return newRegistrationBox;
        } else if (!registrationBox.get("phoneNumber").isEmpty() && !registrationBox.get("serverUserUuid").isEmpty()) {
            newRegistrationBox.remove("registrationPinCode");
            newRegistrationBox.put("registrationComplete", "true");
            String phone = newRegistrationBox.get("phoneNumber");
            if (registrationDatabaseProcessing.findUserPhoneNumberInDataBase(newRegistrationBox)) {
                newRegistrationBox.put("serverUserUuid", registrationDatabaseProcessing.getUserUserUuidFromDataBase(newRegistrationBox));
                System.out.println("--> Пользователь с телефоном " + phone + " уже присутствует в базе данных!");
            } else {
                registrationDatabaseProcessing.addNewUserInDateBase(newRegistrationBox);
            }
            System.out.println(newRegistrationBox);
            return newRegistrationBox;
        }
        System.out.println(newRegistrationBox);
        return registrationBox;
    }

    String getPinCodeFromSmsRu(String phone) {
        phone = phone.replace("+", "");
//        String ip = "33.22.11";
        String api_id = "4087D96B-279F-DF31-70A3-086598810DD1";
        StringBuilder newline = new StringBuilder();
        String code = "";
        URL url;
        try {
//             url = new URL("https://sms.ru/code/call?phone="+phone+"&ip="+ip+"&api_id="+api_id);
            url = new URL("file:/Users/sergeilobachev/IntellijProjects/organizer_server/src/main/resources/curl_test");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
            for (String line; (line = reader.readLine()) != null; ) {
                newline.append(line.trim());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Map<String, String> myMapFromString = Splitter.on(",")
                .withKeyValueSeparator(":")
                .split(newline.toString());


        for (Map.Entry<String, String> entry : myMapFromString.entrySet()) {

            if (entry.getKey().contains("code")) {
                code = entry.getValue().trim();
            }
        }
        return code;
    }
}
