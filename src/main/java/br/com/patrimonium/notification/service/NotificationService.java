package br.com.patrimonium.notification.service;

import br.com.patrimonium.contract.entity.ContractEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final JavaMailSender mailSender;

    public void sendDefaultNotification(ContractEntity contract) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(contract.getTenant().getEmail());
        message.setSubject("Contrato em inadimplência");

        message.setText("""
            Seu contrato referente ao imóvel %s
            encontra-se em situação de inadimplência.

            Regularize o pagamento o quanto antes.
            """.formatted(contract.getProperty().getName()));

        mailSender.send(message);
    }
}
