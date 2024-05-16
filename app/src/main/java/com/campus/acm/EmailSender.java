package com.campus.acm;

/*
public class EmailSender {

    private static final String SENDGRID_API_KEY = "your_sendgrid_api_key";

    public static void sendVerificationCode(String toEmail, String verificationCode) {
        Email from = new Email("your_email@example.com");
        Email to = new Email(toEmail);
        Content content = new Content("text/plain", "Your verification code is: " + verificationCode);
        Mail mail = new Mail(from, "Your Verification Code", to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
*/