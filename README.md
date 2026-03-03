# Patrimonium API

## Visão Geral

Patrimonium é uma API para gestão imobiliária com foco em:

-   Gestão de contratos
-   Geração automática de aluguel
-   Cálculo de ROI
-   Multa e juros automáticos
-   Controle de inadimplência
-   Dashboard financeiro

------------------------------------------------------------------------

## Stack

-   Java 21
-   Spring Boot
-   Spring Data JPA
-   PostgreSQL
-   Flyway
-   Lombok

------------------------------------------------------------------------

## Principais Módulos

### Contracts

-   Criação e gestão de contratos
-   Parametrização de multa e juros
-   Dia fixo de vencimento

### Transactions

Tipos: - RENT - PENALTY - INTEREST - EXPENSE

Status: - PENDING - PAID - OVERDUE - CANCELLED

------------------------------------------------------------------------

## Automação Financeira

-   Scheduler mensal para geração automática de aluguel
-   Scheduler diário para aplicação de multa e juros
-   Recalculo automático de ROI após movimentações

------------------------------------------------------------------------

## Endpoints Principais

### Criar Transação

POST /api/v1/transactions

### Listar Transações

GET /api/v1/transactions?propertyId={id}&page=0&size=10

------------------------------------------------------------------------

## Migrações Importantes

-   Adição de reference_transaction_id em financial_transactions
-   Adição de status em financial_transactions
-   Adição de due_day em contracts

------------------------------------------------------------------------

## Fluxo Financeiro

Contrato ativo\
↓\
Geração automática de aluguel\
↓\
Status PENDING\
↓\
Se não pago → OVERDUE\
↓\
Aplicação automática de multa e juros\
↓\
Recalculo de ROI

------------------------------------------------------------------------

## Roadmap Futuro

-   Integração com gateway de pagamento
-   Multi-tenant
-   Notificações automáticas
-   Frontend SPA
