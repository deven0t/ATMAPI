# ATM API

ATM API is the implementation on sample API required for ATM to withdraw, deposit and to view the account balance.

## Pre-requisite

This ATM API is using Postgres as a database. So to run this application it is required to have postgres is installed on machine.
Postgres will running on localhost with default port

```
postgres-URL: localhost:5432
username: postgres
password: postgres
```

## URL endpoints

1. Get Account Balance

```
GET: localhost:8080/api/account/balance/{accountNumber}/{PIN}
```

2. Withdraw Money from Bank

```
POST: localhost:8080/api/account/withdraw
```

3. Deposit Money in bank account

```
POST: localhost:8080/api/account/deposit
```

>*Note: Both the post methods has to be sent with json body as given below.*
```json
{
	"account_number": "1234567891234",
	"pin":"1234",
	"amount": 100
}
```

##

## Current Implementation
- It has three REST endpoints
- Currently it is with single database
- Unit TestCases are added only for controller
- Not capturing any kind of transactional information

## Improvements can be done
- Store pin in hashed form, same as passwords
- Add Multi-tenancy for database, so that each bank data will be present in separate schema/database.
- Add Unit test for Service by mocking repository
- Add Integration test by setting H2 database with scope for Test. Including sample data.
