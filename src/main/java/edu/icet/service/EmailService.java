package edu.icet.service;

import jakarta.mail.MessagingException;

import java.util.Map;

public interface EmailService {
    void sendHtmlEmail(String to, String subject, String templateName, Map<String, Object> templateModel) throws MessagingException;
    boolean sendBookingSuccessEmail(String to, String name);
    boolean sendBookingUnSuccessEmail(String to, String name);
    boolean updateBookingSuccessEmail(String to, String name);
    boolean updateBookingUnSuccessEmail(String to, String name);
    boolean deleteBookingSuccessEmail(String to, String name);
    boolean deleteBookingUnSuccessEmail(String to, String name);
}
