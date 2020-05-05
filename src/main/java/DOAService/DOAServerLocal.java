package main.java.DOAService;

import main.java.dao.EmailMessage;

import java.util.UUID;

public interface DOAServerLocal {
    public Object save(Object object);
    EmailMessage getEmailId(String emailId);

}
