package br.com.patrimonium.scheduler;

import br.com.patrimonium.contract.service.ContractService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@EnableScheduling
public class ContractScheduler {

    private final ContractService contractService;

    @Scheduled(cron = "0 0 2 * * ?") // todo dia 02:00
    public void verifyDefaults() {
        contractService.checkDefault();
    }
}
